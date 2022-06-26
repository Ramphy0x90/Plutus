package hhccco.plutus.views;

import hhccco.plutus.components.Body;
import hhccco.plutus.controllers.MovementFormController;
import hhccco.plutus.interfaces.Struct;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * MovementForm is the view of the form view for insert new movements
 */
public class MovementForm extends GridPane implements Struct {
    private Stage stage;
    private HBox btnGroup;
    public LocalDate movementDate;
    public HashMap<String, Node> nodesObjects = new HashMap<>();
    private final String[] optionLabels = {"Movimento", "Versamento", "Prelevamento"};

    /**
     * @param value this param is used to specify the date of the movement
     */
    public MovementForm(LocalDate value) {
        this.setVgap(18);
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER);

        this.movementDate = value;

        setStruct();
        initNodes();

        stage.show();
    }

    /**
     * Set MovementForm structure
     */
    public void setStruct() {
        Scene scene = new Scene(this, 300, 300);
        stage = new Stage();

        btnGroup = new HBox();
        btnGroup.setSpacing(15);
        btnGroup.setAlignment(Pos.CENTER);

        stage.setScene(scene);
    }

    /**
     * Init MovementForm node objects
     */
    public void initNodes() {
        for(int i = 0; i < optionLabels.length; i++) {
            int[] indexRef = {0, 2, 3};
            Label newLabel = new Label(optionLabels[i]);
            TextField newTextField = new TextField();
            VBox container = new VBox();

            newLabel.getStyleClass().add("formLabel");
            container.getChildren().addAll(newLabel, newTextField);

            nodesObjects.put(optionLabels[i].toLowerCase() + "Label", newLabel);
            nodesObjects.put(optionLabels[i].toLowerCase() + "Input", newTextField);

            this.add(container, 0, indexRef[i]);
        }

        Label newLabel = new Label("CC");
        ComboBox<String> newDropDown = new ComboBox<>();
        VBox container = new VBox();

        // Populate CC dropdown options from DB
        try {
            ResultSet rs = Body.dbConn.getCcs();

            while(rs.next()) {
                newDropDown.getItems().add(rs.getString("cc"));
            }

        } catch (SQLException e) {
            System.out.println("SQLite CC Dropdown error: " + e);
        }


        nodesObjects.put("ccLabel", newLabel);
        nodesObjects.put("ccInput", newDropDown);

        newLabel.getStyleClass().add("formLabel");
        container.getChildren().addAll(newLabel, newDropDown);

        this.add(container, 0, 1);

        Button saveBtn = new Button("Salva");
        Button cancelBtn = new Button("Cancella");

        saveBtn.setOnAction(new MovementFormController(this));
        cancelBtn.setOnAction(event -> this.stage.close());

        btnGroup.getChildren().addAll(saveBtn, cancelBtn);

        this.add(btnGroup, 0, optionLabels.length + 1);
    }

    public void closeWindow() {
        this.stage.close();
    }
}
