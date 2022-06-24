package hhccco.plutus.components;

import hhccco.plutus.controllers.LeftMenuButtonController;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

public class LeftMenu extends VBox {
    public static HashMap<String, Node> nodesObjects = new HashMap<>();
    final String[] buttonsText = {"Nuovo", "Modifica", "Banche", "CC"};
    public HashMap<String, Node> parentNodes;

    public LeftMenu() {
        this.setId("leftMenu");
        this.setPrefWidth(200);
        this.setSpacing(5);
        this.setPadding(new Insets(10));

        this.parentNodes = Body.nodesObjects;

        initNodes();
        addChildren();
    }

    private void initNodes(){
        nodesObjects.put("dateLabel", new Label("Data"));
        nodesObjects.put("datePicker", new DatePicker());
        nodesObjects.put("separator", new Separator(Orientation.HORIZONTAL));
        nodesObjects.put("separator1", new Separator(Orientation.HORIZONTAL));

        ((DatePicker) nodesObjects.get("datePicker")).setValue(LocalDate.now());
        ((DatePicker) nodesObjects.get("datePicker")).setOnAction(event -> updateDate());

        nodesObjects.put("movementManagementLabel", new Label("Gestione movimenti"));
        nodesObjects.put("constLabel", new Label("Costanti"));

        nodesObjects.get("separator").getStyleClass().add("v-separator");
        nodesObjects.get("separator1").getStyleClass().add("v-separator");

        nodesObjects.get("dateLabel").getStyleClass().add("sub-title");
        nodesObjects.get("movementManagementLabel").getStyleClass().add("sub-title");
        nodesObjects.get("constLabel").getStyleClass().add("sub-title");

        for(String btnText: buttonsText){
            Button newButton = new Button(btnText);
            newButton.getStyleClass().add("leftMenuBtn");
            newButton.setPrefWidth(this.getPrefWidth());
            newButton.setOnAction(new LeftMenuButtonController(nodesObjects));

            nodesObjects.put(btnText.toLowerCase() + "Btn", newButton);
        }
    }
    private void addChildren() {
        this.getChildren().add(nodesObjects.get("dateLabel"));
        this.getChildren().add(nodesObjects.get("datePicker"));

        this.getChildren().add(nodesObjects.get("separator"));

        this.getChildren().add(nodesObjects.get("movementManagementLabel"));
        this.getChildren().add(nodesObjects.get("nuovoBtn"));
        this.getChildren().add(nodesObjects.get("modificaBtn"));

        this.getChildren().add(nodesObjects.get("separator1"));

        this.getChildren().add(nodesObjects.get("constLabel"));
        this.getChildren().add(nodesObjects.get("bancheBtn"));
        this.getChildren().add(nodesObjects.get("ccBtn"));
    }

    private void updateDate() {
        String newDate = String.valueOf(((DatePicker) nodesObjects.get("datePicker")).getValue());

        try {
            Body.populateDataTable(newDate);
        } catch (SQLException e){
            System.err.println("SQLite error: \n\t" + e);
        }
    }
}
