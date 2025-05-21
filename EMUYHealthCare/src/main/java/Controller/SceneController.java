package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class SceneController {
    //private final String LOGIN_LINK = "/com/example/emuyhealthcare/SignUp-view.fxml";
    private final String DASHBOARD_LINK = "/com/example/emuyhealthcare/DashBoard.fxml";
    private final String LOGIN_LINK = "/com/example/emuyhealthcare/loginPage.fxml";

    //Method For Building New Window
    public void BuildWindow(String Url,Stage stage){
        try {
            Parent Load = FXMLLoader.load(getClass().getResource(Url));
            stage.setScene(new Scene(Load));
            stage.setTitle("Emuy HealthCare");
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Method For ChangeScene
    public void SceneChange(String Url){
        try {
            Parent Loader = FXMLLoader.load(getClass().getResource(Url));
            Stage stage = (Stage) Window.getWindows().filtered(Window::isShowing).get(0);
            stage.setScene(new Scene(Loader));
            stage.setTitle("Emuy HealthCare");
            stage.show();
        }catch (IOException e){

        }
    }

    public String getLOGIN_LINK() {
        return LOGIN_LINK;
    }

    public String getDASHBOARD_LINKK() {
        return DASHBOARD_LINK;
    }

    public String getDASHBOARD_LINK() {
        return DASHBOARD_LINK;
    }
}
