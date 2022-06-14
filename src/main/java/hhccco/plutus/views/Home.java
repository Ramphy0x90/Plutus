package hhccco.plutus.views;

import hhccco.plutus.Main;
import hhccco.plutus.components.Body;
import hhccco.plutus.components.Header;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import hhccco.plutus.Env;

public class Home extends GridPane {
    private final Scene scene;

    public Home() {
        //scene = new Scene(this, Env.screenWidth, Env.screenHeight);
        scene = new Scene(this, 900, 600);
        scene.getStylesheets().add(String.valueOf(Main.class.getResource("home-styles.css")));

        this.setVgap(18);
        this.setPadding(new Insets(10));
        this.setId("home");

        setStruct();
    }

    private void setStruct(){
        ColumnConstraints mainColumn = new ColumnConstraints();
        RowConstraints headerRow = new RowConstraints();
        RowConstraints bodyRow = new RowConstraints();

        mainColumn.setPercentWidth(100);
        headerRow.setPrefHeight(60);
        bodyRow.setVgrow(Priority.ALWAYS);

        this.getColumnConstraints().addAll(mainColumn);
        this.getRowConstraints().addAll(headerRow, bodyRow);

        this.add(new Header(), 0, 0);
        this.add(new Body(), 0, 1);
    }

    public Scene getViewScene() {
        return scene;
    }
}
