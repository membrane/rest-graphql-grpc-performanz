package de.predic8.server.db;

import de.predic8.server.model.*;
import de.predic8.server.util.*;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
public class DBInitializer {

    RandomDataGenerator generator;

    ArtikelRepository artikelRepo;
    HerstellerRepository herstellerRepo;

    public DBInitializer(RandomDataGenerator generator, ArtikelRepository artikelRepo, HerstellerRepository herstellerRepo) {
        this.generator = generator;
        this.artikelRepo = artikelRepo;
        this.herstellerRepo = herstellerRepo;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) throws Exception {
        System.out.println("DBInitializer.onApplicationEvent");

        List<Hersteller> hersteller = generator.generateHersteller(100);
        hersteller.forEach(herstellerRepo::save);

        generator.generate(1000,hersteller ).forEach(artikelRepo::save);

        System.out.println("DB initialized!");
    }

}

