package de.predic8.server.service;

import de.predic8.server.model.*;

import java.util.*;

public interface BusinessService {

    Iterable<Artikel> allArtikel();

    Artikel getArtikel(UUID id);

    default Artikel getArtikel(String id) {
        return getArtikel(UUID.fromString(id));
    }

    void storeArtikel(Artikel artikel);

    Hersteller getHersteller(UUID id);

    default Hersteller getHersteller(String id) {
        return getHersteller(UUID.fromString(id));
    }

    String info();
}
