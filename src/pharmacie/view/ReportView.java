package pharmacie.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pharmacie.model.Produit;
import pharmacie.service.ReportService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ReportView {
    private BorderPane layout;
    private ReportService reportService;
    private PieChart stockChart;
    private BarChart<String, Number> supplierChart;
    private Label totalRevenueLabel;
    private Label totalSalesLabel;

    public ReportView() {
        this.reportService = new ReportService();
        createView();
        refresh();
    }

    private void createView() {
        layout = new BorderPane();
        layout.setPadding(new Insets(20));

        // Header
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 0, 20, 0));

        Label title = new Label("Tableau de Bord - Rapports");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button refreshBtn = new Button("Actualiser");
        refreshBtn.setStyle("-fx-background-color: #5bc0de; -fx-text-fill: white;");
        refreshBtn.setOnAction(e -> refresh());

        BorderPane headerPane = new BorderPane();
        headerPane.setLeft(title);
        headerPane.setRight(refreshBtn);
        layout.setTop(headerPane);

        // Content
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);

        // 1. Revenue Cards (Row 0, Col 0-1)
        HBox cards = new HBox(20);
        cards.setAlignment(Pos.CENTER);

        VBox revenueCard = createCard("Chiffre d'Affaires Total", "0.00 €");
        totalRevenueLabel = (Label) revenueCard.getChildren().get(1);

        VBox salesCard = createCard("Nombre de Ventes", "0");
        totalSalesLabel = (Label) salesCard.getChildren().get(1);

        cards.getChildren().addAll(revenueCard, salesCard);

        grid.add(cards, 0, 0, 2, 1);

        // 2. Stock Chart (Row 1, Col 0)
        stockChart = new PieChart();
        stockChart.setTitle("État du Stock");
        grid.add(stockChart, 0, 1);

        // 3. Supplier Chart (Row 1, Col 1)
        grid.add(createSupplierPerformanceChart(), 1, 1);

        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);
        layout.setCenter(scroll);
    }

    private VBox createCard(String title, String initialValue) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setStyle(
                "-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0); -fx-background-radius: 5;");
        card.setAlignment(Pos.CENTER);
        card.setPrefWidth(200);

        Label titleLbl = new Label(title);
        titleLbl.setStyle("-fx-text-fill: #777; -fx-font-size: 14px;");

        Label valueLbl = new Label(initialValue);
        valueLbl.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        card.getChildren().addAll(titleLbl, valueLbl);
        return card;
    }

    public void refresh() {
        // Stock Data
        Map<String, Object> stockData = reportService.getReportData("STOCK");
        if (stockData.get("produits") instanceof List) {
            @SuppressWarnings("unchecked")
            List<Produit> produits = (List<Produit>) stockData.get("produits");

            int lowStock = 0;
            int normalStock = 0;

            for (Produit p : produits) {
                if (p.isStockLow()) {
                    lowStock++;
                } else {
                    normalStock++;
                }
            }

            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                    new PieChart.Data("Stock Faible", lowStock),
                    new PieChart.Data("Stock Normal", normalStock));
            stockChart.setData(pieData);
        }

        // Revenue Data
        Map<String, Object> revenueData = reportService.getReportData("REVENUE");
        if (revenueData.containsKey("totalRevenue")) {
            BigDecimal rev = (BigDecimal) revenueData.get("totalRevenue");
            totalRevenueLabel.setText(rev.toString() + " €");
        }
        if (revenueData.containsKey("transactionCount")) {
            Integer count = (Integer) revenueData.get("transactionCount");
            totalSalesLabel.setText(count.toString());
        }

        // Expenditure Data (for chart or cards if added)
        // We will pass this to the chart method via a class field or logic update if we
        // want real-time update.
        // For simplicity, let's update chart directly here or ensure chart method pulls
        // fresh data.
        // Actually, createSupplierPerformanceChart pulls from DAO directly currently.
        // Let's change it to pull from reportService.
        updateSupplierChart();
    }

    // Extracted method to update chart data
    private void updateSupplierChart() {
        if (supplierChart == null)
            return;

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Volume d'Achats (€)");

        Map<String, Object> expData = reportService.getReportData("EXPENDITURE");
        if (expData.containsKey("supplierBreakdown")) {
            @SuppressWarnings("unchecked")
            Map<String, BigDecimal> breakdown = (Map<String, BigDecimal>) expData.get("supplierBreakdown");
            for (Map.Entry<String, BigDecimal> entry : breakdown.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }
        }

        supplierChart.getData().clear();
        supplierChart.getData().add(series);
    }

    private Node createSupplierPerformanceChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Fournisseur");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Montant (€)");

        supplierChart = new BarChart<>(xAxis, yAxis);
        supplierChart.setTitle("Volume d'Achats Fournisseurs");

        // Initial population
        updateSupplierChart();

        return supplierChart;
    }

    public Parent getView() {
        return layout;
    }
}
