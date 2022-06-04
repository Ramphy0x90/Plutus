package hhccco.plutus.components;

import javafx.scene.layout.BorderPane;

public class Body extends BorderPane {

    public Body() {
        this.setId("body");

        this.setLeft(new LeftMenu());
        this.setCenter(new TableData().getTable());
    }
}
