package de.predic8.server.model;

import com.fasterxml.jackson.annotation.*;

import java.util.*;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artikel {

    @Id
    public UUID id;

    public String name;
    public String farbe;
    public UUID hersteller;
    public Float gewicht;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFarbe() {
        return farbe;
    }

    public void setFarbe(String farbe) {
        this.farbe = farbe;
    }

    public UUID getHersteller() {
        return hersteller;
    }

    public void setHersteller(UUID hersteller) {
        this.hersteller = hersteller;
    }

    public Float getGewicht() {
        return gewicht;
    }

    public void setGewicht(Float gewicht) {
        this.gewicht = gewicht;
    }

    @Override
    public String toString() {
        return "Artikel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", farbe='" + farbe + '\'' +
                ", hersteller=" + hersteller +
                ", gewicht=" + gewicht +
                '}';
    }
}