package hhccco.plutus.controllers;

import hhccco.plutus.components.Body;
import hhccco.plutus.components.LeftMenu;
import hhccco.plutus.views.BankForm;
import hhccco.plutus.views.CcForm;
import hhccco.plutus.views.MovementForm;
import hhccco.plutus.views.MovementUpdateForm;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;

import java.util.HashMap;

public class LeftMenuButtonController implements EventHandler<ActionEvent> {
    private HashMap<String, Node> leftMenuObjects;

    public LeftMenuButtonController(HashMap<String, Node> leftMenuObjects) {
        this.leftMenuObjects = leftMenuObjects;
    }
    @Override
    public void handle(ActionEvent actionEvent) {
        String btnLabel = ((Button) actionEvent.getSource()).getText();
        TableView tableData = (TableView) Body.nodesObjects.get("tableData");

        switch(btnLabel) {
            case "Nuovo":
                DatePicker date = (DatePicker) leftMenuObjects.get("datePicker");
                new MovementForm(date.getValue());

                break;
            case "Modifica":
                if(!tableData.getSelectionModel().isEmpty()) {
                    new MovementUpdateForm();
                }

                break;
            case "Banche":
                new BankForm();

                break;
            case "CC":
                new CcForm();

                break;
        }
    }
}
