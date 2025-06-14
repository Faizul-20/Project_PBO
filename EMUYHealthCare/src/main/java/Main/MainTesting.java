package Main;

import Controller.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainTesting extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SceneController Control = new SceneController(primaryStage);
        primaryStage.centerOnScreen();
        Control.BuildWindow(Control.getDASHBOARD_LINK());
    }
}
