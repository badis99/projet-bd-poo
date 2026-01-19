package pharmacie.controller;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pharmacie.model.Utilisateur;
import pharmacie.view.LoginView;
import pharmacie.view.MainDashboardView;
import pharmacie.view.ProductView;
import pharmacie.view.SceneManager;

public class MainDashboardController {
    private MainDashboardView view;
    private Utilisateur user;

    public MainDashboardController(MainDashboardView view, Utilisateur user) {
        this.view = view;
        this.user = user;
    }

    public void showProducts() {
        // Switch center content to Product Management View
        ProductView productView = new ProductView();
        view.setCenterContent(productView.getView());
    }

    public void showSales() {
        VBox placeholder = new VBox(new Label("Module Ventes - À implémenter (Voir Architecture)"));
        placeholder.setAlignment(Pos.CENTER);
        view.setCenterContent(placeholder);
    }

    public void showReports() {
        if (user.getRole() != pharmacie.model.Role.ADMIN) {
            view.setCenterContent(new VBox(new Label("Accès refusé: Administrateur requis.")));
            return;
        }

        pharmacie.service.ReportService reportService = new pharmacie.service.ReportService();
        String stockReport = reportService.generateReport("STOCK");
        String revenueReport = reportService.generateReport("REVENUE");

        javafx.scene.control.TextArea area = new javafx.scene.control.TextArea();
        area.setText(stockReport + "\n\n" + revenueReport);
        area.setEditable(false);

        view.setCenterContent(area);
    }

    public void logout() {
        LoginView login = new LoginView();
        SceneManager.getInstance().switchScene("Login", login.getScene());
    }
}
