package Controller;

import API.LoginApiV2;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class UpdateData {

    // Komponen menu dashboard
    @FXML private AnchorPane menuDasboard;
    @FXML private ImageView logoChatBot;
    @FXML private ImageView fotomenuBMI;
    @FXML private ImageView fotologout;
    @FXML private ImageView fotomenuChatbot;
    @FXML private ImageView fotomenudashboard;
    @FXML private JFXButton buttonLogout;
    @FXML private JFXButton bodyMesh;
    @FXML private JFXButton chatBot;
    @FXML private JFXButton hoMe;

    // Komponen panel kesehatan
    @FXML private ImageView gambargulaDarah;
    @FXML private ImageView gambarTekanandarah;
    @FXML private ImageView gamabarOlga;
    @FXML private ImageView gambarBerat;
    @FXML private ImageView gambarTinggi;

    // Komponen input data
    @FXML private TextField tfGulaDarah;
    @FXML private TextField tfTekananDarah;
    @FXML private TextField tfBeratBadan;
    @FXML private TextField tfTinggiBadan;
    @FXML private DatePicker dpOlahraga;

    // Komponen tabel
    @FXML private TableView<?> tabel;
    @FXML private TableColumn<?, ?> colNo;
    @FXML private TableColumn<?, ?> colTanggal;
    @FXML private TableColumn<?, ?> colTarget;

    // Label informasi
    @FXML private Label labelGulaDarah;
    @FXML private Label labelTekananDarah;
    @FXML private Label labelOlahraga;
    @FXML private Label labelBeratBadan;
    @FXML private Label labelTinggiBadan;

    // Inisialisasi controller
    SceneController sceneController = new SceneController();
    @FXML
    public void initialize() {
        handleMenuDashboard();
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
        bodyMesh.setOnAction(e-> {
            sceneController.SceneChange(sceneController.getUPDATE_LINK());
        });
    }


}