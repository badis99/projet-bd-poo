package pharmacie.service;

import pharmacie.model.Produit;

/**
 * Observer interface for receiving stock alerts.
 * Part of the Observer pattern.
 */
public interface StockObserver {
    void onLowStock(Produit produit);
}
