package app;

import app.controllers.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        LoginController loginController = new LoginController();
        loginController.showLogin(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

