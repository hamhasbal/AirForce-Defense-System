package app.controllers;

import app.services.SessionManager;
import app.views.LoginView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController {

    private LoginView loginView;

    public void showLogin(Stage stage) {
        loginView = new LoginView(this);
        Scene scene = new Scene(loginView.getView(), 400, 300);
        stage.setScene(scene);
        stage.setTitle("Air Defense System - Login");
        stage.show();
    }

    public void tryLogin(Stage stage, String username, String password) {
        if (SessionManager.authenticate(username, password)) {
            HomeController homeController = new HomeController();
            homeController.showHome(stage);
        } else {
            loginView.showError("Invalid login credentials.");
        }
    }
}
