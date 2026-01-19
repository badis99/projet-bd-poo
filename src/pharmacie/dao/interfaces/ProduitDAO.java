package pharmacie.dao.interfaces;

import pharmacie.model.Produit;
import java.util.List;
import java.util.Optional;

public interface ProduitDAO extends GenericDAO<Produit, Long> {
    Optional<Produit> findByCodeBarre(String codeBarre);

    List<Produit> findLowStock();
}
