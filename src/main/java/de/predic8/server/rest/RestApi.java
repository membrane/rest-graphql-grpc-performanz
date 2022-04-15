package de.predic8.server.rest;

import de.predic8.server.model.*;
import de.predic8.server.service.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class RestApi {

    BusinessService srv;

    public RestApi(BusinessService service) {
        this.srv = service;
    }

    @GetMapping("/artikel")
    public Iterable<Artikel> artikels() {
        return srv.allArtikel();
    }

    @GetMapping("/artikel/{aid}")
    public Artikel getArtikel(@PathVariable UUID aid) {
        return srv.getArtikel(aid);
    }

    @GetMapping("/hersteller/{hid}")
    public Hersteller getHersteller(@PathVariable UUID hid) {
        return srv.getHersteller(hid);
    }

    @PostMapping("/artikel")
    public void storeArtikel(@RequestBody Artikel a) {
        srv.storeArtikel(a);
    }

    @GetMapping("/info")
    public String info() {
        return srv.info();
    }
}
