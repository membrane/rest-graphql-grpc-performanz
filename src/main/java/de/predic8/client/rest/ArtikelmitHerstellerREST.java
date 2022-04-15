package de.predic8.client.rest;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import de.predic8.server.model.*;
import okhttp3.*;

import java.io.*;
import java.time.*;
import java.util.*;

import static de.predic8.client.Util.dumpTime;

public class ArtikelmitHerstellerREST {

    ObjectMapper mapper = new ObjectMapper();

    OkHttpClient client = new OkHttpClient();

    Request listReq = new Request.Builder()
            .url("http://localhost:8080/artikel/")
            .addHeader("Content-Type","application/json")
            .build();

    void doIt() throws IOException {

        Call call = client.newCall(listReq);

        Instant start = Instant.now();

        List<Artikel> artikels = getArtikels();

        ArrayList<String> result = new ArrayList<>();

        artikels.forEach(o -> {
            result.add(o.getName() + " von " + getHersteller(o.hersteller));
        });

        Instant end = Instant.now();

        dumpTime(artikels.size(),start,end);

        System.out.println(result);

    }

    private List<Artikel> getArtikels() throws IOException {
        try (Response response = client.newCall(listReq).execute()) {
            return mapper.readValue(response.body().byteStream(), new TypeReference<List<Artikel>>(){});
        }
    }

    private String getHersteller(UUID id) {
        Request herstellerReq = new Request.Builder()
                .url("http://localhost:8080/hersteller/" + id)
                .addHeader("Content-Type","application/json")
                .build();

        try (Response response = client.newCall(herstellerReq).execute()) {
            return mapper.readValue(response.body().byteStream(), Hersteller.class).name;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) throws IOException {
        new ArtikelmitHerstellerREST().doIt();
    }
}
