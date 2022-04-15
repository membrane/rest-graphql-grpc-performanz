package de.predic8.client.grpc;

import de.predic8.*;
import de.predic8.server.grpc.*;
import de.predic8.server.model.Artikel;
import de.predic8.server.util.*;
import io.grpc.*;
import okhttp3.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.time.*;
import java.util.*;
import java.util.logging.*;

import static de.predic8.client.Util.*;
import static de.predic8.server.grpc.Transformer.*;
import static java.time.Instant.now;

public class StoreArtikelGRPC {

    public static int ANZAHL = 1_000;

    FileUUIDDispenser uuids = new FileUUIDDispenser();

    public StoreArtikelGRPC() throws IOException {
    }

    void doIt() throws Exception {

        RandomDataGenerator generator = new RandomDataGenerator(new SimpleUUIDDispenser());
        List<de.predic8.server.model.Hersteller> hersteller = generator.generateHersteller(100);
        List<Artikel> artikel = generator.generate(1000,hersteller);

        // Turn off extensive debugging
        Logger.getLogger("io.grpc").setLevel(Level.OFF);

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
        ArtikelServiceGrpc.ArtikelServiceBlockingStub stub = ArtikelServiceGrpc.newBlockingStub(channel);

        System.out.println("Start");
        Instant start = now();

        for(Artikel a : artikel) {
            stub.storeArtikel(model2grpc(a));
        }

        Instant end = now();

        dumpTime(artikel.size(),start,end);

    }

    @NotNull
    private Id getId(int i) {
        return Id.newBuilder().setId(uuids.get(i).toString()).build();
    }

    public static void main(String[] args) throws Exception {
        new StoreArtikelGRPC().doIt();
    }
}
