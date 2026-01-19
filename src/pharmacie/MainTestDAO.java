package pharmacie;

import pharmacie.dao.interfaces.*;
import pharmacie.model.*;

import java.math.BigDecimal;
import java.util.List;

public class MainTestDAO {
    public static void main(String[] args) {
        DAOFactory factory = DAOFactory.getFactory(DAOFactory.Type.MYSQL);

        System.out.println("Testing DAO functionality...");

        // 1. Test Utilisateur DAO
        UtilisateurDAO uDao = factory.getUtilisateurDAO();
        List<Utilisateur> users = uDao.findAll();
        System.out.println("Users found: " + users.size());

        // 2. Test Produit DAO
        ProduitDAO pDao = factory.getProduitDAO();
        List<Produit> products = pDao.findAll();
        System.out.println("Products found: " + products.size());

        if (!products.isEmpty()) {
            Produit p = products.get(0);
            System.out.println("First product: " + p.getNom() + ", Stock: " + p.getStockActuel());
        }

        // 3. Test Vente DAO (just compilation check for logic, real test needs db)
        VenteDAO vDao = factory.getVenteDAO();
        System.out.println("Vente DAO initialized.");

        System.out.println("DAO Test complete.");
    }
}
