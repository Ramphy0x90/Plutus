package hhccco.plutus.views;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
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

    public BankForm() {
        setStruct();
        initNodes();

        this.add(nodesObjects.get("banksList"), 0, 0);
        this.add(rightColumnLayout, 1, 0);

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

        for(int i = 0; i < optionLabels.length; i++) {
            Label newLabel = new Label(optionLabels[i]);
            TextField newTextField = new TextField();
            VBox container = new VBox();

            newLabel.getStyleClass().add("formLabel");
            container.getChildren().addAll(newLabel, newTextField);

            nodesObjects.put(optionLabels[i].toLowerCase() + "Label", newLabel);
            nodesObjects.put(optionLabels[i].toLowerCase() + "Input", newTextField);

            rightColumnLayout.getChildren().add(container);
        }

        ((ListView) nodesObjects.get("banksList")).getItems().add("Hello");
        ((ListView) nodesObjects.get("banksList")).getItems().add("Hello");
        ((ListView) nodesObjects.get("banksList")).getItems().add("Hello");
        ((ListView) nodesObjects.get("banksList")).getItems().add("Hello");
    }
}
