package hhccco.plutus.views;

import hhccco.plutus.components.Body;
import hhccco.plutus.controllers.BankFormController;
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
 * BankForm is the view of the form view for CRUD operations on Banks
 */
public class BankForm extends GridPane implements Struct {
    private Stage stage;
    public HashMap<String, Node> nodesObjects = new HashMap<>();
    private final VBox rightColumnLayout = new VBox();
    private final GridPane btnContainer = new GridPane();
    private final VBox btnGroupLeft = new VBox();
    private final VBox btnGroupRight = new VBox();

    Button saveBtn = new Button("Salva");
    Button cancelBtn = new Button("Annulla");
    Button editBtn = new Button("Modifica");
    Button removeBtn = new Button("Rimuovi");

    private boolean onEdit = false;

    public BankForm() {
        setStruct();
        initNodes();
        setNodesEvents();
        setBanksOptions();

        this.add(nodesObjects.get("banksList"), 0, 0);
        this.add(rightColumnLayout, 1, 0);

        btnGroupLeft.setSpacing(15);
        btnGroupLeft.setAlignment(Pos.TOP_LEFT);

        btnGroupRight.setSpacing(15);
        btnGroupRight.setAlignment(Pos.TOP_RIGHT);

        stage.show();
    }

    /**
     * Set BankForm structure
     */
    public void setStruct() {
        Scene scene = new Scene(this, 500, 300);
        stage = new Stage();
        stage.setScene(scene);

        /*
         mainRow is the main row of the view in BankForm
         everything (the 2 columns and nodes) is inside this row
         */
        RowConstraints mainRow = new RowConstraints();
        mainRow.setVgrow(Priority.ALWAYS);

        /*
        leftColumn is the left column of the layout in BankForm
        Used for the list of Banks
         */
        ColumnConstraints leftColumn = new ColumnConstraints();
        leftColumn.setPercentWidth(50);
        leftColumn.setHgrow(Priority.ALWAYS);

        /*
        rightColumn is the right column of the layout in BankForm
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
     * Init BankForm node objects
     */
    public void initNodes() {
        ListView<String> bankList = new ListView<>();
        Label banksFormTitle = new Label("Inserimento banca");

        nodesObjects.put("banksList", bankList);
        nodesObjects.put("banksFormTitle", banksFormTitle);

        rightColumnLayout.getChildren().add(0, nodesObjects.get("banksFormTitle"));

        String[] optionLabels = {"Banca", "Numero Conto", "Tipo Conto"};
        String[] optionTechnicalLabels = {"bank", "accountNumber", "accountType"};

        //Set form (Label | input) group
        for (int i = 0; i < optionLabels.length; i++) {
            Label newLabel = new Label(optionLabels[i]);
            TextField newTextField = new TextField();
            VBox container = new VBox();

            newLabel.getStyleClass().add("formLabel");
            container.getChildren().addAll(newLabel, newTextField);

            nodesObjects.put(optionTechnicalLabels[i] + "Label", newLabel);
            nodesObjects.put(optionTechnicalLabels[i] + "Input", newTextField);

            rightColumnLayout.getChildren().add(container);
        }

        btnGroupLeft.getChildren().addAll(saveBtn, cancelBtn);
        btnGroupRight.getChildren().add(editBtn);

        btnContainer.add(btnGroupLeft, 0, 0);
        btnContainer.add(btnGroupRight, 1, 0);

        rightColumnLayout.getChildren().add(btnContainer);
    }

    /**
     * Set onAction node objects events
     */
    private void setNodesEvents() {
        ((ListView<?>) nodesObjects.get("banksList")).getSelectionModel().selectedItemProperty().addListener((ObservableValue) -> {
            if(onEdit) setSelectedBankData();
        });

        saveBtn.setOnAction(new BankFormController(this));
        cancelBtn.setOnAction(event -> close());
        editBtn.setOnAction(event -> onEdit());
        removeBtn.setOnAction(new BankFormController(this));
    }

    /**
     * Populate Bank list
     */
    public void setBanksOptions() {
        try {
            ResultSet rs = Body.dbConn.getBanks();
            ListView<String> listView = (ListView<String>) nodesObjects.get("banksList");

            listView.getItems().clear();

            while(rs.next()) {
                listView.getItems().add(rs.getString("name"));
            }

            rs.close();
        } catch (SQLException e){
            System.err.println("SQLite POPULATE List Banks Options error: \n\t" + e);
        }
    }

    /**
     * Set selected Bank metadata on left Form (Edit)
     */
    private void setSelectedBankData() {
        String selectedBank = (String) ((ListView<?>)nodesObjects.get("banksList")).getSelectionModel().getSelectedItem();

        try {
            ResultSet rs = Body.dbConn.getBanks(selectedBank);

            ((TextField)nodesObjects.get("bankInput")).setText(rs.getString("name"));
            ((TextField)nodesObjects.get("accountNumberInput")).setText(rs.getString("accountNumber"));
            ((TextField)nodesObjects.get("accountTypeInput")).setText(rs.getString("accountType"));

            rs.close();
        } catch (SQLException e) {
            System.out.println("SQLite SET METADATA for Selected Bank on Form error: " + e);
        }
    }

    /**
     * Set BankForm on edit
     */
    private void onEdit() {
        onEdit = true;

        saveBtn.setText("Aggiorna");
        ((Label)nodesObjects.get("banksFormTitle")).setText("Modifica banca");

        btnGroupRight.getChildren().remove(editBtn);
        btnGroupRight.getChildren().add(removeBtn);

        //Select by default the first item on list when Form on edit
        ((ListView<?>)nodesObjects.get("banksList")).getSelectionModel().select(0);

        setSelectedBankData();
    }

    /**
     * This method is used to exit from edit mode
     * and to close the BankForm view
     */
    public void close() {
        if(onEdit) {
            onEdit = false;

            saveBtn.setText("Salva");
            ((Label)nodesObjects.get("banksFormTitle")).setText("Inserimento banca");
            ((ListView<?>)nodesObjects.get("banksList")).getSelectionModel().clearSelection();

            //Clear form input fields
            ((TextField)nodesObjects.get("bankInput")).setText("");
            ((TextField)nodesObjects.get("accountNumberInput")).setText("");
            ((TextField)nodesObjects.get("accountTypeInput")).setText("");

            btnGroupRight.getChildren().add(editBtn);
            btnGroupRight.getChildren().remove(removeBtn);
        } else {
            stage.close();
        }
    }
}
