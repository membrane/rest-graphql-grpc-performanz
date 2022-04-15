package de.predic8.server.graphql;

import com.fasterxml.jackson.databind.*;
import com.google.common.collect.*;
import de.predic8.server.db.*;
import de.predic8.server.model.*;
import de.predic8.server.service.*;
import graphql.schema.*;
import org.jetbrains.annotations.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Primary
@Component
public class Fetchers {

    BusinessService service;

    public Fetchers(BusinessService service) {
        this.service = service;
    }

    public DataFetcher<Iterable<Artikel>> findArtikel() {
        return env -> service.allArtikel();
    }

    public DataFetcher<Artikel> getArtikel() {
        return env -> service.getArtikel(UUID.fromString(env.getArgument("id")));
     }

    public DataFetcher<Hersteller> herstellerFetcher() {
        return env -> service.getHersteller(env.<Artikel>getSource().getHersteller());
    }

    public DataFetcher<UUID> storeArtikel() {
        return env -> {
            Map am = (Map)env.getArgument("artikel");
            Artikel a = extractArtikel(am);
            service.storeArtikel(a);
            return a.id;
        };
    }

    @NotNull
    private Artikel extractArtikel(Map am) {
        Artikel a = new Artikel();
        a.id = UUID.fromString((String) am.get("id"));
        a.name = (String) am.get("name");
        a.farbe = (String) am.get("farbe");
        a.hersteller = UUID.fromString((String) am.get("hersteller"));
        a.gewicht =  ((Double) am.get("gewicht")).floatValue();
        return a;
    }
}
