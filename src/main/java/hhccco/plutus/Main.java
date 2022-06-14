package hhccco.plutus;

import hhccco.plutus.util.DBconnection;
import hhccco.plutus.views.Home;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Home homeView = new Home();

        stage.setTitle("Plutus");
        stage.setScene(homeView.getViewScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}