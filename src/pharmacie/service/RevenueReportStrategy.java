package pharmacie.service;

import pharmacie.dao.interfaces.DAOFactory;
import pharmacie.dao.interfaces.VenteDAO;
import pharmacie.model.Vente;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RevenueReportStrategy implements ReportStrategy {
    private VenteDAO venteDAO;

    public RevenueReportStrategy() {
        this.venteDAO = DAOFactory.getFactory(DAOFactory.Type.MYSQL).getVenteDAO();
    }

    @Override
    public String generateReport() {
        List<Vente> ventes = venteDAO.findAll();
        BigDecimal totalRevenue = BigDecimal.ZERO;
        for (Vente v : ventes) {
            totalRevenue = totalRevenue.add(v.getTotal());
        }

        return "=== Rapport Chiffre d'Affaires ===\n" +
                "Nombre de ventes: " + ventes.size() + "\n" +
                "Total Revenu: " + totalRevenue.toString() + " €\n";
    }

    @Override
    public Map<String, Object> getData() {
        List<Vente> ventes = venteDAO.findAll();
        BigDecimal totalRevenue = BigDecimal.ZERO;
        for (Vente v : ventes) {
            totalRevenue = totalRevenue.add(v.getTotal());
        }
        Map<String, Object> data = new HashMap<>();
        data.put("totalRevenue", totalRevenue);
        data.put("transactionCount", ventes.size());
        return data;
    }
}
