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

    // Menu Dashboard Components
    @FXML private AnchorPane menuDasboard;
    @FXML private ImageView logoChatBot;
    @FXML private ImageView fotomenuBMI;
    @FXML private ImageView fotologout;
    @FXML private ImageView fotomenuChatbot;
    @FXML private ImageView fotomenudashboard;

    // Menu Buttons
    @FXML private JFXButton buttonLogout;
    @FXML private JFXButton bodyMesh;
    @FXML private JFXButton chatBot;
    @FXML private JFXButton hoMe;

    // Data Panel Components
    @FXML private Label labelGulaDarah;
    @FXML private TextField tfGulaDarah;
    @FXML private ImageView gambargulaDarah;
    @FXML private JFXButton buttonGuladarah;

    @FXML private Label labelTekananDarah;
    @FXML private TextField tfTekananDarah;
    @FXML private ImageView gambarTekanandarah;
    @FXML private JFXButton buttonTekanandarah;

    @FXML private Label labelOlahraga;
    @FXML private DatePicker dpOlahraga;
    @FXML private TextField tfTargetolahraga;
    @FXML private ImageView gamabarOlga;
    @FXML private JFXButton buttonOlahraga;

    @FXML private Label labelBeratBadan;
    @FXML private TextField tfBeratBadan;
    @FXML private ImageView gambarBerat;
    @FXML private JFXButton buttonBeratbadan;

    @FXML private Label labelTinggiBadan;
    @FXML private TextField tfTinggiBadan;
    @FXML private ImageView gambarTinggi;
    @FXML private JFXButton buttonTinggibadan;

    // Table Components
    @FXML private TableView<?> tabel;
    @FXML private TableColumn<?, ?> colNo;
    @FXML private TableColumn<?, ?> colTanggal;
    @FXML private TableColumn<?, ?> colTarget;

    SceneController sceneController = new SceneController();
    // Initialize method (optional)
    @FXML
    public void initialize() {
        handleMenuDashboard();
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