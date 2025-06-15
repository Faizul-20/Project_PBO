package Controller;

import API.LoginApiV2;
import API.PenyakitAPI;
import DataBaseController.PenyakitConnecting;
import chatBotEngine.CakapEmuyService;
import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.sql.SQLException;


public class ChatBotController {


    // MenuBoard DI sisi Kanan
    @FXML
    private AnchorPane menuDasboard;
    @FXML
    private ImageView fotologo;
    @FXML
    private ImageView fotomenudashboard;
    @FXML
    private ImageView fotomenuBMI;
    @FXML
    private ImageView fotomenuChatbot;
    @FXML
    private ImageView fotologout;
    //untuk button
    @FXML
    private JFXButton hoMe;
    @FXML
    private JFXButton chatBot;
    @FXML
    private JFXButton bodyMesh;
    @FXML
    private JFXButton buttonLogout;


    //Layar Percakapan
    @FXML
    private VBox layearbublechat;
    @FXML
    private TextField kolomtext;
    @FXML
    private JFXButton buttonkirimChat;
    @FXML
    private ScrollPane kolomChatGeser;


    //Layar Diagnosis
    @FXML
    private TextArea kolomGejalaDiterima;
    @FXML
    private TextArea kolomDiagnosa;

    String Pesan = "";
    CakapEmuyService EmuyService;
    PenyakitConnecting PenyakitConnecting = new PenyakitConnecting();

    {
        try {
            EmuyService = new CakapEmuyService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    SceneController sceneController = new SceneController();
    public void initialize() {
        layearbublechat.setFillWidth(true);
        handleMenuDashboard();
        InisialisasiAwal();
        kolomtext.setOnAction(e-> buttonkirimChat.fire());
        buttonkirimChat.setOnAction(event -> sendMessage());
    }

    private void sendMessage() {
        Pesan = kolomtext.getText();
        PenyakitConnecting.feedbackChatBot(Pesan);
        String Input = PenyakitAPI.feedback;

        if (!Pesan.isEmpty()) {
            HBox userChat = createBubbleMessage(Pesan, Pos.CENTER_RIGHT, "#375FAD", "White");
            layearbublechat.getChildren().add(userChat);
            kolomtext.clear();

            Label typingLabel = new Label("Bot sedang mengetik...");
            typingLabel.setStyle("-fx-font-style: italic; -fx-text-fill: grey;");
            HBox typingBubble = new HBox(typingLabel);
            typingBubble.setAlignment(Pos.CENTER_LEFT);
            typingBubble.setPadding(new Insets(5));
            layearbublechat.getChildren().add(typingBubble);
            scrollToBottom();

            PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
            delay.setOnFinished(event -> {
                layearbublechat.getChildren().remove(typingBubble);
                showTypingBot(Input);
            });
            delay.play();
        }
    }

    private void showTypingBot(String fullText) {
        HBox botBubble = createBubbleMessage("", Pos.CENTER_LEFT, "White", "black");
        Label botLabel = (Label) botBubble.getChildren().get(0);
        layearbublechat.getChildren().add(botBubble);
        scrollToBottom();

        StringBuilder sb = new StringBuilder();
        Timeline tl = new Timeline();
        for (int i = 0; i < fullText.length(); i++) {
            int j = i;
            tl.getKeyFrames().add(new KeyFrame(Duration.millis(10 * (j + 1)), e -> {
                sb.append(fullText.charAt(j));
                botLabel.setText(sb.toString());
                scrollToBottom();
            }));
        }
        tl.play();
    }


    HBox createBubbleMessage(String text, Pos alignment, String color, String textColor) {
        Label label = new Label(text);
        label.setWrapText(true);
        label.setPadding(new Insets(10));
        label.setTextAlignment(TextAlignment.LEFT);
        label.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 10;"
                + "-fx-text-fill:" + textColor + ";-fx-font-size: 15");
        label.setMaxWidth(300);

        // Perbaikan: Gunakan Region.USE_PREF_SIZE dan Region.USE_COMPUTED_SIZE
        label.setMinHeight(Region.USE_PREF_SIZE);
        label.setPrefHeight(Region.USE_COMPUTED_SIZE);

        HBox wrapper = new HBox(label);
        wrapper.setAlignment(alignment);
        wrapper.setPadding(new Insets(5));

        // Perbaikan: Tambahkan konfigurasi untuk wrapper
        wrapper.setMinHeight(Region.USE_PREF_SIZE);
        wrapper.setPrefHeight(Region.USE_COMPUTED_SIZE);

        return wrapper;
    }

    private void scrollToBottom(){
        Platform.runLater(()->kolomChatGeser.setVvalue(1.0));
    }

    private void InisialisasiAwal(){
        String PesanEmuyDefault = "Hai " + LoginApiV2.getUsername() +"!!\n"
               + "Apa Kabarmu Hari ini?";
        HBox PesanDefault = createBubbleMessage(PesanEmuyDefault,Pos.CENTER_LEFT,"WHite","Black");
        layearbublechat.getChildren().add(PesanDefault);
    }
    private void handleMenuDashboard(){
        //Pilihan Dashboard
        hoMe.setOnAction(e-> {
            sceneController.SceneChange(sceneController.getDASHBOARD_LINK(),"DashBoard");
        });
        //Pilihan Chatbot
        chatBot.setOnAction(e-> {
            sceneController.SceneChange(sceneController.getCHATBOT_LINK(),"ChatBot");
        });
        // Pilihan Logout
        buttonLogout.setOnAction(e -> {
            LoginApiV2.Logout();
            sceneController.SceneChange(sceneController.getLOGIN_PAGE(),"Login");
            LoginApiV2.CetakValue();
        });
        bodyMesh.setOnAction(e-> {
            sceneController.SceneChange(sceneController.getUPDATE_LINK(),"Update");
        });
    }



}



