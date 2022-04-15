package de.predic8.client.rest;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import de.predic8.server.model.*;
import de.predic8.server.util.*;
import okhttp3.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.atomic.*;

import static de.predic8.client.Util.*;
import static java.time.Instant.*;

public class StoreArtikelRESTNonBlocking {

    static int ANZAHL = 1_000;

    public AtomicInteger counter = new AtomicInteger();

    ObjectMapper om = new ObjectMapper();

    OkHttpClient client = new OkHttpClient();

    void doIt() throws Exception {

        RandomDataGenerator generator = new RandomDataGenerator(new SimpleUUIDDispenser());
        List<Hersteller> hersteller = generator.generateHersteller(100);
        List<Artikel> artikel = generator.generate(1000,hersteller);

        System.out.println("Start");
        Instant start = now();

        for(Artikel a : artikel) {
            Call call = client.newCall(buildRequest(a));
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    counter.incrementAndGet();
                }
            });
        }

        while (counter.get() < 1000) {
        }

        Instant end = now();

        dumpTime(artikel.size(),start,end);

    }

    @NotNull
    private Request buildRequest(Artikel a) throws JsonProcessingException {
        System.out.println(om.writeValueAsString(a));
        return new Request.Builder()
                .post(RequestBody.create(om.writeValueAsBytes(a)))
                .header("Content-Type","application/json")
                .url("http://localhost:8080/artikel/")
                .build();
    }

    public static void main(String[] args) throws Exception {
        new StoreArtikelRESTNonBlocking().doIt();
    }
}
