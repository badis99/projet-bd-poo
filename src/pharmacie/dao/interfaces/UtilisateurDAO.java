package pharmacie.dao.interfaces;

import pharmacie.model.Utilisateur;
import java.util.Optional;

public interface UtilisateurDAO extends GenericDAO<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);
}
