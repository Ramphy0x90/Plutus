package hhccco.plutus.views;

import hhccco.plutus.components.Body;
import hhccco.plutus.controllers.CcFormController;
import hhccco.plutus.interfaces.Struct;
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

/**
 * CcForm is the view of the form view for CRUD operations on CC
 */
public class CcForm extends GridPane implements Struct {
    private Stage stage;
    public HashMap<String, Node> nodesObjects = new HashMap<>();
    private final String[] optionLabels = {"Cc", "Descrizione"};
    private final VBox rightColumnLayout = new VBox();
    private final GridPane btnContainer = new GridPane();
    private final VBox btnGroupLeft = new VBox();
    private final VBox btnGroupRight = new VBox();

    Button saveBtn = new Button("Salva");
    Button cancelBtn = new Button("Annulla");
    Button editBtn = new Button("Modifica");
    Button removeBtn = new Button("Rimuovi");

    private boolean onEdit = false;

    public CcForm() {
        setStruct();
        initNodes();
        setNodesEvents();
        setCcOptions();

        this.add(nodesObjects.get("ccList"), 0, 0);
        this.add(rightColumnLayout, 1, 0);

        btnGroupLeft.setSpacing(15);
        btnGroupLeft.setAlignment(Pos.TOP_LEFT);

        btnGroupRight.setSpacing(15);
        btnGroupRight.setAlignment(Pos.TOP_RIGHT);

        stage.show();
    }

    /**
     * Set CcForm structure
     */
    public void setStruct() {
        Scene scene = new Scene(this, 500, 300);
        stage = new Stage();
        stage.setScene(scene);

        /*
         mainRow is the main row of the view in CcForm
         everything (the 2 columns and nodes) is inside this row
         */
        RowConstraints mainRow = new RowConstraints();
        mainRow.setVgrow(Priority.ALWAYS);

        /*
        leftColumn is the left column of the layout in CcForm
        Used for the list of CCs
         */
        ColumnConstraints leftColumn = new ColumnConstraints();
        leftColumn.setPercentWidth(50);
        leftColumn.setHgrow(Priority.ALWAYS);

        /*
        rightColumn is the right column of the layout in CcForm
        Used for the form
         */
        ColumnConstraints rightColumn = new ColumnConstraints();
        rightColumn.setPercentWidth(50);
        rightColumn.setHgrow(Priority.ALWAYS);

        rightColumnLayout.setSpacing(15);
        rightColumnLayout.setPadding(new Insets(10));

        this.getRowConstraints().add(mainRow);
        this.getColumnConstraints().add(0, leftColumn);
        this.getColumnConstraints().add(1, rightColumn);

        btnContainer.getColumnConstraints().addAll(leftColumn, rightColumn);
    }

    /**
     * Init CcForm node objects
     */
    public void initNodes() {
        nodesObjects.put("ccList", new ListView<>());
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

        btnGroupLeft.getChildren().addAll(saveBtn, cancelBtn);
        btnGroupRight.getChildren().addAll(editBtn);

        btnContainer.add(btnGroupLeft, 0, 0);
        btnContainer.add(btnGroupRight, 1, 0);

        rightColumnLayout.getChildren().add(btnContainer);
    }

    /**
     * Set onAction node objects events
     */
    private void setNodesEvents() {
        ((ListView<?>)nodesObjects.get("ccList")).getSelectionModel().selectedItemProperty().addListener((ObservableValue) -> {
            if(onEdit) setSelectedCcData();
        });

        saveBtn.setOnAction(new CcFormController(this));
        cancelBtn.setOnAction(event -> close());
        editBtn.setOnAction(event -> onEdit());
        removeBtn.setOnAction(new CcFormController(this));
    }

    /**
     * Populate CC list
     */
    public void setCcOptions() {
        try {
            ResultSet rs = Body.dbConn.getCcs();
            ((ListView<?>)nodesObjects.get("ccList")).getItems().clear();

            while(rs.next()) {
                ((ListView<String>)nodesObjects.get("ccList")).getItems().add(rs.getString("cc"));
            }
        } catch (SQLException e){
            System.err.println("SQLite POPULATE List CC Options  error: \n\t" + e);
        }
    }

    /**
     * Set selected CC metadata on left Form (Edit)
     */
    private void setSelectedCcData() {
        String selectedCc = (String) ((ListView<?>)nodesObjects.get("ccList")).getSelectionModel().getSelectedItem();

        try {
            ResultSet rs = Body.dbConn.getCcs(selectedCc);

            ((TextField)nodesObjects.get("ccInput")).setText(rs.getString("cc"));
            ((TextField)nodesObjects.get("descrizioneInput")).setText(rs.getString("description"));

            rs.close();
        } catch (SQLException e) {
            System.out.println("SQLite INSERT error: " + e);
        }
    }

    /**
     * Set CcForm on edit
     */
    private void onEdit() {
        onEdit = true;

        saveBtn.setText("Aggiorna");
        ((Label)nodesObjects.get("ccFormTitle")).setText("Modifica CC");

        btnGroupRight.getChildren().remove(editBtn);
        btnGroupRight.getChildren().add(removeBtn);

        ((ListView<?>)nodesObjects.get("ccList")).getSelectionModel().select(0);
        setSelectedCcData();
    }

    /**
     * This method is used to exit from edit mode
     * and to close the CcForm view
     */
    public void close() {
        if(onEdit) {
            onEdit = false;

            saveBtn.setText("Salva");
            ((Label)nodesObjects.get("ccFormTitle")).setText("Inserimento CC");
            ((ListView)nodesObjects.get("ccList")).getSelectionModel().clearSelection();

            //Clear form input fields
            ((TextField)nodesObjects.get("ccInput")).setText("");
            ((TextField)nodesObjects.get("descrizioneInput")).setText("");

            btnGroupRight.getChildren().add(editBtn);
            btnGroupRight.getChildren().remove(removeBtn);
        } else {
            stage.close();
        }
    }
}
