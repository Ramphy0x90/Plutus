package hhccco.plutus.controllers;

import hhccco.plutus.models.TableDataModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LeftMenuButtonController implements EventHandler<ActionEvent> {
    private TableView tableData;
    public LeftMenuButtonController(Node tableData) {
        this.tableData = (TableView) tableData;
    }
    @Override
    public void handle(ActionEvent actionEvent) {
        VBox coso = new VBox();
        coso.getChildren().add(new Label("hello world"));
        Scene test = new Scene(coso, 500, 500);
        Stage fin = new Stage();

        TableDataModel newEntry = new TableDataModel("Test movimento", "123", "1000'000", "300");
        tableData.getItems().addAll(newEntry);

        fin.setScene(test);
        fin.show();
    }
}
