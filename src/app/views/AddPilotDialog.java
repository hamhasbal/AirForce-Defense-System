package app.views;

import app.models.Pilot;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class AddPilotDialog extends Dialog<Pilot> {

    private TextField idField;
    private TextField nameField;
    private TextField rankField;
    private CheckBox activeCheckBox;

    public AddPilotDialog() {
        setTitle("Add New Pilot");
        setHeaderText("Enter pilot details:");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        idField = new TextField();
        idField.setPromptText("ID");

        nameField = new TextField();
        nameField.setPromptText("Name");

        rankField = new TextField();
        rankField.setPromptText("Rank");

        activeCheckBox = new CheckBox("Active");
        activeCheckBox.setSelected(true);

        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Rank:"), 0, 2);
        grid.add(rankField, 1, 2);
        grid.add(activeCheckBox, 1, 3);

        getDialogPane().setContent(grid);

        Node addButton = getDialogPane().lookupButton(addButtonType);
        addButton.setDisable(true);

        idField.textProperty().addListener((obs, oldVal, newVal) -> {
            addButton.setDisable(newVal.trim().isEmpty());
        });

        setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new Pilot(
                        idField.getText().trim(),
                        nameField.getText().trim(),
                        rankField.getText().trim(),
                        null,
                        activeCheckBox.isSelected()
                );
            }
            return null;
        });
    }
}
