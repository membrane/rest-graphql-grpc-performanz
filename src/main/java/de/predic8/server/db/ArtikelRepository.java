package de.predic8.server.db;

import de.predic8.server.model.*;
import org.springframework.data.repository.CrudRepository;

import java.util.*;

public interface ArtikelRepository extends CrudRepository<Artikel, UUID> {
}