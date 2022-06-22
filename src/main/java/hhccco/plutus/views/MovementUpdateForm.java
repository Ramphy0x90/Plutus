package hhccco.plutus.views;

import hhccco.plutus.components.Body;
import hhccco.plutus.controllers.MovementFormController;
import hhccco.plutus.models.TableDataModel;
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

import java.time.LocalDate;
import java.util.HashMap;

public class MovementUpdateForm extends GridPane {
    public int id;
    private Stage stage;
    private Scene scene;
    private HBox btnGroup;
    public HashMap<String, Node> nodesObjects = new HashMap<>();
    private String[] optionLabels = {"Movimento", "CC", "Versamento", "Prelevamento"};

    public MovementUpdateForm() {
        this.setVgap(18);
        this.setPadding(new Insets(10));

        this.setAlignment(Pos.CENTER);

        setStruct();
        initNodes();
        setData();

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

        Button saveBtn = new Button("Aggiorna");
        Button cancelBtn = new Button("Cancella");

        saveBtn.setOnAction(new MovementFormController(this));
        cancelBtn.setOnAction(event -> this.stage.close());

        btnGroup.getChildren().addAll(saveBtn, cancelBtn);

        this.add(btnGroup, 0, optionLabels.length + 1);
    }

    private void setData() {
        TableView tableData = (TableView) Body.nodesObjects.get("tableData");
        TableDataModel dataModel = (TableDataModel) tableData.getSelectionModel().getSelectedItem();

        id = dataModel.getId();
        ((TextField) nodesObjects.get("movimentoInput")).setText(dataModel.getMovimento());
        ((TextField) nodesObjects.get("ccInput")).setText(dataModel.getCc());
        ((TextField) nodesObjects.get("versamentoInput")).setText(Double.toString(dataModel.getVersamento()));
        ((TextField) nodesObjects.get("prelevamentoInput")).setText(Double.toString(dataModel.getPrelevamento()));
    }

    public void closeWindow() {
        this.stage.close();
    }
}
