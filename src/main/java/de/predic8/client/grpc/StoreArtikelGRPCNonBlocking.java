package de.predic8.client.grpc;

import de.predic8.*;
import de.predic8.Void;
import de.predic8.server.model.Artikel;
import de.predic8.server.util.*;
import io.grpc.*;
import io.grpc.stub.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.logging.*;

import static de.predic8.client.Util.*;
import static de.predic8.server.grpc.Transformer.*;
import static java.time.Instant.*;

public class StoreArtikelGRPCNonBlocking {

    public static int ANZAHL = 1_000;

    public AtomicInteger counter = new AtomicInteger();

    FileUUIDDispenser uuids = new FileUUIDDispenser();

    public StoreArtikelGRPCNonBlocking() throws IOException {
    }

    void doIt() throws Exception {

        RandomDataGenerator generator = new RandomDataGenerator(new SimpleUUIDDispenser());
        List<de.predic8.server.model.Hersteller> hersteller = generator.generateHersteller(100);
        List<Artikel> artikel = generator.generate(1000,hersteller);

        // Turn off extensive debugging
        Logger.getLogger("io.grpc").setLevel(Level.OFF);

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
        ArtikelServiceGrpc.ArtikelServiceStub stub = ArtikelServiceGrpc.newStub(channel);

        System.out.println("Start");
        Instant start = now();

        StreamObserver<Void> obs = getOk();

        for(Artikel a : artikel) {
            stub.storeArtikel(model2grpc(a), obs);
        }

        while (counter.get() < 1000) {

        }

        Instant end = now();

        dumpTime(artikel.size(),start,end);

    }

    @NotNull
    private StreamObserver<Void> getOk() {
        return new StreamObserver<Void>() {
            @Override
            public void onNext(Void aVoid) {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                counter.incrementAndGet();
            }
        };
    }

    @NotNull
    private Id getId(int i) {
        return Id.newBuilder().setId(uuids.get(i).toString()).build();
    }

    public static void main(String[] args) throws Exception {
        new StoreArtikelGRPCNonBlocking().doIt();
    }
}
