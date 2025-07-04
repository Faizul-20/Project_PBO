package Controller;

import API.LoginApiV2;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SceneController {

    Image Icon = new Image(getClass().getResourceAsStream("/images/icon.png"));
    private final String DASHBOARD_LINK = "/com/example/emuyhealthcare/dashBoard1.fxml";
    private final String LOGIN_PAGE = "/com/example/emuyhealthcare/loginPage.fxml";
    private final String CHATBOT_LINK = "/com/example/emuyhealthcare/chatBot.fxml";
    private final String UPDATE_LINK = "/com/example/emuyhealthcare/UpdateData.fxml";
    Stage stage;
    LocalDateTime now = LocalDateTime.now(); // Ambil waktu sekarang
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Format waktu
    String formattedTime = now.format(formatter);



    //Constructor injection
    public SceneController(Stage stage) {
        this.stage = stage;
    }
    public SceneController(){};

    //Method For Building New Window
    public void BuildWindow(String Url,String Scene){
        try {
            Parent Load = FXMLLoader.load(getClass().getResource(Url));
            stage.centerOnScreen();
            stage.setScene(new Scene(Load));
            stage.setTitle("Emuy HealthCare");
            stage.getIcons().add(Icon);
            stage.show();
            System.out.println("[EMUYLOG] ["+ LoginApiV2.getUsername() +"] " + formattedTime +" Berhasil menampilkan layar " + Scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void SceneChange(String Url,String Scene) {
        try {
            URL fxmlLocation = getClass().getResource(Url);
            if (fxmlLocation == null) {
                throw new RuntimeException("Resource not found: " + Url);
            }

            Parent loader = FXMLLoader.load(fxmlLocation);
            stage = (Stage) Window.getWindows().filtered(Window::isShowing).get(0);
            stage.centerOnScreen();
            stage.setScene(new Scene(loader));
            stage.setTitle("Emuy HealthCare");
            stage.getIcons().add(Icon);
            stage.show();
            System.out.println("[EMUYLOG] [" + LoginApiV2.getUsername() + "] " + formattedTime + " Berhasil menampilkan layar " + Scene);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
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
