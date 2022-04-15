package de.predic8.server.service;

import de.predic8.server.db.*;
import de.predic8.server.model.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class DBService implements BusinessService {

    ArtikelRepository artikelRepo;
    HerstellerRepository herstellerRepo;

    public DBService(ArtikelRepository artikelRepo, HerstellerRepository herstellerRepo) {
        this.artikelRepo = artikelRepo;
        this.herstellerRepo = herstellerRepo;
    }

    public Iterable<Artikel> allArtikel() {
        return artikelRepo.findAll();
    }

    public Artikel getArtikel(UUID id) {
        return artikelRepo.findById( id).stream().findFirst().orElseThrow();
    }

    @Override
    public void storeArtikel(Artikel a) {
        artikelRepo.save(a);
    }

    public Hersteller getHersteller(UUID id) {
        return herstellerRepo.findById( id).stream().findFirst().orElseThrow();
    }

    @Override
    public String info() {
        return String.format("Hersteller: %d Artikel: %d", herstellerRepo.count(),artikelRepo.count());
    }
}
