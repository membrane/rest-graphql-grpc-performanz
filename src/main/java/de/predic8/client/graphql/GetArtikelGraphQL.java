package de.predic8.client.graphql;

import com.fasterxml.jackson.databind.*;
import de.predic8.server.model.*;

import java.io.*;
import java.net.*;
import java.net.http.*;
import java.time.*;
import java.util.*;

import de.predic8.server.util.*;

import static de.predic8.client.Util.dumpTime;
import static java.time.Instant.now;

public class GetArtikelGraphQL {

    ObjectMapper om = new ObjectMapper();

    FileUUIDDispenser uuids = new FileUUIDDispenser();

    static int ANZAHL = 1_000;

    HttpClient client = HttpClient.newBuilder().build();

    public GetArtikelGraphQL() throws Exception {
    }

    void doIt() throws Exception {

        List<Artikel> artikel = new ArrayList<>();

        Instant start = now();

        for (int i = 0; i < ANZAHL; i++) {

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/graphql"))
                    .headers("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(getQuery(i)))
                    .build();

            HttpResponse<InputStream> response = client.send(req, HttpResponse.BodyHandlers.ofInputStream());
            if (response.statusCode() != 200) throw new RuntimeException();

            Result value = om.readValue(response.body(), Result.class);
            artikel.add(value.data.artikel);
        }

        Instant end = Instant.now();

        dumpTime(artikel.size(),start,end);

    }

    String getQuery(int i) {
        return "{ \"query\": \"{ artikel(id:\\\"" + uuids.get(i) + "\\\") { id name farbe gewicht }} \"}";
    }

    public static void main(String[] args) throws Exception {
        new GetArtikelGraphQL().doIt();
    }

    public static class Result {
        public Data data;

        public static class Data {
            public Artikel artikel;
        }
    }
}
