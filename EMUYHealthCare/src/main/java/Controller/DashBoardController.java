package Controller;

import API.LoginApiV2;
import DataBaseController.UserConnecting;
import com.jfoenix.controls.JFXButton;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.*;


public class DashBoardController {
    @FXML
    private AnchorPane layer;
    @FXML
    private  AnchorPane loginnew;
    @FXML
    private TextField inputGuladarah;
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
    private AnchorPane tabelGuladarahnormal;

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
    private  Label indikatorIdeal;

    //schedule
    @FXML
    private Label JarakLari;
    @FXML
    private Label tanggal;
// angka indikator tekanan
    @FXML
    private Label angkaGuladarah;

    @FXML
    private Label angkaTekanandarah;


    @FXML
    private ImageView fotoBmi;

    SceneController sceneController = new SceneController();
    UserConnecting userConnecting = new UserConnecting();
    private final Map<String, Image> imageCache = new HashMap<>();



    @FXML
    public void initialize(){
        try {
            loginnew.setVisible(checkMember());
            handleMenuDashboard();
            judulUsername.setText("Hai " + LoginApiV2.getUsername() + " !!");
            getValuesLogin();
            bmiArrowIndikator();
            getTargetUser();
            updateTargetLari();
            BMIvalues(LoginApiV2.BMIIndeksBadan);
            Platform.runLater(() -> updateGulaDarahColor());
            Platform.runLater(() -> updateTekananDarahColor());
            inputNewlogin.setOnAction(event -> handleNewLogin());

        } catch (Exception e) {
            System.err.println("Terjadi Eror");
            System.out.println("Pesan Eror :"+ e.getMessage());
        }


    }


    private void handleNewLogin(){
        double gulaDarah = Double.parseDouble(inputGuladarah.getText());
        double tekananDarah = Double.parseDouble(inputTekanandarah.getText());

        if (gulaDarah != 0 && tekananDarah != 0 ) {
            userConnecting.InsertDarah(gulaDarah, tekananDarah);
            userConnecting.SignInV2(LoginApiV2.getUsername(), LoginApiV2.getPassword());
            loginnew.setVisible(false);
            LoginApiV2.CetakValue();
        }else {
            System.out.println("Input Tidak Berhasil di masukan ke database");
        }
        getValuesLogin();
        inputGuladarah.clear();
        inputTekanandarah.clear();

    }

    private void handleInput(){
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

    private void getValuesLogin(){
        labelBmivalue.setText(String.valueOf(LoginApiV2.BMIIndeksBadan));
        labelTInggibadan.setText(String.valueOf(LoginApiV2.tinggiBadan));
        labelBeratbedan.setText(String.valueOf(LoginApiV2.beratBadan));
        angkaGuladarah.setText(String.valueOf(LoginApiV2.gulaDarah));
        angkaTekanandarah.setText(String.valueOf(LoginApiV2.TekananDarah));

    }

    private boolean checkMember(){
        if (LoginApiV2.gulaDarah == 0 && LoginApiV2.TekananDarah == 0){
            return true;
        }
        return false;
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
        series.setName("Target Lari");

        // Sumbu X dikunci
        CategoryAxis xAxis = (CategoryAxis) tabelOlaraga.getXAxis();
        List<String> semuaLabel = new ArrayList<>(LoginApiV2.Target.keySet());
        xAxis.setCategories(FXCollections.observableArrayList(semuaLabel));

        // Sumbu Y dikunci
        NumberAxis yAxis = (NumberAxis) tabelOlaraga.getYAxis();
        yAxis.setAutoRanging(false);
        double max = LoginApiV2.Target.values().stream().mapToDouble(v -> v).max().orElse(10);
        double min = LoginApiV2.Target.values().stream().mapToDouble(v -> v).min().orElse(0);
        yAxis.setLowerBound(Math.floor(min - 2));
        yAxis.setUpperBound(Math.ceil(max + 2));
        yAxis.setTickUnit(2);

        tabelOlaraga.getData().clear();
        tabelOlaraga.getData().add(series);

        // Siapkan data
        List<Map.Entry<String, Double>> entries = new ArrayList<>(LoginApiV2.Target.entrySet());

        Timeline timeline = new Timeline();
        int delayPerPoint = 500;

        for (int i = 0; i < entries.size(); i++) {
            int index = i;
            KeyFrame kf = new KeyFrame(Duration.millis(index * delayPerPoint), e -> {
                Map.Entry<String, Double> entry = entries.get(index);
                XYChart.Data<String, Number> data = new XYChart.Data<>(entry.getKey(), entry.getValue());
                series.getData().add(data);

                PauseTransition pause = new PauseTransition(Duration.millis(50));
                pause.setOnFinished(ev -> {
                    if (data.getNode() != null) {
                        Node node = data.getNode();
                        node.setStyle("-fx-background-color: red, white;");
                        node.setTranslateY(10);
                        node.setOpacity(0);

                        TranslateTransition tt = new TranslateTransition(Duration.millis(300), node);
                        tt.setToY(0);
                        FadeTransition ft = new FadeTransition(Duration.millis(300), node);
                        ft.setToValue(1);
                        new ParallelTransition(tt, ft).play();
                    }
                });
                pause.play();
            });
            timeline.getKeyFrames().add(kf);
        }

        timeline.play();
    }

    private void BMIvalues(double value) {
        if (value <= 18.5) {
            System.out.println("\nMenampilkan Gambar... Kurus");
            Image image = getImageFromCache("kurus", "/images/bmikurus.png");
            UpdateFotoBMI(image);
            indikatorIdeal.setText("Berat badan kurang");

        } else if (value > 18.5 && value < 25) {
            System.out.println("\nMenampilkan Gambar... Ideal");
            Image image = getImageFromCache("bminormal", "/images/bminormal.png");
            UpdateFotoBMI(image);
            indikatorIdeal.setText("Ideal");

        } else if (value >= 25 && value < 30) {
            System.out.println("\nMenampilkan Gambar... Kelebihan Berat Badan");
            Image image = getImageFromCache("kelebihan", "/images/bmiobesitas.png");
            UpdateFotoBMI(image);
            indikatorIdeal.setText("Kelebihan Berat Badan");

        } else if (value >= 30 && value < 35) {
            System.out.println("\nMenampilkan Gambar... Obesitas 1");
            Image image = getImageFromCache("obesitas1", "/images/bmiobesitas.png");
            UpdateFotoBMI(image);
            indikatorIdeal.setText("Obesitas 1");

        } else if (value >= 35 && value < 40) {
            System.out.println("\nMenampilkan Gambar... Obesitas 2");
            Image image = getImageFromCache("obesitas2", "/images/bmiobesitas.png");
            UpdateFotoBMI(image);
            indikatorIdeal.setText("Obesitas 2");

        } else if (value >= 40) {
            System.out.println("\nMenampilkan Gambar... Obesitas 3");
            Image image = getImageFromCache("obesitas3", "/images/bmiobesitas.png");
            UpdateFotoBMI(image);
            indikatorIdeal.setText("Obesitas 3");
        }
    }




    private void updateGulaDarahColor(){
        angkaGuladarah.setText(String.valueOf(LoginApiV2.gulaDarah));
        Color color = gulaDarahColor(LoginApiV2.gulaDarah);
        CornerRadii radii = new CornerRadii(20);
        tabelGuladarahnormal.setBackground(new Background(new BackgroundFill(color,radii, Insets.EMPTY)));
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
        angkaTekanandarah.setText(String.valueOf(LoginApiV2.TekananDarah));
        Color color = TekananDarahColor(LoginApiV2.TekananDarah);
        CornerRadii radii = new CornerRadii(20);
        tabelTekanandarahnormal.setBackground(new Background(new BackgroundFill(color,radii, Insets.EMPTY)));
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
        try {
            System.out.println("=======================Update Target Lari=========================");
            for (Map.Entry<String, Double> LastEntry : LoginApiV2.Target.entrySet()) {
                System.out.println("Tanggal : " + LastEntry.getKey() + "  Jarak : " + LastEntry.getValue());
                targetLari = LastEntry;
                LoginApiV2.LastTarget = LastEntry.getValue();
            }
            tanggal.setText(targetLari.getKey());
            JarakLari.setText("Lari " + targetLari.getValue().toString() + " KM");
            System.out.println("===================================================================");
        }catch (NullPointerException e){
            System.err.println("Terjadi Eror");
            System.out.println("Pesan Eror : " + e.getMessage());

            // Set default value ketika terjadi NullPointerException
            tanggal.setText("Belum Ada Data");
            JarakLari.setText("Lari 0 KM");
            // Optional: Set nilai default ke targetLari dan LoginApiV2.LastTarget
            targetLari = new AbstractMap.SimpleEntry<>("Belum Ada", 0.0);
            LoginApiV2.Target.put(targetLari.getKey(), targetLari.getValue());
            LoginApiV2.LastTarget = 0.0;
        }
    }

    private void UpdateFotoBMI(Image fotoBMI){
        fotoBmi.setImage(fotoBMI);
    }

    private Image getImageFromCache(String key, String path) {
        return imageCache.computeIfAbsent(key, k -> new Image(getClass().getResource(path).toExternalForm()));
    }
}



