package de.predic8.client.graphql;

import com.fasterxml.jackson.databind.*;
import de.predic8.server.model.*;
import de.predic8.server.util.*;

import java.io.*;
import java.net.*;
import java.net.http.*;
import java.time.*;
import java.util.*;

import static de.predic8.client.Util.*;
import static java.time.Instant.*;

public class StoreArtikelGraphQL {

    ObjectMapper om = new ObjectMapper();

    FileUUIDDispenser uuids = new FileUUIDDispenser();

    static int ANZAHL = 1_000;

    HttpClient client = HttpClient.newBuilder().build();

    public StoreArtikelGraphQL() throws Exception {
    }

    void doIt() throws Exception {

        RandomDataGenerator generator = new RandomDataGenerator(new SimpleUUIDDispenser());

        Instant start = now();

        for (Artikel a : generator.generate(1000,generator.generateHersteller(100))) {

            Map<String,Object> am = new HashMap();
            am.put( "id",a.id);
            am.put("name",a.name);
            am.put("farbe",a.farbe);
            am.put("hersteller",a.hersteller);
            am.put("gewicht",a.gewicht);

            Map<String,Map<String,Object>> variables = new HashMap();

            variables.put("artikel", am);

            Map<String,Object> payload = new HashMap();
            payload.put("query","mutation($artikel : ArtikelInput!) {storeArtikel( artikel: $artikel)}");
            payload.put("variables",variables);

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/graphql"))
                    .headers("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofByteArray(om.writeValueAsBytes(payload)))
                    .build();

            HttpResponse<InputStream> response = client.send(req, HttpResponse.BodyHandlers.ofInputStream());
            if (response.statusCode() != 200) throw new RuntimeException();

            Map value = om.readValue(response.body(), Map.class);
        }

        Instant end = Instant.now();

        dumpTime(1000,start,end);

    }

    public static void main(String[] args) throws Exception {
        new StoreArtikelGraphQL().doIt();
    }

    public static class Result {
        public Data data;

        public static class Data {
            public Artikel artikel;
        }
    }
}
