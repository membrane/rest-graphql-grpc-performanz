package de.predic8.server.util;

import java.io.*;
import java.util.*;

import static java.util.UUID.fromString;
import static java.util.UUID.randomUUID;

public class UUIDUtilFileGenerator {

    public static void main(String[] args) throws Exception {

        try(PrintWriter bw = new PrintWriter(new FileWriter(new File("uuids.txt")))) {
            for (int i = 0; i < 1000; i++) {
                bw.println(randomUUID());
            }
        }
    }
}
