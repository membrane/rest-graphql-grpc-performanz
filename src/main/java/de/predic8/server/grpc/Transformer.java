package de.predic8.server.grpc;

import de.predic8.*;

import java.util.*;

public class Transformer {

    public static Artikel model2grpc(de.predic8.server.model.Artikel src) {
        return Artikel.newBuilder()
                .setId(src.getId().toString())
                .setName(src.getName())
                .setFarbe(src.getFarbe())
                .setHerstellerId(src.getHersteller().toString())
                .setGewicht(src.getGewicht())
                .build();
    }

    public static de.predic8.server.model.Artikel grpc2model(Artikel src) {
        de.predic8.server.model.Artikel a = new de.predic8.server.model.Artikel();
        a.setId(UUID.fromString(src.getId()));
        a.setName(src.getName());
        a.setFarbe(src.getFarbe());
        a.setHersteller(UUID.fromString(src.getHerstellerId()));
        a.setGewicht(src.getGewicht());
        return a;
    }

    public static Hersteller model2grpc(de.predic8.server.model.Hersteller src) {
        return Hersteller.newBuilder()
                .setId(src.getId().toString())
                .setName(src.getName())
                .setOrt(src.getOrt())
                .build();
    }


}
