package app.views;

import app.data.DataManager;
import app.models.Pilot;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class PilotView {

    private final VBox root;
    private final TableView<Pilot> pilotTable;
    private final ObservableList<Pilot> pilotList;

    public PilotView() {
        pilotList = FXCollections.observableArrayList(DataManager.getInstance().getPilots());
        root = new VBox(10);
        root.setPadding(new Insets(10));

        Label title = new Label("Pilot Database");

        pilotTable = new TableView<>(pilotList);
        pilotTable.setEditable(true);  // Enable editing

        TableColumn<Pilot, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Pilot, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Pilot, String> rankColumn = new TableColumn<>("Rank");
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));

        TableColumn<Pilot, Boolean> activeColumn = new TableColumn<>("Active");
        activeColumn.setCellValueFactory(new PropertyValueFactory<>("active"));
        activeColumn.setCellFactory(column -> {
            CheckBoxTableCell<Pilot, Boolean> cell = new CheckBoxTableCell<>();
            cell.setSelectedStateCallback(index -> {
                BooleanProperty prop = new SimpleBooleanProperty(pilotList.get(index).isActive());
                prop.addListener((obs, oldVal, newVal) -> {
                    Pilot pilot = pilotList.get(index);
                    pilot.setActive(newVal);
                    DataManager.getInstance().updatePilot(pilot); // Save immediately
                });
                return prop;
            });
            return cell;
        });
        activeColumn.setEditable(true);

        pilotTable.getColumns().addAll(idColumn, nameColumn, rankColumn, activeColumn);

        Button addButton = new Button("Add Pilot");
        addButton.setOnAction(e -> {
            AddPilotDialog dialog = new AddPilotDialog();
            dialog.showAndWait().ifPresent(newPilot -> {
                DataManager.getInstance().addPilot(newPilot);
                pilotList.add(newPilot);
            });
        });

        Button deleteButton = new Button("Delete Selected");
        deleteButton.setOnAction(e -> {
            Pilot selected = pilotTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                DataManager.getInstance().removePilot(selected);
                pilotList.remove(selected);
            }
        });

        root.getChildren().addAll(title, pilotTable, addButton, deleteButton);
    }

    public VBox getView() {
        return root;
    }
}
