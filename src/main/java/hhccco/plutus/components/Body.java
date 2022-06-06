package hhccco.plutus.components;

import javafx.scene.Node;
import javafx.scene.layout.*;

import java.util.HashMap;

public class Body extends GridPane {
    HashMap<String, Node> nodesObjects = new HashMap<>();

    public Body() {
        this.setId("body");

        setStruct();
        initNodes();

        this.add(nodesObjects.get("leftMenu"), 0, 0);
        this.add(nodesObjects.get("tableData"), 1, 0);
    }

    private void setStruct() {
        ColumnConstraints leftColumn = new ColumnConstraints();
        leftColumn.setPrefWidth(200);

        ColumnConstraints centerColumn = new ColumnConstraints();
        centerColumn.setHgrow(Priority.ALWAYS);

        RowConstraints mainRow = new RowConstraints();
        mainRow.setVgrow(Priority.ALWAYS);

        this.getRowConstraints().add(mainRow);
        this.getColumnConstraints().add(0, leftColumn);
        this.getColumnConstraints().add(1, centerColumn);
    }

    private void initNodes() {
        nodesObjects.put("tableData", new TableData().getTable());
        nodesObjects.put("leftMenu", new LeftMenu(this.nodesObjects));
    }

    public HashMap<String, Node> getNodes() {
        return nodesObjects;
    }
}