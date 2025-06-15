package Controller;

import API.LoginApiV2;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
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

    private ObservableList<Map.Entry<String, Double>> dataList;

    SceneController sceneController = new SceneController();
    LoginApiV2 loginApiV2 = new LoginApiV2();

    // Initialize method (optional)
    @FXML
    public void initialize() {
        handleMenuDashboard();
        handleUpdateData();
        EasyHeandle();
        Platform.runLater(this::getValues);
    }

    public void getValues() {
        labelGulaDarah.setText(String.valueOf(LoginApiV2.gulaDarah));
        labelTekananDarah.setText(String.valueOf(LoginApiV2.TekananDarah));
        labelTinggiBadan.setText(String.valueOf(LoginApiV2.tinggiBadan));
        labelBeratBadan.setText(String.valueOf(LoginApiV2.beratBadan));
        setupTable();
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

    private void EasyHeandle(){
        tfGulaDarah.setOnAction(e -> buttonGuladarah.fire());
        tfTekananDarah.setOnAction(e -> buttonTekanandarah.fire());
        tfTinggiBadan.setOnAction(e -> buttonTinggibadan.fire());
        tfBeratBadan.setOnAction(e -> buttonBeratbadan.fire());
        tfTargetolahraga.setOnAction(e-> buttonOlahraga.fire());
    }

    private void handleUpdateData(){
        buttonGuladarah.setOnAction(e-> UpdateGulaDarah());
        buttonTekanandarah.setOnAction(e-> UpdateTekananDarah());
        buttonBeratbadan.setOnAction(e-> UpdateBeratBadan());
        buttonTinggibadan.setOnAction(e-> UpdateTinggiBadan());
        buttonOlahraga.setOnAction(e-> addNewTarget());
        Platform.runLater(this::UpdateTargetOlahraga);
    }

    private void UpdateGulaDarah(){
        double GulaDarah = Double.parseDouble(tfGulaDarah.getText());
        LoginApiV2.gulaDarah = GulaDarah;
        loginApiV2.updateGulaDarah(GulaDarah);
        labelGulaDarah.setText(tfGulaDarah.getText());
        tfGulaDarah.clear();
        LoginApiV2.CetakValue();
    }
    private void UpdateTekananDarah(){
        double TekananDarah = Double.parseDouble(tfTekananDarah.getText());
        LoginApiV2.TekananDarah = TekananDarah;
        loginApiV2.updateTekananDarah(TekananDarah);
        labelTekananDarah.setText(tfTekananDarah.getText());
        tfTekananDarah.clear();
        LoginApiV2.CetakValue();
    }

    private void UpdateTinggiBadan(){
        double TinggiBadan = Double.parseDouble(tfTinggiBadan.getText());
        LoginApiV2.tinggiBadan = TinggiBadan;
        loginApiV2.updateTinggiBadan(TinggiBadan);
        labelTinggiBadan.setText(tfTinggiBadan.getText());
        tfTinggiBadan.clear();
        LoginApiV2.CetakValue();
    }

    private void UpdateBeratBadan(){
        double BeratBadan = Double.parseDouble(tfBeratBadan.getText());
        LoginApiV2.beratBadan = BeratBadan;
        loginApiV2.updateBeratBadan(BeratBadan);
        labelBeratBadan.setText(tfBeratBadan.getText());
        tfBeratBadan.clear();
        LoginApiV2.CetakValue();
    }

    private void setupTable() {
        colNo.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(tabel.getItems().indexOf(cellData.getValue()) + 1)
        );
        colTanggal.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKey()));
        colTarget.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValue()).asObject());

        // Inisialisasi dataList dengan data terbaru
        dataList = FXCollections.observableArrayList(LoginApiV2.Target.entrySet());
        tabel.setItems(dataList);
    }

    private void addNewTarget(){
        String Tanggal = dpOlahraga.getValue().toString();
        int Target = Integer.parseInt(tfTargetolahraga.getText());
        loginApiV2.updateTargetLari(Tanggal, Target);
        labelOlahraga.setText(String.valueOf(Target));

        dataList.setAll(LoginApiV2.Target.entrySet());
        tabel.setItems(dataList);
        tabel.refresh();


        tfTargetolahraga.clear();
        dpOlahraga.setValue(null);
        LoginApiV2.CetakValue();
    }

    private void UpdateTargetOlahraga(){
        colNo.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(tabel.getItems().indexOf(cellData.getValue()) + 1)
        );
        colTanggal.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKey()));
        colTarget.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValue()).asObject());

        ObservableList<Map.Entry<String, Double>> dataList =
                FXCollections.observableArrayList(LoginApiV2.Target.entrySet());

        System.out.println("===================================================");
        System.out.println("Update Target Olahraga");
        System.out.println("===================================================");
        tabel.setItems(dataList);
        tabel.refresh();
    }




}