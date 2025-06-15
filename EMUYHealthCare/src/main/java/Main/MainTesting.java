package Main;

import Controller.SceneController;
import Controller.alert;
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
        alert.AlertEror("eror","Apakah Kamu ingin keluar?");

    }
}
