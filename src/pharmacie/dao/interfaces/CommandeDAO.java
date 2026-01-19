package pharmacie.dao.interfaces;

import pharmacie.model.Commande;
import pharmacie.model.StatutCommande;
import java.util.List;

public interface CommandeDAO extends GenericDAO<Commande, Long> {
    List<Commande> findByStatut(StatutCommande statut);
}
