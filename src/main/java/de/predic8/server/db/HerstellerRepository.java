package de.predic8.server.db;

import de.predic8.server.model.*;
import org.springframework.data.repository.*;

import java.util.*;

public interface HerstellerRepository extends CrudRepository<Hersteller, UUID> {
}