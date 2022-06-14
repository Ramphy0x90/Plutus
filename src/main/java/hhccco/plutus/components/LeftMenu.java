package hhccco.plutus.components;

import hhccco.plutus.controllers.LeftMenuButtonController;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.HashMap;

public class LeftMenu extends VBox {
    HashMap<String, Node> nodesObjects = new HashMap<>();
    final String[] buttonsText = {"Nuovo", "Banche", "CC"};
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

        ((DatePicker) nodesObjects.get("datePicker")).setValue(LocalDate.now());
        nodesObjects.get("dateLabel").getStyleClass().add("sub-title");

        for(String btnText: buttonsText){
            Button newButton = new Button(btnText);
            newButton.getStyleClass().add("leftMenuBtn");
            newButton.setPrefWidth(this.getPrefWidth());
            newButton.setOnAction(new LeftMenuButtonController(nodesObjects, parentNodes.get("tableData")));

            nodesObjects.put(btnText.toLowerCase() + "Btn", newButton);
        }
    }
    private void addChildren() {
        this.getChildren().add(nodesObjects.get("dateLabel"));
        this.getChildren().add(nodesObjects.get("datePicker"));
        this.getChildren().add(nodesObjects.get("separator"));

        this.getChildren().add(nodesObjects.get("nuovoBtn"));
        this.getChildren().add(nodesObjects.get("bancheBtn"));
        this.getChildren().add(nodesObjects.get("ccBtn"));
    }
}
