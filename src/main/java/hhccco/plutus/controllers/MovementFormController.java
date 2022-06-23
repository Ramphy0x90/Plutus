package hhccco.plutus.controllers;

import hhccco.plutus.components.BanksHomeNavigation;
import hhccco.plutus.components.Body;
import hhccco.plutus.components.LeftMenu;
import hhccco.plutus.models.TableDataModel;
import hhccco.plutus.util.DBconnection;
import hhccco.plutus.views.MovementForm;
import hhccco.plutus.views.MovementUpdateForm;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class MovementFormController implements EventHandler<ActionEvent> {
    DBconnection dbConn = Body.dbConn;
    MovementForm movementForm;
    MovementUpdateForm movementUpdateForm;
    public MovementFormController(MovementForm movementForm) {
        this.movementForm = movementForm;
    }
    public MovementFormController(MovementUpdateForm movementUpdateForm) {
        this.movementUpdateForm = movementUpdateForm;
    }
    @Override
    public void handle(ActionEvent actionEvent) {
        String btnLabel = ((Button) actionEvent.getSource()).getText();

        switch (btnLabel) {
            case "Salva":
                try {
                    String deposit = ((TextField) movementForm.nodesObjects.get("versamentoInput")).getText();
                    Double depositValue = deposit.length() == 0 ? 0 : Double.parseDouble(deposit);

                    String withdrawal = ((TextField) movementForm.nodesObjects.get("prelevamentoInput")).getText();
                    Double withdrawalValue = withdrawal.length() == 0 ? 0 : Double.parseDouble(withdrawal);

                    String bankId = ((ComboBox<String>) BanksHomeNavigation.nodesObjects.get("bankPicker")).getValue();

                    TableDataModel tableDataModel = new TableDataModel(
                            movementForm.movementDate,
                            ((TextField) movementForm.nodesObjects.get("movimentoInput")).getText(),
                            ((TextField) movementForm.nodesObjects.get("ccInput")).getText(),
                            depositValue,
                            withdrawalValue,
                            bankId);

                    dbConn.insertMovement(tableDataModel);
                    Body.populateDataTable(String.valueOf(movementForm.movementDate));

                    movementForm.closeWindow();
                } catch (SQLException e) {
                    System.out.println("SQLite INSERT error: " + e);
                }
                break;
            case "Aggiorna":
                try {
                    String deposit = ((TextField) movementUpdateForm.nodesObjects.get("versamentoInput")).getText();
                    Double depositValue = deposit.length() == 0 ? 0 : Double.parseDouble(deposit);

                    String withdrawal = ((TextField) movementUpdateForm.nodesObjects.get("prelevamentoInput")).getText();
                    Double withdrawalValue = withdrawal.length() == 0 ? 0 : Double.parseDouble(withdrawal);

                    DatePicker date = (DatePicker) LeftMenu.nodesObjects.get("datePicker");
                    String bankId = ((ComboBox<String>) BanksHomeNavigation.nodesObjects.get("bankPicker")).getValue();

                    TableDataModel tableDataModel = new TableDataModel(
                            date.getValue(),
                            ((TextField) movementUpdateForm.nodesObjects.get("movimentoInput")).getText(),
                            ((TextField) movementUpdateForm.nodesObjects.get("ccInput")).getText(),
                            depositValue,
                            withdrawalValue,
                            bankId);

                    tableDataModel.setId(movementUpdateForm.id);

                    dbConn.updateMovement(tableDataModel);
                    Body.populateDataTable(String.valueOf(date.getValue()));

                    movementUpdateForm.closeWindow();
                } catch (SQLException e) {
                    System.out.println("SQLite UPDATE error: " + e);
                }

                break;
            case "Rimuovi":
                try {
                    DatePicker date = (DatePicker) LeftMenu.nodesObjects.get("datePicker");

                    dbConn.removeMovement(movementUpdateForm.id);
                    Body.populateDataTable(String.valueOf(date.getValue()));

                    movementUpdateForm.closeWindow();
                } catch(SQLException e) {
                    System.out.println("SQLite DELETE error: " + e);
                }

                break;
        }
    }
}
