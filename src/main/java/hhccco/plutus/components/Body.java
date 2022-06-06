package hhccco.plutus.components;

import javafx.scene.layout.*;

public class Body extends GridPane {

    public Body() {
        this.setId("body");

        setStruct();
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

        this.add(new LeftMenu(), 0, 0);
        this.add(new TableData().getTable(), 1, 0);
    }
}
