package pharmacie.controller;

import pharmacie.exception.AuthentificationException;
import pharmacie.model.Utilisateur;
import pharmacie.service.AuthService;
import pharmacie.view.LoginView;
import pharmacie.view.MainDashboardView;
import pharmacie.view.SceneManager;

public class LoginController {
    private LoginView view;
    private AuthService authService;

    public LoginController(LoginView view) {
        this.view = view;
        this.authService = new AuthService();
    }

    public void handleLogin() {
        String email = view.getEmail();
        String password = view.getPassword();

        if (email.isEmpty() || password.isEmpty()) {
            view.setMessage("Veuillez remplir tous les champs.");
            return;
        }

        try {
            Utilisateur user = authService.login(email, password);
            // Navigate to Dashboard
            MainDashboardView dashboard = new MainDashboardView(user);
            SceneManager.getInstance().switchScene("Dashboard", dashboard.getScene());
        } catch (AuthentificationException e) {
            view.setMessage("Erreur: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            view.setMessage("Erreur système: " + e.getMessage());
        }
    }
}
