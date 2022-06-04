package hhccco.plutus.views;

import hhccco.plutus.Main;
import hhccco.plutus.components.Body;
import hhccco.plutus.components.Header;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import hhccco.plutus.Env;

public class Home extends VBox {
    private final Scene scene;

    public Home() {
        scene = new Scene(this, Env.screenWidth, Env.screenHeight);
        scene.getStylesheets().add(String.valueOf(Main.class.getResource("home-styles.css")));

        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.setId("home");

        this.getChildren().add(new Header());
        this.getChildren().add(new Body());
    }

    public Scene getViewScene() {
        return scene;
    }
}
