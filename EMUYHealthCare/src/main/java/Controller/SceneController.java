package Controller;

import API.LoginApiV2;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class SceneController {

    private final Image Icon = new Image(getClass().getResourceAsStream("/Kelinci.png"));
    private final String DASHBOARD_LINK = "/com/example/emuyhealthcare/DashBoard1.fxml";
    private final String LOGIN_PAGE = "/com/example/emuyhealthcare/LoginPage.fxml";
    private final String CHATBOT_LINK = "/com/example/emuyhealthcare/chatBot.fxml";
    private final String UPDATE_LINK = "/com/example/emuyhealthcare/UpdateData.fxml";

    private Stage stage;

    // Cache scene yang sudah dimuat
    private final Map<String, Parent> sceneCache = new HashMap<>();

    public SceneController(Stage stage) {
        this.stage = stage;
    }

    public SceneController() {
    }

    // Format waktu log
    private String getCurrentFormattedTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // Caching scene pertama kali
    private Parent loadFXML(String url) throws IOException {
        if (sceneCache.containsKey(url)) {
            return sceneCache.get(url);
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
        Parent root = loader.load();
        sceneCache.put(url, root);
        return root;
    }

    // Build window baru (untuk launch awal misalnya login)
    public void BuildWindow(String url, String sceneName) {
        try {
            Parent root = loadFXML(url);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Emuy HealthCare");
            stage.getIcons().setAll(Icon);
            stage.centerOnScreen();
            stage.show();
            logSceneChange(sceneName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Ganti scene di window yang sama
    public void SceneChange(String url, String sceneName) {
        try {
            if (stage == null) {
                stage = (Stage) Window.getWindows().filtered(Window::isShowing).get(0);
            }

            Parent root = loadFXML(url);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Emuy HealthCare");
            stage.getIcons().setAll(Icon);
            stage.centerOnScreen();
            stage.show();
            logSceneChange(sceneName);
        } catch (IOException e) {
            System.err.println("Gagal mengganti scene ke: " + sceneName);
            e.printStackTrace();
        }
    }

    private void logSceneChange(String sceneName) {
        System.out.println("[EMUYLOG] [" + LoginApiV2.getUsername() + "] "
                + getCurrentFormattedTime() + " Berhasil menampilkan layar " + sceneName);
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
