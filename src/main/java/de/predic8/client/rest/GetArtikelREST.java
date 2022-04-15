package de.predic8.client.rest;

import com.fasterxml.jackson.databind.*;
import de.predic8.server.model.*;
import de.predic8.server.util.*;
import okhttp3.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.time.*;
import java.util.*;

import static de.predic8.client.Util.*;
import static java.time.Instant.*;

public class GetArtikelREST {

    static int ANZAHL = 1_000;

    FileUUIDDispenser uuids = new FileUUIDDispenser();

    ObjectMapper om = new ObjectMapper();

    OkHttpClient client = new OkHttpClient();

    public GetArtikelREST() throws IOException {
    }

    void doIt() throws Exception {

        List<Artikel> artikel = new ArrayList<>();

        Instant start = now();

        
        for (int i = 0; i < ANZAHL; i++) {
            Call call = client.newCall(buildRequest(i));
            Response res = call.execute();
            if (res.code() != 200) {
                System.out.println("i = " + i);
                System.out.println("uuids.get(i) = " + uuids.get(i));
                throw new RuntimeException();
            }
            artikel.add(om.readValue(res.body().byteStream(), Artikel.class));
        }

        Instant end = now();

        dumpTime(artikel.size(),start,end);

    }

    @NotNull
    private Request buildRequest(int i) {
        return new Request.Builder()
                .url("http://localhost:8080/artikel/" + uuids.get(i))
                .build();
    }

    public static void main(String[] args) throws Exception {
        new GetArtikelREST().doIt();
    }
}
