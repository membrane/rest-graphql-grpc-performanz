package de.predic8.server.util;

import de.predic8.server.model.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
public class RandomDataGenerator {

    UUIDDispenser uuids;

    String[] farben = {"rot","grün","blau","gelb","schwarz","grün","weiß","braun"};

    String[] produkt = {"Lutscher","Saft","Bausteine","Kleber","Papier","Stifte","Kaffee","Brause"};

    String[] eigenschaften = {"Dauer","Meister","Weltmeister","Giga","Mega"};

    String[] hersteller = {"Süß und Klebrig KG","Trödel AG","Quality GmbH","Marktmacht AG","Trödel KG","Saftladen GmbH","Wunsch AG","Büro Bedarf AG","World Inc."};

    String[] ort = {"Bonn","Berlin","Mannheim","Boston","München","Stebbach","Heidelberg","Köln","Mosback","Frankfurt","Sülz"};

    public RandomDataGenerator(UUIDDispenser uuids) {
        this.uuids = uuids;
    }

    public List<Artikel> generate(int anzahl, List<Hersteller> hersteller) throws Exception {

        List<Artikel> l = new ArrayList<>();

        for (int i = 0; i < anzahl; i++) {
            Artikel a = new Artikel();
            a.id = uuids.get(i);
            a.name = pickRandom(eigenschaften) + " " + pickRandom(produkt);
            a.farbe = pickRandom(farben);
            a.gewicht = new Random().nextFloat();
            a.hersteller = pickRandom(hersteller).id;
            l.add(a);
        }
        return l;
    }

    public List<Hersteller> generateHersteller(int anzahl) {
        List<Hersteller> l =  new ArrayList<>();

        for (int i = 0; i < anzahl; i++) {
            Hersteller h = new Hersteller();
            h.id = UUID.randomUUID();
            h.name = pickRandom(hersteller);
            h.ort = pickRandom(ort);
            l.add(h);
        }

        return l;
    }

    public int randInt(int max) {
        return new Random().nextInt(max);
    }

    public String pickRandom(String[] auswahl) {
        return auswahl[randInt(auswahl.length)];
    }

    public Hersteller pickRandom(List<Hersteller> auswahl) {
        return auswahl.get(randInt(auswahl.size()-1));
    }
}
