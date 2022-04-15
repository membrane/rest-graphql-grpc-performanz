package de.predic8.server.service;

import de.predic8.server.model.*;
import de.predic8.server.util.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.util.*;
import java.util.concurrent.*;

//@Service
public class InMemoryService implements BusinessService {

    RandomDataGenerator generator;

    List<Hersteller> hersteller;
    Map<UUID,Artikel> artikelMap = new ConcurrentHashMap<>();

    public InMemoryService(RandomDataGenerator generator) {
        this.generator = generator;
    }

    public Iterable<Artikel> allArtikel() {
        return artikelMap.values();
    }

    public Artikel getArtikel(UUID id) {
        return artikelMap.get(id);
    }

    @Override
    public void storeArtikel(Artikel a) {
        artikelMap.put(a.id,a);
    }

    public Hersteller getHersteller(UUID id) {
        return hersteller.stream().filter( h -> h.id.equals(id)).findFirst().orElseThrow();
    }

    @PostConstruct
    public void init() throws Exception {
        hersteller = generator.generateHersteller(100);
        List<Artikel> artikels = generator.generate(1000,hersteller );

        artikels.forEach(a -> artikelMap.put(a.id, a));
    }

    @Override
    public String info() {
        return String.format("Hersteller: %d Artikel: %d", hersteller.size(),artikelMap.size());
    }
}
