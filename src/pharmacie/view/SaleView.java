package pharmacie.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pharmacie.controller.SaleController;
import pharmacie.model.LigneVente;
import pharmacie.model.Produit;
import pharmacie.model.Utilisateur;

import java.math.BigDecimal;
import java.util.Optional;

public class SaleView {
    private BorderPane layout;
    private SaleController controller;
    private TableView<Produit> productTable;
    private TableView<LigneVente> cartTable;
    private Label totalLabel;

    public SaleView(Utilisateur user) {
        this.controller = new SaleController(this, user);
        createView();
        controller.loadProducts(productTable);
    }

    private void createView() {
        layout = new BorderPane();
        layout.setPadding(new Insets(10));

        // Split Pane
        SplitPane splitPane = new SplitPane();

        // LEFT: Product Catalog
        VBox leftPane = new VBox(10);
        leftPane.setPadding(new Insets(10));
        Label catalogLabel = new Label("Catalogue Produits");
        catalogLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        productTable = new TableView<>();
        setupProductTable();

        Button addToCartBtn = new Button("Ajouter au Panier");
        addToCartBtn.setMaxWidth(Double.MAX_VALUE);
        addToCartBtn.setOnAction(e -> {
            Produit selected = productTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                askQuantityAndAdd(selected);
            }
        });

        leftPane.getChildren().addAll(catalogLabel, productTable, addToCartBtn);

        // RIGHT: Cart
        VBox rightPane = new VBox(10);
        rightPane.setPadding(new Insets(10));
        Label cartLabel = new Label("Panier en cours");
        cartLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        cartTable = new TableView<>();
        setupCartTable();

        totalLabel = new Label("Total: 0.00 €");
        totalLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button checkoutBtn = new Button("Valider la Vente");
        checkoutBtn.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white;");
        checkoutBtn.setMaxWidth(Double.MAX_VALUE);
        checkoutBtn.setOnAction(e -> controller.checkout());

        rightPane.getChildren().addAll(cartLabel, cartTable, totalLabel, checkoutBtn);

        splitPane.getItems().addAll(leftPane, rightPane);
        splitPane.setDividerPositions(0.6); // 60% for catalog

        layout.setCenter(splitPane);
    }

    private void setupProductTable() {
        TableColumn<Produit, String> nomCol = new TableColumn<>("Nom");
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Produit, Integer> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stockActuel"));

        TableColumn<Produit, BigDecimal> prixCol = new TableColumn<>("Prix");
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prixVente"));

        productTable.getColumns().addAll(nomCol, stockCol, prixCol);
    }

    private void setupCartTable() {
        TableColumn<LigneVente, String> nomCol = new TableColumn<>("Produit");
        nomCol.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProduit().getNom()));

        TableColumn<LigneVente, Integer> qtyCol = new TableColumn<>("Qte");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantite"));

        TableColumn<LigneVente, BigDecimal> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("sousTotal"));

        cartTable.getColumns().addAll(nomCol, qtyCol, totalCol);
        cartTable.setItems(controller.getCartItems());
    }

    private void askQuantityAndAdd(Produit p) {
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("Quantité");
        dialog.setHeaderText("Combien d'unités de " + p.getNom() + " ?");
        dialog.setContentText("Quantité:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(qtyStr -> {
            try {
                int qty = Integer.parseInt(qtyStr);
                if (qty > 0) {
                    controller.addToCart(p, qty);
                }
            } catch (NumberFormatException e) {
                // Ignore invalid numbers
            }
        });
    }

    public void refreshCart() {
        cartTable.refresh();
        // Recalculate Total
        BigDecimal total = BigDecimal.ZERO;
        for (LigneVente item : controller.getCartItems()) {
            total = total.add(item.getSousTotal());
        }
        totalLabel.setText("Total: " + total.toString() + " €");
    }

    public void refreshProductList() {
        controller.loadProducts(productTable);
    }

    public Parent getView() {
        return layout;
    }
}
