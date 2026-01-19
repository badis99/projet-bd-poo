package pharmacie;

import pharmacie.dao.interfaces.DAOFactory;
import pharmacie.model.Produit;
import pharmacie.service.AuthService;
import pharmacie.service.StockService;
import pharmacie.service.StockObserver;

public class TestServiceMain {
    public static void main(String[] args) {
        System.out.println("Starting Service Layer Test...");

        // 1. Auth Service
        AuthService authService = new AuthService();
        String hash = authService.hashPassword("test");
        System.out.println("Hash of 'test': " + hash);

        // 2. Stock Service & Observer
        StockService stockService = new StockService();
        // stockService.addObserver(p -> System.out.println("ALERT: Low stock for " +
        // p.getNom()));

        System.out.println("Checking stock levels...");
        stockService.checkStockLevels(); // Should trigger alerts if any exist in DB

        System.out.println("Service Test Complete.");
    }
}
