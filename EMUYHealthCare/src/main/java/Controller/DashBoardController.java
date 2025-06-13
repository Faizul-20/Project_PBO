package Controller;

import API.LoginApiV2;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.util.Map;

import static com.sun.javafx.logging.PulseLogger.newInput;


public class DashBoardController {
    @FXML
    private AnchorPane layer;
    @FXML
    private  AnchorPane loginnew;
    @FXML
    private TextField inputGUladarah;
    @FXML
    private TextField inputTekanandarah;
    @FXML
    private  JFXButton inputNewlogin;
    //tombol menu sisi kanan
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

    //tabel gula darah
    @FXML
    private AnchorPane tabelGuladarah;
    @FXML
    private ImageView logoGuladarah;
    @FXML
    private Label labelGuladarah;
    //indikator tak gawe 3
    @FXML
    private AnchorPane tabelGuladarahnormal;
    @FXML
    private Label labelGuladarahnormal; // iki angka indikator
    @FXML
    private AnchorPane tabelGuladarahwaspada;
    @FXML
    private Label labelGuladarahwaspada; // iki angka indikator
    @FXML
    private AnchorPane tabelGuladarahbahaya;
    @FXML
    private Label labelGuladarahbahaya; // iki angka indikator

    //tabel Tekanan darah
    @FXML
    private AnchorPane TabelTekanandarah;
    @FXML
    private ImageView logoTekanandarah;
    @FXML
    private Label labelTekanandarah;
    //indikator tak gawe 3
    @FXML
    private AnchorPane tabelTekanandarahnormal;
    @FXML
    private Label labelTekanandarahnormal; // iki angka indikator
    @FXML
    private AnchorPane tabelTekanandarahwaspada;
    @FXML
    private Label labelTekanandarahwaspada; // iki angka indikator
    @FXML
    private AnchorPane tabelTekanandarahbahaya;
    @FXML
    private Label labelTekanandarahbahaya; // iki angka indikator

    //tabel bmi
    //tabel tinggi badan
    @FXML
    private AnchorPane tabelTinggibadan;
    @FXML
    private Label labelTInggibadan;
    //tabel berat badan
    @FXML
    private AnchorPane tabelBeratbadan;
    @FXML
    private Label labelBeratbadan;

    //indikator
    @FXML
    private AnchorPane tabelIndikatorbmi;
    @FXML
    private Polygon kursorIndikator;
    @FXML
    private Rectangle tableIndikator;

    @FXML
    private Label labelBmivalue;

    //tabel olahraga
    @FXML
    private LineChart<String,Number> tabelOlaraga;
    @FXML
    private CategoryAxis XAxis;
    @FXML
    private NumberAxis YAxis;



    @FXML
    public void initialize(){
        loginnew.setVisible(false);
        getValuesLogin();
        bmiArrowIndikator();
        getTargetUser();

    }

    private void handleInput(){
    }

    private void getValuesLogin(){
        labelBmivalue.setText(String.valueOf(LoginApiV2.BMIIndeksBadan));
        labelTInggibadan.setText(String.valueOf(LoginApiV2.tinggiBadan));
        labelBeratbadan.setText(String.valueOf(LoginApiV2.beratBadan));
    }

    private boolean checkMember(){
        if (LoginApiV2.gulaDarah == 0 && LoginApiV2.TekananDarah == 0){
            return false;
        }
        return true;
    }
    private void bmiArrowIndikator(){
        kursorIndikator.setLayoutY(kursorIndikator.getLayoutY());

        double x = map(LoginApiV2.BMIIndeksBadan,10,40,3,190);
        kursorIndikator.setLayoutX(x);
    }
    private double map(double value, double inMin, double inMax, double outMin, double outMax) {
        return (value - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
    }

    private void getTargetUser() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(null);

        //Memasukan nilai Target ke dalam LineChart
        for (Map.Entry<String, Double> entry : LoginApiV2.Target.entrySet()) {
            try {
                series.getData().add(new XYChart.Data<>(entry.getKey(),entry.getValue()));
            } catch (NumberFormatException e) {
                System.err.println("Error parsing value: " + entry.getValue());
            }
        }
        // Tambahkan series ke chart
        tabelOlaraga.getData().add(series);
    }


}



