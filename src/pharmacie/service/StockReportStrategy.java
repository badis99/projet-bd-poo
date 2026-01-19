package pharmacie.service;

import pharmacie.dao.interfaces.DAOFactory;
import pharmacie.dao.interfaces.ProduitDAO;
import pharmacie.model.Produit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockReportStrategy implements ReportStrategy {
    private ProduitDAO produitDAO;

    public StockReportStrategy() {
        this.produitDAO = DAOFactory.getFactory(DAOFactory.Type.MYSQL).getProduitDAO();
    }

    @Override
    public String generateReport() {
        List<Produit> produits = produitDAO.findAll();
        StringBuilder sb = new StringBuilder();
        sb.append("=== Rapport de Stock ===\n");
        sb.append("Total Produits: ").append(produits.size()).append("\n");
        sb.append("---------------------------------\n");
        for (Produit p : produits) {
            sb.append(String.format("%s (Code: %s): %d unités [Seuil: %d]\n",
                    p.getNom(), p.getCodeBarre(), p.getStockActuel(), p.getSeuilMin()));
        }
        return sb.toString();
    }

    @Override
    public Map<String, Object> getData() {
        List<Produit> produits = produitDAO.findAll();
        Map<String, Object> data = new HashMap<>();
        data.put("produits", produits);
        data.put("count", produits.size());
        return data;
    }
}
