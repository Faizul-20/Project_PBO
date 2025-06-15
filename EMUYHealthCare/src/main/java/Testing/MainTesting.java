package Testing;

import Controller.SceneController;
import Utils.TimeLogger;
import Utils.alert;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class MainTesting extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        SceneController Control = new SceneController(primaryStage);
        primaryStage.centerOnScreen();
        Control.BuildWindow(Control.getCHATBOT_LINK(),"Login");
        new TimeLogger().start();


    }
}
