package app.views;

import app.controllers.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginView {

    private final StackPane root = new StackPane();
    private final TextField usernameField;
    private final PasswordField passwordField;
    private final Label errorLabel;
    private final LoginController controller;

    public LoginView(LoginController controller) {
        this.controller = controller;

        // === Background Image ===
        Image bgImage = new Image("file:resources/images/login_bg.jpg"); // Ensure this path is correct
        BackgroundSize bgSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(bgImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, bgSize);
        root.setBackground(new Background(backgroundImage));

        // === Login Form ===
        GridPane loginForm = new GridPane();
        loginForm.setAlignment(Pos.CENTER_RIGHT);
        loginForm.setPadding(new Insets(50));
        loginForm.setHgap(10);
        loginForm.setVgap(10);
        loginForm.setMaxWidth(300);
        loginForm.setStyle("-fx-background-color: rgba(255,255,255,0.85); -fx-background-radius: 10;");

        Label userLabel = new Label("Username:");
        Label passLabel = new Label("Password:");

        usernameField = new TextField();
        passwordField = new PasswordField();
        errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button loginButton = new Button("Log In");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setOnAction(e -> {
            errorLabel.setText("");
            controller.tryLogin((Stage) root.getScene().getWindow(),
                    usernameField.getText(), passwordField.getText());
        });

        // Add components to grid
        loginForm.add(userLabel, 0, 0);
        loginForm.add(usernameField, 1, 0);
        loginForm.add(passLabel, 0, 1);
        loginForm.add(passwordField, 1, 1);
        loginForm.add(loginButton, 1, 2);
        loginForm.add(errorLabel, 1, 3);

        // === Position form to right side ===
        HBox formWrapper = new HBox();
        formWrapper.setPadding(new Insets(30));
        formWrapper.setAlignment(Pos.CENTER_RIGHT);
        formWrapper.setPrefWidth(Double.MAX_VALUE);
        formWrapper.getChildren().add(loginForm);

        root.getChildren().add(formWrapper);
    }

    public StackPane getView() {
        return root;
    }

    public void showError(String message) {
        errorLabel.setText(message);
    }
}
