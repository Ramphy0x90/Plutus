package hhccco.plutus;

import hhccco.plutus.views.Home;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Ramphy Aquino Nova
 * @version 24/06/2022
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) {
        //Home is the main screen where everything is displayed
        Home homeView = new Home();

        stage.setTitle("Plutus");
        stage.setScene(homeView.getViewScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}