package de.predic8.client.grpc;

import de.predic8.Void;
import de.predic8.*;
import io.grpc.*;

import java.time.*;
import java.util.*;
import java.util.logging.*;
import java.util.stream.*;

import static de.predic8.client.Util.*;

public class ArtikelListeGRPC {
    public static void main(String[] args) {
        Logger.getLogger("io.grpc").setLevel(Level.OFF);

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();

        ArtikelServiceGrpc.ArtikelServiceBlockingStub stub = ArtikelServiceGrpc.newBlockingStub(channel);

        Instant t1 = Instant.now();

        ArtikelListe artikel = stub.findArtikel(Void.newBuilder().build());

        dumpTime(artikel.getItemsCount(),t1, Instant.now());

    }
}
