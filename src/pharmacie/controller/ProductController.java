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

    // Will add logic for Add/Edit here
}
