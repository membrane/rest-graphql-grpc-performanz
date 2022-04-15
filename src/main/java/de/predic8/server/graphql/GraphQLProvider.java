package de.predic8.server.graphql;

import graphql.*;
import graphql.schema.*;
import graphql.schema.idl.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import com.google.common.io.Resources;

import javax.annotation.*;
import java.io.*;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.io.Resources.getResource;
import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
public class GraphQLProvider {

    GraphQL graphQL;

    Fetchers fetchers;

    public GraphQLProvider(Fetchers fetchers) {
        this.fetchers = fetchers;
    }

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    private RuntimeWiring wiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("findArtikel", fetchers.findArtikel()))
                .type(newTypeWiring("Query")
                        .dataFetcher("artikel", fetchers.getArtikel()))
                .type(newTypeWiring("Artikel")
                        .dataFetcher("hersteller", fetchers.herstellerFetcher()))
                .type(newTypeWiring("Mutation")
                        .dataFetcher("storeArtikel", fetchers.storeArtikel()))
                .build();
    }

    @PostConstruct
    public void init() throws IOException {
        this.graphQL = GraphQL.newGraphQL(buildSchema(readSDL())).build();
    }

    private String readSDL() throws IOException {
        return Resources.toString(getResource("schema.graphqls"), UTF_8);
    }

    private GraphQLSchema buildSchema(String sdl) {
        return new SchemaGenerator().makeExecutableSchema(new SchemaParser().parse(sdl), wiring());
    }
}