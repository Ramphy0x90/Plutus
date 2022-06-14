package hhccco.plutus.controllers;

import hhccco.plutus.views.MovementInsertForm;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class LeftMenuButtonController implements EventHandler<ActionEvent> {
    private TableView tableData;
    public LeftMenuButtonController(Node tableData) {
        this.tableData = (TableView) tableData;
    }
    @Override
    public void handle(ActionEvent actionEvent) {
        String btnLabel = ((Button) actionEvent.getSource()).getText();

        switch(btnLabel) {
            case "Nuovo":
                new MovementInsertForm(tableData.getItems());

                break;
            case "Banche":
                break;
            case "CC":
                break;
        }
    }
}
