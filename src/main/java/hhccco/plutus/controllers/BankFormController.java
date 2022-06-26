package hhccco.plutus.controllers;

import hhccco.plutus.components.Body;
import hhccco.plutus.models.BankModel;
import hhccco.plutus.util.DBConnection;
import hhccco.plutus.views.BankForm;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class BankFormController implements EventHandler<ActionEvent> {
    DBConnection dbConn = Body.dbConn;
    BankForm bankForm;

    public BankFormController(BankForm bankForm) {
        this.bankForm = bankForm;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        // Get the selected button text
        String btnLabel = ((Button) actionEvent.getSource()).getText();

        BankModel bankModel = new BankModel(
                ((TextField) bankForm.nodesObjects.get("bankInput")).getText(),
                ((TextField) bankForm.nodesObjects.get("accountNumberInput")).getText(),
                ((TextField) bankForm.nodesObjects.get("accountTypeInput")).getText()
        );

        switch (btnLabel) {
            case "Salva":
                try {
                    dbConn.insertBank(bankModel);
                    Body.populateBanks();

                    bankForm.close();
                } catch (SQLException e) {
                    System.out.println("SQLite INSERT error: " + e);
                }

                break;
            case "Aggiorna":
                try {
                    String oldBankId = ((ListView<String>)bankForm.nodesObjects.get("banksList")).getSelectionModel().getSelectedItem();

                    dbConn.updateBank(oldBankId, bankModel);
                    Body.populateBanks();
                    bankForm.setBanksOptions();

                    bankForm.close();
                } catch (SQLException e) {
                    System.out.println("SQLite UPDATE error: " + e);
                }

                break;
            case "Rimuovi":
                try {
                    String bankId = ((ListView<String>)bankForm.nodesObjects.get("banksList")).getSelectionModel().getSelectedItem();

                    dbConn.removeBank(bankId);
                    Body.populateBanks();
                    bankForm.setBanksOptions();

                    bankForm.close();
                } catch (SQLException e) {
                    System.out.println("SQLite UPDATE error: " + e);
                }

                break;
        }
    }
}
