package Main;
import Controller.SceneController;
import DataBaseController.UserConnecting;
import  javafx.application.Application;
import javafx.stage.Stage;


public class EmuyMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        UserConnecting Con = new UserConnecting();
        Con.ConnectToDatabase(Con.getUserData());
        SceneController Control = new SceneController();
        Control.BuildWindow(Control.getLOGIN_LINK(),primaryStage);
    }
}
