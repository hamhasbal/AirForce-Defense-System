package app.views;

import app.data.DataManager;
import app.models.Aircraft;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class InventoryView {

    private final VBox root;
    private final TableView<Aircraft> aircraftTable;
    private final ObservableList<Aircraft> aircraftList;

    public InventoryView() {
        aircraftList = FXCollections.observableArrayList(DataManager.getInstance().getAircrafts());
        root = new VBox(10);
        root.setPadding(new Insets(10));

        Label title = new Label("Aircraft Inventory");

        aircraftTable = new TableView<>(aircraftList);
        aircraftTable.setEditable(true);  // Enable editing

        TableColumn<Aircraft, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Aircraft, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Aircraft, String> armamentColumn = new TableColumn<>("Armament");
        armamentColumn.setCellValueFactory(new PropertyValueFactory<>("armament"));

        TableColumn<Aircraft, Boolean> availableColumn = new TableColumn<>("Available");
        availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
        availableColumn.setCellFactory(column -> {
            CheckBoxTableCell<Aircraft, Boolean> cell = new CheckBoxTableCell<>();
            cell.setSelectedStateCallback(index -> {
                BooleanProperty prop = new SimpleBooleanProperty(aircraftList.get(index).isAvailable());
                prop.addListener((obs, oldVal, newVal) -> {
                    Aircraft aircraft = aircraftList.get(index);
                    aircraft.setAvailable(newVal);
                    DataManager.getInstance().updateAircraft(aircraft); // Save immediately
                });
                return prop;
            });
            return cell;
        });
        availableColumn.setEditable(true);

        aircraftTable.getColumns().addAll(idColumn, typeColumn, armamentColumn, availableColumn);

        Button addButton = new Button("Add Aircraft");
        addButton.setOnAction(e -> {
            AddAircraftDialog dialog = new AddAircraftDialog();
            dialog.showAndWait().ifPresent(newAircraft -> {
                DataManager.getInstance().addAircraft(newAircraft);
                aircraftList.add(newAircraft);
            });
        });

        Button deleteButton = new Button("Delete Selected");
        deleteButton.setOnAction(e -> {
            Aircraft selected = aircraftTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                DataManager.getInstance().removeAircraft(selected);
                aircraftList.remove(selected);
            }
        });

        root.getChildren().addAll(title, aircraftTable, addButton, deleteButton);
    }

    public VBox getView() {
        return root;
    }
}
