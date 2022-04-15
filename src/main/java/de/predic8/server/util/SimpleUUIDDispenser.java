package de.predic8.server.util;

import java.util.*;

public class SimpleUUIDDispenser implements UUIDDispenser{
    @Override
    public UUID get(int i) {
        return UUID.randomUUID();
    }
}
