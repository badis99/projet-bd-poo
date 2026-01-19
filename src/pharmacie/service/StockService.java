package pharmacie.service;

import pharmacie.dao.interfaces.DAOFactory;
import pharmacie.dao.interfaces.ProduitDAO;
import pharmacie.model.Produit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing product stock and alerts.
 * Acts as the Subject in the Observer pattern.
 */
public class StockService {
    private ProduitDAO produitDAO;
    private List<StockObserver> observers;

    public StockService() {
        this.produitDAO = DAOFactory.getFactory(DAOFactory.Type.MYSQL).getProduitDAO();
        this.observers = new ArrayList<>();
    }

    // For testing mocking
    public StockService(ProduitDAO produitDAO) {
        this.produitDAO = produitDAO;
        this.observers = new ArrayList<>();
    }

    public void addObserver(StockObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(StockObserver observer) {
        this.observers.remove(observer);
    }

    private void notifyObservers(Produit p) {
        for (StockObserver observer : observers) {
            observer.onLowStock(p);
        }
    }

    public void checkStockLevels() {
        List<Produit> lowStock = produitDAO.findLowStock();
        for (Produit p : lowStock) {
            notifyObservers(p);
        }
    }

    // Pass-through DAO methods
    public List<Produit> getAllProduits() {
        return produitDAO.findAll();
    }

    public Optional<Produit> getProduitById(Long id) {
        return produitDAO.findById(id);
    }

    public void saveProduit(Produit p) {
        produitDAO.save(p);
    }

    public void deleteProduit(Long id) {
        produitDAO.delete(id);
    }
}
