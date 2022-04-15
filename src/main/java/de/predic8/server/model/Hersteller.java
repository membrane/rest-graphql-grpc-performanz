package de.predic8.server.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Hersteller {

    @Id
    public UUID id;

    public String name;

    public String ort;

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

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }
}
