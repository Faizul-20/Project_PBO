package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {
    @FXML
    private TextField UsernameText;
    @FXML
    private PasswordField PasswordText;
    @FXML
    private TextField BB_TB;
    @FXML
    private DatePicker DatePicking;
    @FXML
    private Button SignUpBtn;
    @FXML
    private Button LeftSignUpBtn;

    public void initialize(){
        UsernameText.setOnAction(e -> BB_TB.requestFocus());
        BB_TB.setOnAction(e-> DatePicking.requestFocus());
        DatePicking.setOnAction(e-> PasswordText.requestFocus());
        PasswordText.setOnAction(e-> SignUpBtn.fire());

    }

    public void SignUpHandler(){
    }


}
