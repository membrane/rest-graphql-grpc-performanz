package de.predic8.client.grpc;

import de.predic8.*;
import io.grpc.*;

import java.io.*;
import java.time.*;
import java.util.*;
import java.util.logging.*;

import de.predic8.server.util.*;
import org.jetbrains.annotations.*;

import static de.predic8.client.Util.dumpTime;
import static de.predic8.server.grpc.Transformer.grpc2model;

public class GetArtikelGRPC {

    public static int ANZAHL = 1_000;

    FileUUIDDispenser uuids = new FileUUIDDispenser();

    public GetArtikelGRPC() throws IOException {
    }

    void doIt() throws Exception {

        // Turn off extensive debugging
        Logger.getLogger("io.grpc").setLevel(Level.OFF);

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
        ArtikelServiceGrpc.ArtikelServiceBlockingStub stub = ArtikelServiceGrpc.newBlockingStub(channel);

        List<de.predic8.server.model.Artikel> artikel = new ArrayList<>();

        Instant start = Instant.now();

        for (int i = 0; i < ANZAHL; i++) {
            artikel.add(grpc2model(stub.getArtikel(getId(i))));
        }

        Instant end = Instant.now();

        dumpTime(artikel.size(),start,end);

    }

    @NotNull
    private Id getId(int i) {
        return Id.newBuilder().setId(uuids.get(i).toString()).build();
    }

    public static void main(String[] args) throws Exception {
        new GetArtikelGRPC().doIt();
    }
}
