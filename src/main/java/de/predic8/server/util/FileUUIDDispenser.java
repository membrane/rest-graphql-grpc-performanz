package de.predic8.server.util;

import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;

import static java.util.UUID.fromString;

@Component
public class FileUUIDDispenser implements UUIDDispenser {

    List<UUID> uuids = new ArrayList<>();

    public FileUUIDDispenser() throws IOException {

        try(BufferedReader r = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader()
                .getResourceAsStream("uuids.txt")))) {
            String line;

            while ((line = r.readLine()) != null) {
                uuids.add(fromString(line));
            }
        }
    }

    public UUID get(int i) {
        return uuids.get(i);
    }
}
