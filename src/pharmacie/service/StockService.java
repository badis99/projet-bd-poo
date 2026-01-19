package pharmacie.service;

import pharmacie.dao.interfaces.DAOFactory;
import pharmacie.dao.interfaces.ProduitDAO;
import pharmacie.model.Produit;

import java.util.List;
import java.util.Optional;

public class StockService {
    private ProduitDAO produitDAO;

    public StockService() {
        this.produitDAO = DAOFactory.getFactory(DAOFactory.Type.MYSQL).getProduitDAO();
    }

    public List<Produit> getAllProduits() {
        return produitDAO.findAll();
    }

    public void saveProduit(Produit p) {
        if (p.getCodeBarre() != null && !p.getCodeBarre().isEmpty()) {
            Optional<Produit> existing = produitDAO.findByCodeBarre(p.getCodeBarre());
            if (existing.isPresent() && (p.getId() == null || !existing.get().getId().equals(p.getId()))) {
                throw new RuntimeException("Code barre déjà existant: " + p.getCodeBarre());
            }
        }
        produitDAO.save(p);
    }

    public void deleteProduit(Long id) {
        produitDAO.delete(id);
    }

    public List<Produit> checkLowStock() {
        return produitDAO.findLowStock();
    }

    public void checkStockLevels() {
        // Alias for compatibility or simplified void check if needed by tests
        // or printed output. For now let's just ignore or log.
        // If TestServiceMain expects this to do something, we can map it.
        // Assuming it might print warnings.
        List<Produit> low = checkLowStock();
        if (!low.isEmpty()) {
            System.out.println("ALERT: " + low.size() + " produits en stock critique.");
        }
    }

    public Optional<Produit> getProduitById(Long id) {
        return produitDAO.findById(id);
    }
}
