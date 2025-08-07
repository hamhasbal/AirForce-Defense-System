package app.views;

import app.data.DataManager;
import app.models.Aircraft;
import app.models.Pilot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class AlertResponseView {

    private final BorderPane root;
    private final String unknownAircraftId;
    private final Consumer<Void> onResolveCallback;
    private final DeploymentLogView deploymentLogView;

    private final ObservableList<Pilot> pilots;
    private final ObservableList<Aircraft> aircrafts;

    private final ComboBox<Pilot> pilotComboBox;
    private final ComboBox<Aircraft> aircraftComboBox;
    private final TextArea logArea;

    public AlertResponseView(String unknownAircraftId, Consumer<Void> onResolveCallback, DeploymentLogView deploymentLogView) {
        this.unknownAircraftId = unknownAircraftId;
        this.onResolveCallback = onResolveCallback;
        this.deploymentLogView = deploymentLogView;

        pilots = FXCollections.observableArrayList(DataManager.getInstance().getPilots());
        aircrafts = FXCollections.observableArrayList(DataManager.getInstance().getAircrafts());

        root = new BorderPane();

        VBox radarInfoBox = new VBox(10);
        radarInfoBox.setPadding(new Insets(20));
        Label radarHeader = new Label("Radar - Unverified Aircraft");
        radarHeader.setFont(Font.font(18));
        Label aircraftLabel = new Label("Unknown Aircraft: " + unknownAircraftId);

        radarInfoBox.getChildren().addAll(radarHeader, aircraftLabel);
        root.setLeft(radarInfoBox);

        VBox deployPanel = new VBox(15);
        deployPanel.setPadding(new Insets(20));
        Label deployHeader = new Label("Deploy Aircraft");
        deployHeader.setFont(Font.font(18));

        Label pilotLabel = new Label("Select Pilot:");
        pilotComboBox = new ComboBox<>();
        pilotComboBox.setItems(pilots);
        pilotComboBox.setPromptText("Choose Pilot");

        Label aircraftLabel2 = new Label("Select Aircraft:");
        aircraftComboBox = new ComboBox<>();
        aircraftComboBox.setItems(aircrafts);
        aircraftComboBox.setPromptText("Choose Aircraft");

        Button deployButton = new Button("Deploy");
        deployButton.setOnAction(e -> deploy());

        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefHeight(150);

        deployPanel.getChildren().addAll(deployHeader, pilotLabel, pilotComboBox, aircraftLabel2, aircraftComboBox,
                deployButton, new Label("Deployment Log:"), logArea);
        root.setRight(deployPanel);
    }

    private void deploy() {
        Pilot pilot = pilotComboBox.getValue();
        Aircraft aircraft = aircraftComboBox.getValue();

        if (pilot == null || aircraft == null) {
            logArea.appendText("Please select both pilot and aircraft to deploy.\n");
            return;
        }
        if (!aircraft.isAvailable()) {
            logArea.appendText("Selected aircraft is not available.\n");
            return;
        }
        if (!pilot.isActive()) {
            logArea.appendText("Selected pilot is not active.\n");
            return;
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logEntry = String.format("%s - Deployed aircraft %s with pilot %s", timestamp, aircraft.getId(), pilot.getName());

        logArea.appendText(logEntry + "\n");
        deploymentLogView.addLogEntry(logEntry);

        pilot.setAssignedAircraftId(aircraft.getId());
        aircraft.setAvailable(false);

        DataManager.getInstance().updatePilot(pilot);
        DataManager.getInstance().updateAircraft(aircraft);

        if (onResolveCallback != null) {
            onResolveCallback.accept(null);
        }
    }

    public BorderPane getView() {
        return root;
    }
}
