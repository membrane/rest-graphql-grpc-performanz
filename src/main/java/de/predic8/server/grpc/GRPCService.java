package de.predic8.server.grpc;

import de.predic8.*;
import de.predic8.Void;
import de.predic8.server.service.*;
import io.grpc.stub.*;
import net.devh.boot.grpc.server.service.*;

import java.util.*;

import static de.predic8.server.grpc.Transformer.grpc2model;
import static de.predic8.server.grpc.Transformer.model2grpc;

@GrpcService
public class GRPCService extends ArtikelServiceGrpc.ArtikelServiceImplBase {

    static Void VOID = Void.newBuilder().build();

    BusinessService srv;

    public GRPCService(BusinessService service) {
        this.srv = service;
    }

    @Override
    public void getArtikel(Id id, StreamObserver<Artikel> obs) {
        obs.onNext(model2grpc(srv.getArtikel(id.getId())));
        obs.onCompleted();

    }

    @Override
    public void findArtikel(Void request, StreamObserver<ArtikelListe> obs) {

        Iterator<de.predic8.server.model.Artikel> artikels = srv.allArtikel().iterator();

        ArtikelListe.Builder builder = ArtikelListe.newBuilder();
        while (artikels.hasNext()) {
            builder.addItems(model2grpc(artikels.next()));
        }

        obs.onNext(builder.build());
        obs.onCompleted();
    }

    @Override
    public void getHersteller(Id id, StreamObserver<Hersteller> obs) {
        obs.onNext(model2grpc(srv.getHersteller(id.getId())));
        obs.onCompleted();
    }

    @Override
    public void storeArtikel(Artikel a, StreamObserver<Void> obs) {
        srv.storeArtikel(grpc2model(a));
        obs.onNext(VOID);
        obs.onCompleted();
    }
}
