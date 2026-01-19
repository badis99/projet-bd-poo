package pharmacie.app;

import javafx.application.Application;
import javafx.stage.Stage;
import pharmacie.view.LoginView;
import pharmacie.view.SceneManager;

public class PharmacieApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Système de Gestion de Pharmacie");

        SceneManager.getInstance().setPrimaryStage(primaryStage);

        // Show Login Screen
        LoginView loginParams = new LoginView();
        SceneManager.getInstance().switchScene("Login", loginParams.getScene());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
