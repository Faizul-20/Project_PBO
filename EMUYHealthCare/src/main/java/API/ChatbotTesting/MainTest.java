package API.ChatbotTesting;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class MainTest extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/emuyhealthcare/chat_view.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/example/emuyhealthcare/style.css").toExternalForm());
        stage.setTitle("Chatbot Mengambang");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
