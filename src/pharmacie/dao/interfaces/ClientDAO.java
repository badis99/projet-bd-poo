package pharmacie.dao.interfaces;

import pharmacie.model.Client;
import java.util.Optional;

public interface ClientDAO extends GenericDAO<Client, Long> {
    Optional<Client> findByCarteVitale(String carteVitale);
}
