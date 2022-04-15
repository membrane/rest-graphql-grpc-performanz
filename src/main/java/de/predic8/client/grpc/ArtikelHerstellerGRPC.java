package de.predic8.client.grpc;

import de.predic8.*;
import de.predic8.Void;
import io.grpc.*;

import java.time.*;
import java.util.*;
import java.util.logging.*;
import java.util.stream.*;

import static de.predic8.client.Util.dumpTime;

public class ArtikelHerstellerGRPC {
    public static void main(String[] args) {
        Logger.getLogger("io.grpc").setLevel(Level.OFF);

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();

        ArtikelServiceGrpc.ArtikelServiceBlockingStub stub = ArtikelServiceGrpc.newBlockingStub(channel);

        Instant t1 = Instant.now();

        ArtikelListe artikel = stub.findArtikel(Void.newBuilder().build());

        List<String> result = artikel.getItemsList().stream().map(a -> {
            Hersteller hersteller = stub.getHersteller(Id.newBuilder().setId(a.getHerstellerId()).build());
            return a.getName() + " " + hersteller.getName();
        }).collect(Collectors.toList());


        Instant end = Instant.now();


        dumpTime(artikel.getItemsCount(),t1,end);

    }
}
