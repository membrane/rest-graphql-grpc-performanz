package de.predic8.client.graphql;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import org.junit.jupiter.api.*;

import de.predic8.server.util.*;

class GetArtikelGraphQLTest {

    ObjectMapper om = new ObjectMapper();

    String json = "{\"data\":{\"artikel\":{\"id\":\"393df0b6-8537-4e1f-968f-1c5ad022b09b\",\"name\":\"Giga Kleber\",\"farbe\":\"grün\",\"gewicht\":0.89750123}}}";

    @Test
    void getQuery() throws Exception {

        GetArtikelGraphQL ql = new GetArtikelGraphQL();
        String query = ql.getQuery(1);

        System.out.println(query);
    }

    @Test
    void parseResult() throws JsonProcessingException {
        om.readValue(json, GetArtikelGraphQL.Result.class);
    }


}