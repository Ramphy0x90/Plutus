package hhccco.plutus.controllers;

import hhccco.plutus.components.Body;
import hhccco.plutus.models.TableDataModel;
import hhccco.plutus.util.DBconnection;
import hhccco.plutus.views.MovementForm;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class CRUDformController implements EventHandler<ActionEvent> {
    DBconnection dbConn = Body.dbConn;
    MovementForm movementForm;
    public  CRUDformController(MovementForm movementForm) {
        this.movementForm = movementForm;
    }
    @Override
    public void handle(ActionEvent actionEvent) {
        String btnLabel = ((Button) actionEvent.getSource()).getText();

        switch (btnLabel) {
            case "Salva":
                try {
                    TableDataModel tableDataModel = new TableDataModel(
                            movementForm.movementDate,
                            ((TextField) movementForm.nodesObjects.get("movimentoInput")).getText(),
                            ((TextField) movementForm.nodesObjects.get("ccInput")).getText(),
                            Double.parseDouble(((TextField) movementForm.nodesObjects.get("versamentoInput")).getText()),
                            Double.parseDouble(((TextField) movementForm.nodesObjects.get("prelevamentoInput")).getText()));

                    dbConn.insertMovement(tableDataModel);
                    Body.populateDataTable();

                    movementForm.closeWindow();
                } catch (SQLException e) {
                    System.out.println("SQLite INSERT error: " + e);
                }
                break;
            case "Aggiorna":
                break;
        }
    }
}
