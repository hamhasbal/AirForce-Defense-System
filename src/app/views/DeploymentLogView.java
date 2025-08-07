package app.views;

import app.data.DataManager;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.util.List;

public class DeploymentLogView {

    private final VBox root;
    private final TextArea logArea;

    public DeploymentLogView() {
        root = new VBox(10);
        root.setPadding(new Insets(10));

        Label title = new Label("Deployment Log");
        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefHeight(600);

        root.getChildren().addAll(title, logArea);

        // Load logs from data manager
        List<String> logs = DataManager.getInstance().getDeploymentLogs();
        for (String log : logs) {
            logArea.appendText(log + "\n");
        }
    }

    public void addLogEntry(String entry) {
        DataManager.getInstance().addDeploymentLog(entry);
        logArea.appendText(entry + "\n");
    }

    public VBox getView() {
        return root;
    }
}
