package pharmacie.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import pharmacie.controller.LoginController;

public class LoginView {
    private LoginController controller;
    private Scene scene;

    // UI Controls
    private TextField emailField;
    private PasswordField passwordField;
    private Label messageLabel;

    public LoginView() {
        this.controller = new LoginController(this);
        createView();
    }

    private void createView() {
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 20; -fx-background-color: #f4f4f4;");

        Label titleLabel = new Label("Pharmacie Management");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(300);

        passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");
        passwordField.setMaxWidth(300);

        Button loginButton = new Button("Se connecter");
        loginButton.setDefaultButton(true);
        loginButton.setMaxWidth(300);
        loginButton.setStyle("-fx-background-color: #2c96ea; -fx-text-fill: white; -fx-font-size: 14px;");
        loginButton.setOnAction(e -> controller.handleLogin());

        messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: red;");

        root.getChildren().addAll(titleLabel, emailField, passwordField, loginButton, messageLabel);

        this.scene = new Scene(root, 400, 300);
    }

    public Scene getScene() {
        return scene;
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
    }
}
