package pharmacie.view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import pharmacie.controller.ProductController;
import pharmacie.model.Produit;
import pharmacie.service.StockService;

import java.util.List;

public class ProductView {
    private BorderPane layout;
    private TableView<Produit> table;
    private ProductController controller;

    public ProductView() {
        // We initialize parts first
        this.controller = new ProductController(this);
        createView();
        controller.loadData(table);
    }

    private void createView() {
        layout = new BorderPane();
        layout.setPadding(new Insets(10));

        // Toolbar
        HBox toolbar = new HBox(10);
        toolbar.setPadding(new Insets(0, 0, 10, 0));
        Button refreshBtn = new Button("Actualiser");
        refreshBtn.setOnAction(e -> controller.loadData(table));

        Button addBtn = new Button("Ajouter Produit");
        // addBtn.setOnAction(...) // To implement dialog

        toolbar.getChildren().addAll(refreshBtn, addBtn);
        layout.setTop(toolbar);

        // Table
        table = new TableView<>();

        TableColumn<Produit, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Produit, String> nomCol = new TableColumn<>("Nom");
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Produit, Integer> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stockActuel"));

        TableColumn<Produit, Double> prixCol = new TableColumn<>("Prix Vente");
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prixVente"));

        table.getColumns().addAll(idCol, nomCol, stockCol, prixCol);

        layout.setCenter(table);
    }

    public Parent getView() {
        return layout;
    }
}
