package hhccco.plutus.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Header extends VBox {
    public Header() {
        this.setSpacing(5);
        this.setPadding(new Insets(10));
        this.setPrefHeight(60);
        this.setAlignment(Pos.CENTER);

        this.setId("header");

        Label mainTitle = new Label("Gestione Conto Corrente");
        mainTitle.getStyleClass().add("main-title");
        mainTitle.getStyleClass().add("text-light");

        Label subTitle = new Label("Inserimento - Ricerca - Modifica Dati");
        subTitle.getStyleClass().add("sub-title");
        subTitle.getStyleClass().add("text-light");

        this.getChildren().add(mainTitle);
        this.getChildren().add(subTitle);
    }
}
