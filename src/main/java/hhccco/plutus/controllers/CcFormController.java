package hhccco.plutus.controllers;

import hhccco.plutus.components.Body;
import hhccco.plutus.models.CcModel;
import hhccco.plutus.util.DBConnection;
import hhccco.plutus.views.CcForm;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class CcFormController implements EventHandler<ActionEvent> {
    DBConnection dbConn = Body.dbConn;
    CcForm ccForm;

    public CcFormController(CcForm ccForm) {
        this.ccForm = ccForm;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        String btnLabel = ((Button) actionEvent.getSource()).getText();

        switch (btnLabel) {
            case "Salva":
                try {
                    CcModel ccModel = new CcModel(
                            ((TextField) ccForm.nodesObjects.get("ccInput")).getText(),
                            ((TextField) ccForm.nodesObjects.get("descrizioneInput")).getText()
                    );

                    dbConn.insertCc(ccModel);

                    ccForm.close();
                } catch (SQLException e) {
                    System.out.println("SQLite INSERT error: " + e);
                }

                break;

            case "Aggiorna":
                try {
                    String oldCc = (String) ((ListView)ccForm.nodesObjects.get("ccList")).getSelectionModel().getSelectedItem();

                    CcModel ccModel = new CcModel(
                            ((TextField) ccForm.nodesObjects.get("ccInput")).getText(),
                            ((TextField) ccForm.nodesObjects.get("descrizioneInput")).getText()
                    );

                    dbConn.updateCc(oldCc, ccModel);
                    ccForm.setCcOptions();

                    ccForm.close();
                } catch (SQLException e) {
                    System.out.println("SQLite UPDATE error: " + e);
                }

                break;
            case "Rimuovi":
                try {
                    String ccId = (String) ((ListView)ccForm.nodesObjects.get("ccList")).getSelectionModel().getSelectedItem();

                    dbConn.removeCc(ccId);
                    ccForm.setCcOptions();

                    ccForm.close();
                } catch (SQLException e) {
                    System.out.println("SQLite UPDATE error: " + e);
                }

                break;
        }
    }
}
