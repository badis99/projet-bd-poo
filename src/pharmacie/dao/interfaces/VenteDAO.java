package pharmacie.dao.interfaces;

import pharmacie.model.Vente;
import java.time.LocalDateTime;
import java.util.List;

public interface VenteDAO extends GenericDAO<Vente, Long> {
    List<Vente> findByPeriod(LocalDateTime start, LocalDateTime end);
}
