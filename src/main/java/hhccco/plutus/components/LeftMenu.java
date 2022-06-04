package hhccco.plutus.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LeftMenu extends VBox {
    public LeftMenu() {
        this.setId("leftMenu");
        this.setPrefWidth(200);
        this.setPadding(new Insets(10));

        this.getChildren().add(new Label("Data"));
        this.getChildren().add(new TextField());

        this.getChildren().add(new Button("Nuovo"));
        this.getChildren().add(new Button("Salva"));
        this.getChildren().add(new Button("Banche"));
        this.getChildren().add(new Button("CC"));
    }
}
