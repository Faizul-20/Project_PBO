package Controller;

import API.LoginApiV2;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
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
    private Label labelBeratbedan;

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
    private LineChart tabelOlaraga;

    //warna indikator tabel darah
    @FXML
    private AnchorPane indikatorGuladarah;

    @FXML
    private Label labelIndikatorGulaDarah;
    @FXML
    private  AnchorPane indikatorTekanandarah;
    @FXML
    private Label labelIndikatorTekananDarah;


    //judul user name
    @FXML
    private  Label judulUsername;
    //indikator ideal
    @FXML
    private  Label indikatoIdeal;

    //schedule
    @FXML
    private Label JarakLari;
    @FXML
    private Label tanggal;

    //foto sek on process

//collor palet indikator
    //bahaya #ff0000
    //waspada #ff8c00
    //normal #00ff0d

    SceneController sceneController = new SceneController();
    @FXML
    public void initialize(){
        try {
            loginnew.setVisible(false);
            //judulUsername.setText("Hello " + LoginApiV2.getUsername());
            getValuesLogin();
            bmiArrowIndikator();
            getTargetUser();
            updateTargetLari();
            BMIvalues(LoginApiV2.BMIIndeksBadan);
            Platform.runLater(() -> updateGulaDarahColor());
            Platform.runLater(() -> updateTekananDarahColor());
            handleMenuDashboard();

        } catch (Exception e) {
            System.err.println("Terjadi Eror");
            System.out.println("Pesan Eror :"+ e.getMessage());
        }


    }

    private void handleInput(){
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

    private void getValuesLogin(){
        labelBmivalue.setText(String.valueOf(LoginApiV2.BMIIndeksBadan));
        labelTInggibadan.setText(String.valueOf(LoginApiV2.tinggiBadan));
        labelBeratbedan.setText(String.valueOf(LoginApiV2.beratBadan));
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

    private void BMIvalues(double value){
        if (value <= 18.5){
            indikatoIdeal.setText("Berat badan kurang");
        } else if (value > 18.5 && value < 25   ) {
            indikatoIdeal.setText("Ideal");
        }else if (value > 25 && value < 30){
            indikatoIdeal.setText("Kelebihan Berat Badan");
        } else if (value > 30 && value < 35) {
            indikatoIdeal.setText("Obesitas 1");
        } else if (value > 35 && value < 40) {
            indikatoIdeal.setText("Obesitas 2");
        } else if (value > 40) {
            indikatoIdeal.setText("Obesitas 3");
        }
    }



    private void updateGulaDarahColor(){
        labelGuladarahnormal.setText(String.valueOf(LoginApiV2.gulaDarah));
        Color color = gulaDarahColor(LoginApiV2.gulaDarah);
        CornerRadii radii = new CornerRadii(20);
        indikatorGuladarah.setBackground(new Background(new BackgroundFill(color,radii, Insets.EMPTY)));
    }

    private Color gulaDarahColor(double value){
        if (value <= 100){
            labelIndikatorGulaDarah.setText("Normal");
            return Color.rgb(91,221,91);
        } else if (value <= 140) {
            labelIndikatorGulaDarah.setText("Pradiabetes");
            double rasio = (value - 100) / 40;
            int red = (int) (255 * rasio);
            int green = 255;
            return Color.rgb(red, green,85);

        } else if (value <= 200) {
            labelIndikatorGulaDarah.setText("Diabetes");
            double rasio = (value - 140) /60;
            int red = 255;
            int green = (int) (255 * (1-rasio));
            return Color.rgb(red, green, 80);
        }else {
            labelIndikatorGulaDarah.setText("DANGER!");
            return Color.rgb(200,70,70);
        }
    }

    private void updateTekananDarahColor(){
        //labelTekananDarah.setText(String.valueOf(LoginApiV2.TekananDarah));
        Color color = TekananDarahColor(LoginApiV2.TekananDarah);
        CornerRadii radii = new CornerRadii(20);
        indikatorTekanandarah.setBackground(new Background(new BackgroundFill(color,radii, Insets.EMPTY)));
    }

    private Color TekananDarahColor(double value){
        if (value < 90) {
            labelIndikatorTekananDarah.setText("Hipotensi");
            return Color.rgb(70, 130, 180); // Biru muda (low pressure)
        } else if (value >= 90 && value <= 120) {
            labelIndikatorTekananDarah.setText("Normal");
            double ratio = (value - 90) / 30.0;
            int red = (int) (70 + (91 - 70) * ratio);      // transisi ke hijau
            int green = (int) (180 + (221 - 180) * ratio); // dari biru ke hijau
            return Color.rgb(red, green, 90);
        } else if (value > 120 && value < 140) {
            labelIndikatorTekananDarah.setText("Prehipertensi");
            double ratio = (value - 120) / 20.0;
            int red = (int) (91 + (255 - 91) * ratio);     // hijau ke kuning
            int green = (int) (221 - (221 - 200) * ratio); // tetap agak hijau
            return Color.rgb(red, green, 80);
        } else if (value > 140 && value < 160) {
            labelIndikatorTekananDarah.setText("Hipertensi 1");
            double ratio = (value - 140) / 20.0;
            int red = 255;
            int green = (int) (200 - (200 - 120) * ratio); // kuning ke oranye
            return Color.rgb(red, green, 70);
        } else if (value >= 160) {
            labelIndikatorTekananDarah.setText("Hipertensi 2");
            return Color.rgb(200, 50, 50); // Merah bahaya
        }
        return Color.GRAY;
    }


    private void updateTargetLari(){
        Map.Entry<String,Double> targetLari = null;

        for (Map.Entry<String,Double> LastEntry : LoginApiV2.Target.entrySet()){
            System.out.println("Tanggal : " +LastEntry.getKey() + "  Jarak : " + LastEntry.getValue());
            targetLari = LastEntry;
        }
        tanggal.setText(targetLari.getKey());
        JarakLari.setText("Lari " + targetLari.getValue().toString() + " KM");

    }
}



