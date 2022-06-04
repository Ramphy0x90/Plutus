package hhccco.plutus.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Header extends VBox {
    private final Label mainTitle = new Label("Gestione Conto Corrente");
    private final Label subTitle = new Label("Inserimento - Ricerca - Modifica Dati");
    public Header() {
        this.setSpacing(5);
        this.setPadding(new Insets(10));
        this.setPrefHeight(56);

        this.setId("header");

        mainTitle.setId("mainTitle");
        mainTitle.getStyleClass().add("text-light");
        subTitle.getStyleClass().add("text-light");

        this.getChildren().add(mainTitle);
        this.getChildren().add(subTitle);
    }
}
