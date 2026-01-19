package pharmacie.service;

import pharmacie.dao.interfaces.CommandeDAO;
import pharmacie.dao.interfaces.DAOFactory;
import pharmacie.exception.DonneeInvalideException;
import pharmacie.model.Commande;
import pharmacie.model.LigneCommande;
import pharmacie.model.Produit;
import pharmacie.model.StatutCommande;

public class CommandeService {
    private CommandeDAO commandeDAO;
    private StockService stockService;

    public CommandeService(StockService stockService) {
        this.stockService = stockService;
        this.commandeDAO = DAOFactory.getFactory(DAOFactory.Type.MYSQL).getCommandeDAO();
    }

    public void createOrder(Commande c) {
        c.setStatut(StatutCommande.EN_ATTENTE);
        commandeDAO.save(c);
    }

    public void receiveOrder(Commande c) throws DonneeInvalideException {
        if (c.getStatut() == StatutCommande.RECUE) {
            throw new DonneeInvalideException("Commande déjà reçue.");
        }

        // Update status
        c.setStatut(StatutCommande.RECUE);
        commandeDAO.save(c);

        // Update Stock
        for (LigneCommande lc : c.getLignes()) {
            Produit p = lc.getProduit();
            // Fetch fresh
            stockService.getProduitById(p.getId()).ifPresent(fresh -> {
                fresh.setStockActuel(fresh.getStockActuel() + lc.getQuantite());
                stockService.saveProduit(fresh);
            });
        }
    }
}
