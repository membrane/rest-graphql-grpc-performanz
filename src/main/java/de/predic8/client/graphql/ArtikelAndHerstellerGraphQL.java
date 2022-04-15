package de.predic8.client.graphql;

import com.fasterxml.jackson.databind.*;
import okhttp3.*;

import java.io.*;
import java.time.*;
import java.util.*;

import static de.predic8.client.Util.dumpTime;

public class ArtikelAndHerstellerGraphQL {

    ObjectMapper om = new ObjectMapper();
    OkHttpClient client = new OkHttpClient();

    void doIt() throws IOException {

        Request req = new Request.Builder()
                .url("http://localhost:8080/graphql")
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create("{ \"query\": \"{findArtikel { name hersteller { name }}}\"}".getBytes()))
                .build();

        Instant start = Instant.now();


        Map<String, Object> herstellerMap = null;
        try (Response response = client.newCall(req).execute()) {
            herstellerMap = om.readValue(response.body().byteStream(), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Instant end = Instant.now();

        int size = ((Map<String, List>)herstellerMap.get("data")).get("findArtikel").size();

        dumpTime(size,start,end);

    }

    public static void main(String[] args) throws IOException {
        new ArtikelAndHerstellerGraphQL().doIt();
    }
}
