package pharmacie.controller;

import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import pharmacie.model.Produit;
import pharmacie.service.StockService;
import pharmacie.view.ProductView;

import java.util.List;

public class ProductController {
    private ProductView view;
    private StockService stockService;

    public ProductController(ProductView view) {
        this.view = view;
        this.stockService = new StockService();
    }

    public void loadData(TableView<Produit> table) {
        List<Produit> products = stockService.getAllProduits();
        table.setItems(FXCollections.observableArrayList(products));
    }

    public void addProduct(Produit p) {
        try {
            stockService.saveProduit(p);
            // Refresh the table currently displayed in the view
            loadData(view.getTable());
            showAlert("Succès", "Produit ajouté avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors de l'ajout du produit: " + e.getMessage());
        }
    }

    public void deleteProduct(Produit p) {
        if (p == null) {
            showAlert("Avertissement", "Sélectionnez un produit.");
            return;
        }
        try {
            stockService.deleteProduit(p.getId());
            loadData(view.getTable());
            showAlert("Succès", "Produit supprimé.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de supprimer (Probablement lié à des ventes/commandes).");
        }
    }

    private void showAlert(String title, String content) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
