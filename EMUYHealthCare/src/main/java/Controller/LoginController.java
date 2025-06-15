package Controller;

import API.LoginAPI;
import API.LoginApiV2;
import API.SignUpAPI;
import DataBaseController.UserConnecting;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private AnchorPane layer1;

    @FXML
    private AnchorPane layer2;

    @FXML
    private Label Logintext1;

    @FXML
    private Label Logintext2;

    @FXML
    private Button BtnSi;

    @FXML
    private Label daftar1;

    @FXML
    private Label daftar2;

    @FXML
    private Button BtnSu;

    @FXML
    private Label judul;

    @FXML
    private Label signUp;

    @FXML
    private TextField signUpusername;

    @FXML
    private PasswordField signInpass;

    @FXML
    private JFXButton signInlogin;

    @FXML
    private TextField signInusername;

    @FXML
    private DatePicker signUplahir;

    @FXML
    private TextField signUpBB;

    @FXML
    private TextField signUpTB;

    @FXML
    private PasswordField signUppass;

    @FXML
    private JFXButton signUpbutton;

    @FXML
    private Label signIn;

    @FXML
    private ImageView gambar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Menyembunyikan elemen-elemen saat aplikasi dimulai
        gambar.setVisible(false);
        Logintext1.setVisible(false);
        Logintext2.setVisible(false);
        BtnSi.setVisible(false);
        BtnSu.setVisible(false);
        daftar1.setVisible(false);
        daftar2.setVisible(false);
        judul.setVisible(true);

        // Mengatur tampilan awal form
        signIn.setVisible(false);
        signInusername.setVisible(false);
        signInpass.setVisible(false);
        signInlogin.setVisible(false);

        signUp.setVisible(false);
        signUpusername.setVisible(false);
        signUplahir.setVisible(false);
        signUpBB.setVisible(false);
        signUpTB.setVisible(false);
        signUppass.setVisible(false);
        signUpbutton.setVisible(false);

        // Menambahkan event handler untuk tombol-tombol
        BtnSi.setOnMouseClicked(this::btnSignin);
        BtnSu.setOnMouseClicked(this::btnSignup);
        signInlogin.setOnMouseClicked(this::handleLogin);
        signUpbutton.setOnMouseClicked(this::handleSignup);

        setupEnterKeyHandling();
        btnSignin(null);

    }

    private void handleLogin(MouseEvent mouseEvent) {
    }

    private void setupEnterKeyHandling() {
        signInusername.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                signInpass.requestFocus();
            }
        });

        signInpass.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin();
            }
        });
    }

    private void addFadeTransition(Node node, boolean fadeIn) {
        FadeTransition fade = new FadeTransition(Duration.millis(300), node);
        fade.setFromValue(fadeIn ? 0.0 : 1.0);
        fade.setToValue(fadeIn ? 1.0 : 0.0);
        fade.play();
    }

    @FXML
    private void btnSignin(MouseEvent event) {
        // Animasi slide untuk layer2 (ke kanan)
        TranslateTransition slideLayer2 = new TranslateTransition(Duration.seconds(0.7), layer2);
        slideLayer2.setToX(0);

        // Animasi slide untuk layer1 (ke kiri)
        TranslateTransition slideLayer1 = new TranslateTransition(Duration.seconds(0.7), layer1);
        slideLayer1.setToX(0);

        // Jalankan kedua animasi
        slideLayer2.play();
        slideLayer1.play();
        // menunggu layer 2 selasai slide ke kanan baru sistem beroperasi
        slideLayer2.setOnFinished((e) -> {
            // Fade out elemen signup
            addFadeTransition(signUp, false);
            addFadeTransition(signUpusername, false);
            addFadeTransition(signUplahir, false);
            addFadeTransition(signUpBB, false);
            addFadeTransition(signUpTB, false);
            addFadeTransition(signUppass, false);
            addFadeTransition(signUpbutton, false);

            // Fade in elemen signin
            addFadeTransition(signIn, true);
            addFadeTransition(signInusername, true);
            addFadeTransition(signInpass, true);
            addFadeTransition(signInlogin, true);

            // Mengubah visibilitas button dan label
            gambar.setVisible(true);
            BtnSi.setVisible(false);
            BtnSu.setVisible(true);
            Logintext1.setVisible(false);
            Logintext2.setVisible(false);
            daftar1.setVisible(true);
            daftar2.setVisible(true);

            // Mengubah visibilitas form
            signIn.setVisible(true);
            signInusername.setVisible(true);
            signInpass.setVisible(true);
            signInlogin.setVisible(true);

            signUp.setVisible(false);
            signUpusername.setVisible(false);
            signUplahir.setVisible(false);
            signUpBB.setVisible(false);
            signUpTB.setVisible(false);
            signUppass.setVisible(false);
            signUpbutton.setVisible(false);
        });
    }

    @FXML
    private void btnSignup(MouseEvent event) {
        // Animasi slide untuk layer2 (ke kiri)
        TranslateTransition slideLayer2 = new TranslateTransition(Duration.seconds(0.7), layer1);
        slideLayer2.setToX(-465);

        // Animasi slide untuk layer1 (ke kanan)
        TranslateTransition slideLayer1 = new TranslateTransition(Duration.seconds(0.7), layer2);
        slideLayer1.setToX(465);

        // Jalankan kedua animasi
        slideLayer2.play();
        slideLayer1.play();

        slideLayer2.setOnFinished((e) -> {
            // Fade out elemen signin
            addFadeTransition(signIn, false);
            addFadeTransition(signInusername, false);
            addFadeTransition(signInpass, false);
            addFadeTransition(signInlogin, false);

            // Fade in elemen signup
            addFadeTransition(signUp, true);
            addFadeTransition(signUpusername, true);
            addFadeTransition(signUplahir, true);
            addFadeTransition(signUpBB, true);
            addFadeTransition(signUpTB, true);
            addFadeTransition(signUppass, true);
            addFadeTransition(signUpbutton, true);

            judul.setVisible(true);
            // Mengubah visibilitas button dan label
            gambar.setVisible(true);
            BtnSi.setVisible(true);
            BtnSu.setVisible(false);
            Logintext1.setVisible(true);
            Logintext2.setVisible(true);
            daftar1.setVisible(false);
            daftar2.setVisible(false);

            // Mengubah visibilitas form
            signIn.setVisible(false);
            signInusername.setVisible(false);
            signInpass.setVisible(false);
            signInlogin.setVisible(false);

            signUp.setVisible(true);
            signUpusername.setVisible(true);
            signUplahir.setVisible(true);
            signUpBB.setVisible(true);
            signUpTB.setVisible(true);
            signUppass.setVisible(true);
            signUpbutton.setVisible(true);
        });
    }

    @FXML
    private void handleLogin() {
        String username = signInusername.getText().trim();
        String password = signInpass.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username and password are empty");
            alert.AlertWarning("Warning!!", "Username and password are empty");
            return;
        }


        LoginApiV2 loginAPI = new LoginApiV2(username,password);
        loginAPI.CekValue();
        loginAPI.Login();
        signInusername.clear();
        signInpass.clear();
    }

    @FXML
    private void handleSignup(MouseEvent event) {
        String username = signUpusername.getText().trim();
        String password = signUppass.getText().trim();
        //Maaf ini kebalik jadi nanti benerin sendiri
        String bb = signUpTB.getText().trim();
        String tb = signUpBB.getText().trim();
        String UlangTahun = signUplahir.getValue().toString();


        if (username.length() < 3) {
            System.out.println("Username is too short");
            alert.AlertEror("Eror!", "Username is too short");
            return;
        }
        if (password.length() < 6) {
            System.out.println("Password is less than 6 characters");
            alert.AlertEror("Eror!", "Password is less than 6 characters");
            return;
        }

        if (signUplahir.getValue() == null) {
            System.out.println("Tanggal lahir harus diisi");
            alert.AlertEror("Registrasi Error", "Tanggal lahir harus diisi!");
            return;
        }

        try {
            double beratBadan = Double.parseDouble(bb);
            double tinggiBadan = Double.parseDouble(tb);

            if (beratBadan <= 0 || tinggiBadan <= 0) {
                alert.AlertInfo("Input Error", "Berat badan dan tinggi badan harus lebih dari 0!");
                return;
            }
            try {
                if (!username.isEmpty() && !password.isEmpty() && !bb.isEmpty() && !tb.isEmpty() && !UlangTahun.isEmpty()) {
                    SignUpAPI SignUp = new SignUpAPI(username, password, UlangTahun, bb, tb);
                    SignUp.CekValue();
                    SignUp.PostDataUserTodatabase();
                } else {
                    throw new NullPointerException("You Have to full fill all of questions");
                }
            }catch (NullPointerException e){
                System.err.println("Pesan Eror : " + e.getMessage());
                alert.AlertWarning("Pesan Eror", e.getMessage());
            }

            alert.AlertInfo("Registrasi Berhasil", "Akun berhasil dibuat! Silakan login.");

            // Reset form
            signUpusername.clear();
            signUppass.clear();
            signUpBB.clear();
            signUpTB.clear();
            signUplahir.setValue(null);

            // Beralih ke tampilan login
            btnSignin(null);

        } catch (NumberFormatException e) {
            System.out.println("Pesan Eror : " + e.getMessage());
            System.out.println("Berat badan dan tinggi badan harus berupa angka!");
            alert.AlertWarning("Input Error", "Berat badan dan tinggi badan harus berupa angka!");
        }
    }


}
