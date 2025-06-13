package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Objects;

public class SceneController {

    Image Icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Kelinci.png")));
    private final String DASHBOARD_LINK = "/com/example/emuyhealthcare/DashBoard1.fxml";
    private final String LOGIN_PAGE = "/com/example/emuyhealthcare/LoginPage.fxml";
    Stage stage;



    //Constructor injection

    public SceneController(Stage stage) {
        this.stage = stage;
    }
    public SceneController(){}

    //Method For Building New Window
    public void BuildWindow(String Url){
        try {
            Parent Load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Url)));
            stage.setScene(new Scene(Load));
            stage.setTitle("Emuy HealthCare");
            stage.getIcons().add(Icon);
            System.out.println("Berhasil Menampilkan Layar");
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Tidak Berhasil Menampilkan Layar");
        }
    }

    //Method For ChangeScene
    public void SceneChange(String Url){
        try {
            Parent Loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Url)));
            stage = (Stage) Window.getWindows().filtered(Window::isShowing).getFirst();
            stage.setScene(new Scene(Loader));
            stage.setTitle("Emuy HealthCare");
            stage.getIcons().add(Icon);
            System.out.println("Berhasil Menampilkan Layar");
            stage.centerOnScreen();
            stage.show();
        }catch (IOException e){
            System.out.println("Tidak Berhasil Menampilkan Layar");
            System.out.println(e.getMessage());
        }
    }


    public String getDASHBOARD_LINK() {
        return DASHBOARD_LINK;
    }

    public String getLOGIN_PAGE() {
        return LOGIN_PAGE;
    }
}
