package de.predic8.client;

import de.predic8.server.model.Artikel;

import java.time.*;
import java.util.*;

import static java.time.temporal.ChronoUnit.MILLIS;

public class Util {

    public static void dumpTime(int size, Instant start, Instant end) {

        long millis = MILLIS.between(start, end);

        double single = Double.valueOf(millis) / size;

        System.out.printf("%d Aufrufe in %d ms\n", size, millis);
        System.out.printf("Pro Aufruf: %f ms\n", single);
    }
}
