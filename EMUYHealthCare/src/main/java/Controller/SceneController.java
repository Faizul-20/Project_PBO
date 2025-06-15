package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class SceneController {

    Image Icon = new Image(getClass().getResourceAsStream("/Kelinci.png"));
    private final String DASHBOARD_LINK = "/com/example/emuyhealthcare/DashBoard1.fxml";
    private final String LOGIN_PAGE = "/com/example/emuyhealthcare/LoginPage.fxml";
    private final String CHATBOT_LINK = "/com/example/emuyhealthcare/chatBot.fxml";
    private final String UPDATE_LINK = "/com/example/emuyhealthcare/UpdateData.fxml";
    Stage stage;


    //Constructor injection

    public SceneController(Stage stage) {
        this.stage = stage;
    }
    public SceneController(){};

    //Method For Building New Window
    public void BuildWindow(String Url){
        try {
            Parent Load = FXMLLoader.load(getClass().getResource(Url));
            stage.centerOnScreen();
            stage.setScene(new Scene(Load));
            stage.setTitle("Emuy HealthCare");
            stage.getIcons().add(Icon);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Method For ChangeScene
    public void SceneChange(String Url){
        try {
            Parent Loader = FXMLLoader.load(getClass().getResource(Url));
            stage = (Stage) Window.getWindows().filtered(Window::isShowing).get(0);
            stage.centerOnScreen();
            stage.setScene(new Scene(Loader));
            stage.setTitle("Emuy HealthCare");
            stage.getIcons().add(Icon);
            stage.show();
            System.out.println("Berhasil menampilkan layar");
        }catch (IOException e){
            System.out.println("Pesan Eror : " + e.getMessage());
            System.out.println("Tidak Dapat menampilkan Layar");
        }
    }


    public String getDASHBOARD_LINK() {
        return DASHBOARD_LINK;
    }
    public String getUPDATE_LINK() {
        return UPDATE_LINK;
    }
    public String getLOGIN_PAGE() {
        return LOGIN_PAGE;
    }
    public String getCHATBOT_LINK() {
        return CHATBOT_LINK;
    }
}
