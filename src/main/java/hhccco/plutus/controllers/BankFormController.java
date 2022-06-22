package hhccco.plutus.controllers;

import hhccco.plutus.components.Body;
import hhccco.plutus.models.BankModel;
import hhccco.plutus.util.DBconnection;
import hhccco.plutus.views.BankForm;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class BankFormController implements EventHandler<ActionEvent> {
    DBconnection dbConn = Body.dbConn;
    BankForm bankForm;

    public BankFormController(BankForm bankForm) {
        this.bankForm = bankForm;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        String btnLabel = ((Button) actionEvent.getSource()).getText();

        switch (btnLabel) {
            case "Salva":
                try {
                    BankModel bankModel = new BankModel(
                            ((TextField) bankForm.nodesObjects.get("bancaInput")).getText(),
                            ((TextField) bankForm.nodesObjects.get("numero contoInput")).getText(),
                            ((TextField) bankForm.nodesObjects.get("tipo contoInput")).getText()
                    );

                    dbConn.insertBank(bankModel);
                    Body.populateBanks();

                    bankForm.close();
                } catch (SQLException e) {
                    System.out.println("SQLite INSERT error: " + e);
                }

                break;

            case "Aggiorna":
                try {
                    String oldBankId = (String) ((ListView)bankForm.nodesObjects.get("banksList")).getSelectionModel().getSelectedItem();

                    BankModel bankModel = new BankModel(
                            ((TextField) bankForm.nodesObjects.get("bancaInput")).getText(),
                            ((TextField) bankForm.nodesObjects.get("numero contoInput")).getText(),
                            ((TextField) bankForm.nodesObjects.get("tipo contoInput")).getText()
                    );

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
                    String bankId = (String) ((ListView)bankForm.nodesObjects.get("banksList")).getSelectionModel().getSelectedItem();

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