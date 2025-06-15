package Controller;

import API.LoginApiV2;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.Map;

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
    @FXML private TableView<Map.Entry<String,Double>> tabel;
    @FXML private TableColumn<Map.Entry<String,Double>,Integer> colNo;
    @FXML private TableColumn<Map.Entry<String,Double>,String> colTanggal;
    @FXML private TableColumn<Map.Entry<String,Double>,Double> colTarget;

    SceneController sceneController = new SceneController();
    LoginApiV2 loginApiV2 = new LoginApiV2();
    // Initialize method (optional)
    @FXML
    public void initialize() {
        handleMenuDashboard();
        handleUpdateData();
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

    private void handleUpdateData(){
        buttonGuladarah.setOnAction(e-> UpdateGulaDarah());
        buttonTekanandarah.setOnAction(e-> UpdateTekananDarah());
        buttonBeratbadan.setOnAction(e-> UpdateBeratBadan());
        buttonTinggibadan.setOnAction(e-> UpdateTinggiBadan());
        Platform.runLater(this::UpdateTargetOlahraga);
    }

    private void UpdateGulaDarah(){
        double GulaDarah = Double.parseDouble(tfGulaDarah.getText());
        LoginApiV2.gulaDarah = GulaDarah;
        loginApiV2.updateGulaDarah(GulaDarah);
        labelGulaDarah.setText(tfGulaDarah.getText());
        tfGulaDarah.clear();
    }
    private void UpdateTekananDarah(){
        double TekananDarah = Double.parseDouble(tfTekananDarah.getText());
        LoginApiV2.TekananDarah = TekananDarah;
        loginApiV2.updateTekananDarah(TekananDarah);
        labelTekananDarah.setText(tfTekananDarah.getText());
        tfTekananDarah.clear();
    }

    private void UpdateTinggiBadan(){
        double TinggiBadan = Double.parseDouble(tfTinggiBadan.getText());
        LoginApiV2.tinggiBadan = TinggiBadan;
        loginApiV2.updateTinggiBadan(TinggiBadan);
        labelTinggiBadan.setText(tfTinggiBadan.getText());
        tfTinggiBadan.clear();
    }

    private void UpdateBeratBadan(){
        double BeratBadan = Double.parseDouble(tfBeratBadan.getText());
        LoginApiV2.beratBadan = BeratBadan;
        loginApiV2.updateBeratBadan(BeratBadan);
        labelBeratBadan.setText(tfBeratBadan.getText());
        tfBeratBadan.clear();
    }

    private void UpdateTargetOlahraga(){
        colTanggal.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKey()));
        colTarget.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValue()).asObject());

        ObservableList<Map.Entry<String, Double>> dataList =
                FXCollections.observableArrayList(LoginApiV2.Target.entrySet());
        System.out.println("===================================================");
        System.out.println("Update Target Olahraga");
        System.out.println("===================================================");
        tabel.setItems(dataList);
    }

}