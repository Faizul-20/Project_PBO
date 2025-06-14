package Controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

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
    private AnchorPane labelTinggibadan;
    //tabel berat badan
    @FXML
    private AnchorPane tabelBeratbadan;
    @FXML
    private AnchorPane labelBeratbadan;

    //indikator
    @FXML
    private AnchorPane tabelIndikatorbmi;
    @FXML
    private Polygon kursorIndikator;
    @FXML
    private Rectangle tableIndikator;

    //tabel olahraga
    @FXML
    private LineChart tabelOlaraga;

    private LineChart tabelTekanandarah;
}



