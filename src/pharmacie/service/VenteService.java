package pharmacie.service;

import pharmacie.dao.interfaces.DAOFactory;
import pharmacie.dao.interfaces.VenteDAO;
import pharmacie.exception.StockInsuffisantException;
import pharmacie.model.LigneVente;
import pharmacie.model.Produit;
import pharmacie.model.Vente;

import java.util.Optional;

public class VenteService {
    private VenteDAO venteDAO;
    private StockService stockService;

    public VenteService(StockService stockService) {
        this.stockService = stockService;
        this.venteDAO = DAOFactory.getFactory(DAOFactory.Type.MYSQL).getVenteDAO();
    }

    /**
     * Processes a sale: validates stock, saves sale, decreases stock.
     */
    public void processSale(Vente vente) throws StockInsuffisantException {
        // 1. Validate Stock
        for (LigneVente ligne : vente.getLignes()) {
            Produit p = ligne.getProduit();
            // We should fetch refreshed product to be sure of stock
            Optional<Produit> freshP = stockService.getProduitById(p.getId());
            if (freshP.isPresent()) {
                p = freshP.get();
                if (p.getStockActuel() < ligne.getQuantite()) {
                    throw new StockInsuffisantException("Stock insuffisant pour: " + p.getNom());
                }
            } else {
                throw new StockInsuffisantException("Produit introuvable: " + p.getId());
            }
        }

        // 2. Save Sale (DAO handles transaction and stock update in DB)
        venteDAO.save(vente);

        // 3. Notify Observers about low stock (Post-transaction check)
        stockService.checkStockLevels();
    }
}
