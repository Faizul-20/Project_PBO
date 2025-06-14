package Controller;

import API.LoginApiV2;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

import static com.sun.javafx.logging.PulseLogger.newInput;


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

    SceneController sceneController = new SceneController();
    public void initialize() {
        handleMenuDashboard();
        InisialisasiAwal();
        kolomtext.setOnAction(e-> buttonkirimChat.fire());
        buttonkirimChat.setOnAction(event -> sendMessage());
    }

    private void sendMessage() {
            Pesan = kolomtext.getText();

        if (!Pesan.isEmpty()){
            HBox userChat = createBubbleMessage(Pesan,Pos.CENTER_RIGHT, "#375FAD","White");
            layearbublechat.getChildren().add(userChat);
            kolomtext.clear();

            HBox BotChat = createBubbleMessage("Ini Jawaban Bot",Pos.CENTER_LEFT,"White","Black");
            layearbublechat.getChildren().add(BotChat);
            scrollToBottom();
        }
    }

    HBox createBubbleMessage(String text, Pos Alignment,String Color,String TextColor) {
        Label label = new Label(text);
        label.setWrapText(true);
        label.setPadding(new Insets(10));
        label.setTextAlignment(TextAlignment.LEFT);
        label.setStyle("-fx-background-color: " + Color + "; -fx-background-radius: 10;" +
                "-fx-text-fill:" + TextColor + ";-fx-font-size: 15");
        label.setMaxWidth(300);

        HBox Wrapper = new HBox(label);
        Wrapper.setAlignment(Alignment);
        Wrapper.setPadding(new Insets(5));
        return Wrapper;
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
            sceneController.SceneChange(sceneController.getDASHBOARD_LINK());
        });
        //Pilihan Chatbot
        chatBot.setOnAction(e-> {
            sceneController.SceneChange(sceneController.getCHATBOT_LINK());
        });
        // Pilihan Logout
        buttonLogout.setOnAction(e -> {
            LoginApiV2.Logout();
            sceneController.SceneChange(sceneController.getLOGIN_PAGE());
            LoginApiV2.CetakValue();
        });
    }



}



