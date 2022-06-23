package hhccco.plutus.views;

import hhccco.plutus.components.Body;
import hhccco.plutus.controllers.CcFormController;
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CcForm extends GridPane {
    private Stage stage;
    private Scene scene;
    public HashMap<String, Node> nodesObjects = new HashMap<>();
    private String[] optionLabels = {"Cc", "Descrizione"};
    private final VBox rightColumnLayout = new VBox();
    private GridPane btnContainer = new GridPane();
    private VBox btnGroupLeft = new VBox();
    private VBox btnGroupRight = new VBox();

    Button saveBtn = new Button("Salva");
    Button cancelBtn = new Button("Annulla");
    Button editBtn = new Button("Modifica");
    Button removeBtn = new Button("Rimuovi");

    private boolean onEdit = false;

    public CcForm() {
        setStruct();
        initNodes();
        setCcOptions();

        this.add(nodesObjects.get("ccList"), 0, 0);
        this.add(rightColumnLayout, 1, 0);

        btnGroupLeft.setSpacing(15);
        btnGroupLeft.setAlignment(Pos.TOP_LEFT);

        btnGroupRight.setSpacing(15);
        btnGroupRight.setAlignment(Pos.TOP_RIGHT);

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

        rightColumnLayout.setSpacing(15);
        rightColumnLayout.setPadding(new Insets(10));

        this.getRowConstraints().add(mainRow);
        this.getColumnConstraints().add(0, leftColumn);
        this.getColumnConstraints().add(1, rightColumn);

        btnContainer.getColumnConstraints().addAll(leftColumn, rightColumn);

        stage.setScene(scene);
    }

    private void initNodes() {
        nodesObjects.put("ccList", new ListView<>());
        ((ListView)nodesObjects.get("ccList")).getSelectionModel().selectedItemProperty().addListener((ObservableValue) -> {
            if(onEdit) {
                setSelectedCcData();
            }
        });

        nodesObjects.put("ccFormTitle", new Label("Inserimento CC"));

        rightColumnLayout.getChildren().add(nodesObjects.get("ccFormTitle"));

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

        saveBtn.setOnAction(new CcFormController(this));
        cancelBtn.setOnAction(event -> close());
        editBtn.setOnAction(event -> onEdit());
        removeBtn.setOnAction(new CcFormController(this));

        btnGroupLeft.getChildren().addAll(saveBtn, cancelBtn);
        btnGroupRight.getChildren().addAll(editBtn);

        btnContainer.add(btnGroupLeft, 0, 0);
        btnContainer.add(btnGroupRight, 1, 0);

        rightColumnLayout.getChildren().add(btnContainer);
    }

    public void setCcOptions() {
        try {
            ResultSet rs = Body.dbConn.getCcs();
            ((ListView)nodesObjects.get("ccList")).getItems().clear();

            while(rs.next()) {
                ((ListView)nodesObjects.get("ccList")).getItems().add(rs.getString("cc"));
            }
        } catch (SQLException e){
            System.err.println("SQLite error: \n\t" + e);
        }
    }

    private void setSelectedCcData() {
        String selectedCc = (String) ((ListView)nodesObjects.get("ccList")).getSelectionModel().getSelectedItem();

        try {
            ResultSet rs = Body.dbConn.getCcs(selectedCc);

            ((TextField)nodesObjects.get("ccInput")).setText(rs.getString("cc"));
            ((TextField)nodesObjects.get("descrizioneInput")).setText(rs.getString("description"));

            rs.close();
        } catch (SQLException e) {
            System.out.println("SQLite INSERT error: " + e);
        }
    }

    private void onEdit() {
        onEdit = true;
        saveBtn.setText("Aggiorna");
        ((Label)nodesObjects.get("ccFormTitle")).setText("Modifica CC");

        btnGroupRight.getChildren().remove(editBtn);
        btnGroupRight.getChildren().add(removeBtn);

        ((ListView)nodesObjects.get("ccList")).getSelectionModel().select(0);
        setSelectedCcData();
    }

    public void close() {
        if(onEdit) {
            onEdit = false;
            saveBtn.setText("Salva");
            ((Label)nodesObjects.get("ccFormTitle")).setText("Inserimento CC");
            ((ListView)nodesObjects.get("ccList")).getSelectionModel().clearSelection();

            ((TextField)nodesObjects.get("ccInput")).setText("");
            ((TextField)nodesObjects.get("descrizioneInput")).setText("");

            btnGroupRight.getChildren().add(editBtn);
            btnGroupRight.getChildren().remove(removeBtn);
        } else {
            stage.close();
        }
    }
}
