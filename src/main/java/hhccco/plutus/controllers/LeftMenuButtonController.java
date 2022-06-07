package hhccco.plutus.controllers;

import hhccco.plutus.models.TableDataModel;
import hhccco.plutus.views.MovementInsertForm;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TableView;

public class LeftMenuButtonController implements EventHandler<ActionEvent> {
    private TableView tableData;
    public LeftMenuButtonController(Node tableData) {
        this.tableData = (TableView) tableData;
    }
    @Override
    public void handle(ActionEvent actionEvent) {
        new MovementInsertForm();

        TableDataModel newEntry = new TableDataModel("Test movimento", "123", "1000'000", "300");
        tableData.getItems().addAll(newEntry);
    }
}
