package hhccco.plutus.components;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.sql.SQLException;
import java.util.HashMap;

public class BanksHomeNavigation extends HBox {
    static HashMap<String, Node> nodesObjects = new HashMap<>();

    public BanksHomeNavigation() {
        this.setId("banksHomeNavigation");
        this.setSpacing(5);
        this.setPadding(new Insets(10));

        initNodes();
        addChildren();
    }

    private void initNodes(){
        nodesObjects.put("bankLabel", new Label("Banca"));
        nodesObjects.put("bankPicker", new ComboBox<String>());
        nodesObjects.put("accountNumber", new Label("Num. Conto"));
        nodesObjects.put("accountNumberField", new TextField());
        nodesObjects.put("accountType", new Label("Tipo Conto"));
        nodesObjects.put("accountTypeField", new TextField());

        ((ComboBox<?>) nodesObjects.get("bankPicker")).setPrefWidth(150);
        ((ComboBox<?>) nodesObjects.get("bankPicker")).setOnAction(event -> updateBank());

        ((TextField) nodesObjects.get("accountNumberField")).setPrefWidth(100);
        ((TextField) nodesObjects.get("accountTypeField")).setPrefWidth(100);

        ((TextField) nodesObjects.get("accountNumberField")).setEditable(false);
        ((TextField) nodesObjects.get("accountTypeField")).setEditable(false);
    }

    private void addChildren() {
        Separator separator1 = new Separator(Orientation.VERTICAL);
        Separator separator2 = new Separator(Orientation.VERTICAL);

        separator1.getStyleClass().add("separator");
        separator2.getStyleClass().add("separator");

        this.getChildren().add(nodesObjects.get("bankLabel"));
        this.getChildren().add(nodesObjects.get("bankPicker"));
        this.getChildren().add(separator1);
        this.getChildren().add(nodesObjects.get("accountNumber"));
        this.getChildren().add(nodesObjects.get("accountNumberField"));
        this.getChildren().add(separator2);
        this.getChildren().add(nodesObjects.get("accountType"));
        this.getChildren().add(nodesObjects.get("accountTypeField"));
    }

    private void updateBank() {
        String bankName = (String) ((ComboBox<?>) nodesObjects.get("bankPicker")).getValue();

        try {
            Body.setDataBySelectedBank(bankName);
        } catch (SQLException e){
            System.err.println("SQLite error: \n\t" + e);
        }
    }
}
