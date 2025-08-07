package app.controllers;

import app.views.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import app.data.DataManager;
import app.models.Aircraft;

public class HomeController {

    private BorderPane rootLayout;
    private VBox menuPane;
    private StackPane contentPane;
    private Label alertBanner;
    private Stage mainStage;
    private Scene primaryScene;

    private RadarView radarView;
    private InventoryView inventoryView;
    private PilotView pilotView;
    private DeploymentLogView deploymentLogView;
    private AlertResponseView alertResponseView;

    private String currentUnknownAircraftId;

    public void showHome(Stage stage) {
        mainStage = stage;
        createLayout();

        primaryScene = new Scene(rootLayout, 1200, 800);
        mainStage.setScene(primaryScene);
        mainStage.setTitle("Air Defense System - Home");
        mainStage.show();

        showRadarView();
    }

    private void createLayout() {
        rootLayout = new BorderPane();

        menuPane = new VBox(10);
        menuPane.setPadding(new Insets(10));
        rootLayout.setLeft(menuPane);
        menuPane.setPrefWidth(220);

        Button radarBtn = new Button("Radar View");
        Button inventoryBtn = new Button("Inventory");
        Button pilotBtn = new Button("Pilot Database");
        Button logsBtn = new Button("Deployment Log");
        Button logoutBtn = new Button("Logout");

        radarBtn.setMaxWidth(Double.MAX_VALUE);
        inventoryBtn.setMaxWidth(Double.MAX_VALUE);
        pilotBtn.setMaxWidth(Double.MAX_VALUE);
        logsBtn.setMaxWidth(Double.MAX_VALUE);
        logoutBtn.setMaxWidth(Double.MAX_VALUE);

        radarBtn.setOnAction(e -> showRadarView());
        inventoryBtn.setOnAction(e -> showInventoryView());
        pilotBtn.setOnAction(e -> showPilotView());
        logsBtn.setOnAction(e -> showDeploymentLogView());
        logoutBtn.setOnAction(e -> logout());

        menuPane.getChildren().addAll(radarBtn, inventoryBtn, pilotBtn, logsBtn, logoutBtn);

        contentPane = new StackPane();
        contentPane.setPadding(new Insets(10));
        rootLayout.setCenter(contentPane);

        alertBanner = new Label("ALERT: Unknown Aircraft Detected!");
        alertBanner.setMaxWidth(Double.MAX_VALUE);
        alertBanner.setVisible(false);
        alertBanner.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px;");
        alertBanner.setEffect(new DropShadow(10, Color.RED));
        alertBanner.setOnMouseClicked(e -> {
            if (currentUnknownAircraftId != null) {
                alertResponseView = new AlertResponseView(currentUnknownAircraftId, (Void v) -> {
                    hideAlertBanner();
                    showRadarView();
                    currentUnknownAircraftId = null;
                }, getDeploymentLogView());
                showAlertResponseView();
            }
        });
        rootLayout.setTop(alertBanner);

        radarView = new RadarView(this);
        inventoryView = new InventoryView();
        pilotView = new PilotView();
        deploymentLogView = new DeploymentLogView();
    }

    public void onUnknownAircraftDetected(String aircraftId) {
        currentUnknownAircraftId = aircraftId;
        showAlertBanner();
    }

    private void showRadarView() {
        contentPane.getChildren().setAll(radarView.getView());
    }

    private void showInventoryView() {
        contentPane.getChildren().setAll(inventoryView.getView());
    }

    private void showPilotView() {
        contentPane.getChildren().setAll(pilotView.getView());
    }

    private void showDeploymentLogView() {
        contentPane.getChildren().setAll(deploymentLogView.getView());
    }

    private void showAlertResponseView() {
        contentPane.getChildren().setAll(alertResponseView.getView());
    }

    private void showAlertBanner() {
        alertBanner.setVisible(true);
    }

    private void hideAlertBanner() {
        alertBanner.setVisible(false);
    }

    private DeploymentLogView getDeploymentLogView() {
        if (deploymentLogView == null)
            deploymentLogView = new DeploymentLogView();
        return deploymentLogView;
    }

    private void logout() {
        // Save all data before logout
        DataManager.getInstance().saveAll();

        // Logic to show login screen or reset UI as needed
        LoginController loginController = new LoginController();
        loginController.showLogin(mainStage);
    }

    // This method is needed by AircraftGenerator
    public void handleUnidentifiedAircraft(Aircraft aircraft) {
        onUnknownAircraftDetected(aircraft.getId());
        System.out.println("Unidentified Aircraft Detected: " + aircraft.getId());
    }
    public void handleIdentifiedAircraft(app.models.Aircraft aircraft) {
        System.out.println("Identified Aircraft: " + aircraft.getId());
    }

}
