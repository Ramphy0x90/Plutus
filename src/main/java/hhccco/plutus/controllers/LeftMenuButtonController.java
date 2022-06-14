package hhccco.plutus.controllers;

import hhccco.plutus.views.MovementForm;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;

import java.util.HashMap;

public class LeftMenuButtonController implements EventHandler<ActionEvent> {
    private HashMap<String, Node> leftMenuObjects;
    private TableView tableData;
    public LeftMenuButtonController(HashMap<String, Node> leftMenuObjects, Node tableData) {
        this.leftMenuObjects = leftMenuObjects;
        this.tableData = (TableView) tableData;
    }
    @Override
    public void handle(ActionEvent actionEvent) {
        String btnLabel = ((Button) actionEvent.getSource()).getText();

        switch(btnLabel) {
            case "Nuovo":
                DatePicker date = (DatePicker) leftMenuObjects.get("datePicker");
                new MovementForm(date.getValue());

                break;
            case "Banche":
                break;
            case "CC":
                break;
        }
    }
}
