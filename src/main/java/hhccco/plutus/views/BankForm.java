package hhccco.plutus.views;

import hhccco.plutus.controllers.BankFormController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.HashMap;

public class BankForm extends GridPane {
    private Stage stage;
    private Scene scene;
    public HashMap<String, Node> nodesObjects = new HashMap<>();
    private String[] optionLabels = {"Banca", "Numero Conto", "Tipo Conto"};
    private final VBox rightColumnLayout = new VBox();
    private HBox btnGroup = new HBox();

    public BankForm() {
        setStruct();
        initNodes();

        this.add(nodesObjects.get("banksList"), 0, 0);
        this.add(rightColumnLayout, 1, 0);

        btnGroup.setSpacing(15);
        btnGroup.setAlignment(Pos.CENTER);

        stage.show();
    }

    private void setStruct() {
        stage = new Stage();
        scene = new Scene(this, 500, 300);

        RowConstraints mainRow = new RowConstraints();
        mainRow.setVgrow(Priority.ALWAYS);

        ColumnConstraints leftColumn = new ColumnConstraints();
        leftColumn.setPercentWidth(50);
        leftColumn.setHgrow(Priority.ALWAYS);

        ColumnConstraints rightColumn = new ColumnConstraints();
        rightColumn.setPercentWidth(50);
        rightColumn.setHgrow(Priority.ALWAYS);

        rightColumnLayout.setPadding(new Insets(10));

        this.getRowConstraints().add(mainRow);
        this.getColumnConstraints().add(0, leftColumn);
        this.getColumnConstraints().add(1, rightColumn);

        stage.setScene(scene);
    }

    private void initNodes() {
        nodesObjects.put("banksList", new ListView<>());
        nodesObjects.put("banksFormTitle", new Label("Inserimento banca"));
        //((ListView)nodesObjects.get("banksList")).ed;

        rightColumnLayout.getChildren().add(nodesObjects.get("banksFormTitle"));

        for (String optionLabel : optionLabels) {
            Label newLabel = new Label(optionLabel);
            TextField newTextField = new TextField();
            VBox container = new VBox();

            newLabel.getStyleClass().add("formLabel");
            container.getChildren().addAll(newLabel, newTextField);

            nodesObjects.put(optionLabel.toLowerCase() + "Label", newLabel);
            nodesObjects.put(optionLabel.toLowerCase() + "Input", newTextField);

            rightColumnLayout.getChildren().add(container);
        }

        Button saveBtn = new Button("Salva");
        Button cancelBtn = new Button("Cancella");

        saveBtn.setOnAction(new BankFormController(this));
        cancelBtn.setOnAction(event -> this.stage.close());

        btnGroup.getChildren().addAll(saveBtn, cancelBtn);

        rightColumnLayout.getChildren().add(btnGroup);
    }

    public void close() {
        stage.close();
    }
}
