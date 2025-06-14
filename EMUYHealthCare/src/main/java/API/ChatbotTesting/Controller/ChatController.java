package API.ChatbotTesting.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.text.TextAlignment;

public class ChatController {
    @FXML
    private VBox chatBox;

    @FXML
    private TextField inputField;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            // Bubble pengguna di kanan
            HBox userBubble = createBubble(message, Pos.CENTER_RIGHT, "#DCF8C6"); // hijau muda
            chatBox.getChildren().add(userBubble);

            // Simulasi balasan bot (di kiri)
            HBox botBubble = createBubble("Ini jawaban bot untuk: " + message, Pos.CENTER_LEFT, "#ECECEC"); // abu-abu muda
            chatBox.getChildren().add(botBubble);

            inputField.clear();

            // Scroll otomatis
            scrollToBottom();
        }
    }

    private HBox createBubble(String text, Pos alignment, String backgroundColor) {
        Label label = new Label(text);
        label.setWrapText(true);
        label.setPadding(new Insets(10));
        label.setTextAlignment(TextAlignment.LEFT);
        label.setStyle("-fx-background-color: " + backgroundColor + "; -fx-background-radius: 10;");
        label.setMaxWidth(300);

        HBox wrapper = new HBox(label);
        wrapper.setAlignment(alignment);
        wrapper.setPadding(new Insets(5));
        return wrapper;
    }

    private void scrollToBottom() {
        Platform.runLater(() -> scrollPane.setVvalue(1.0));
    }
}
