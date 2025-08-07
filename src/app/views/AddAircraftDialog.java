package app.views;

import app.models.Aircraft;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class AddAircraftDialog extends Dialog<Aircraft> {

    private TextField idField;
    private TextField typeField;
    private TextField armamentField;
    private CheckBox availableCheckBox;

    public AddAircraftDialog() {
        setTitle("Add New Aircraft");
        setHeaderText("Enter aircraft details:");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        idField = new TextField();
        idField.setPromptText("ID");

        typeField = new TextField();
        typeField.setPromptText("Type");

        armamentField = new TextField();
        armamentField.setPromptText("Armament");

        availableCheckBox = new CheckBox("Available");
        availableCheckBox.setSelected(true);

        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Type:"), 0, 1);
        grid.add(typeField, 1, 1);
        grid.add(new Label("Armament:"), 0, 2);
        grid.add(armamentField, 1, 2);
        grid.add(availableCheckBox, 1, 3);

        getDialogPane().setContent(grid);

        Node addButton = getDialogPane().lookupButton(addButtonType);
        addButton.setDisable(true);

        idField.textProperty().addListener((obs, oldVal, newVal) -> {
            addButton.setDisable(newVal.trim().isEmpty());
        });

        setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new Aircraft(
                        idField.getText().trim(),
                        typeField.getText().trim(),
                        armamentField.getText().trim(),
                        availableCheckBox.isSelected()
                );
            }
            return null;
        });
    }
}
