package Main;
import Controller.SceneController;
import Utils.TimeLogger;
import  javafx.application.Application;
import javafx.stage.Stage;


public class EmuyMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SceneController Control = new SceneController(primaryStage);
        primaryStage.centerOnScreen();
        Control.BuildWindow(Control.getLOGIN_PAGE(),"Login");
        new TimeLogger().start();
    }
}
