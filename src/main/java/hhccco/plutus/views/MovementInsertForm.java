package hhccco.plutus.views;

import hhccco.plutus.models.TableDataModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;

public class MovementInsertForm extends GridPane {
    private Stage stage;
    private Scene scene;
    private HBox btnGroup;
    HashMap<String, Node> nodesObjects = new HashMap<>();
    private String[] optionLabels = {"Movimento", "CC", "Versamento", "Prelevamento"};
    private ObservableList tableDataItems;

    public MovementInsertForm(ObservableList tableDataItems) {
        this.setVgap(18);
        this.setPadding(new Insets(10));

        this.setAlignment(Pos.CENTER);

        this.tableDataItems = tableDataItems;

        setStruct();
        initNodes();

        stage.show();
    }

    private void setStruct() {
        stage = new Stage();
        scene = new Scene(this, 300, 300);

        btnGroup = new HBox();
        btnGroup.setSpacing(15);
        btnGroup.setAlignment(Pos.CENTER);

        stage.setScene(scene);
    }

    private void initNodes() {
        for(int i = 0; i < optionLabels.length; i++) {
            Label newLabel = new Label(optionLabels[i]);
            TextField newTextField = new TextField();
            VBox container = new VBox();

            newLabel.getStyleClass().add("formLabel");
            container.getChildren().addAll(newLabel, newTextField);

            nodesObjects.put(optionLabels[i].toLowerCase() + "Label", newLabel);
            nodesObjects.put(optionLabels[i].toLowerCase() + "Input", newTextField);

            this.add(container, 0, i);
        }

        Button saveBtn = new Button("Salva");
        Button cancelBtn = new Button("Cancella");

        saveBtn.setOnAction(event -> this.saveData());
        cancelBtn.setOnAction(event -> this.stage.close());

        btnGroup.getChildren().addAll(saveBtn, cancelBtn);

        this.add(btnGroup, 0, optionLabels.length + 1);
    }

    private void saveData() {
        TableDataModel newEntry = new TableDataModel(
                ((TextField) nodesObjects.get("movimentoInput")).getText(),
                ((TextField) nodesObjects.get("ccInput")).getText(),
                ((TextField) nodesObjects.get("versamentoInput")).getText(),
                ((TextField) nodesObjects.get("prelevamentoInput")).getText()
        );
        this.tableDataItems.add(newEntry);
        this.stage.close();
    }
}
