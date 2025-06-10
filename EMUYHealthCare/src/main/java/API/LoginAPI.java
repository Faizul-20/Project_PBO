package API;

import Controller.SceneController;
import DataBaseController.UserConnecting;

public class LoginAPI {
    String Username;
    String Password;
    double tinggiBadan;
    double beratBadan;
    double BMIIndeksBadan;
    double gulaDarah;

    UserConnecting userConnecting = new UserConnecting();
    SceneController sceneController = new SceneController();

    public LoginAPI(String Username,String Password){
        this.Username = Username;
        this.Password = Password;
    }

    public void CekValue(){
        System.out.println("Username : " + Username);
        System.out.println("Password : " + Password);
    }

    public void Login(){
        if (userConnecting.SignIn(Username,Password)){
            System.out.println("Login Successful");
            Username = null;
            Password = null;
            sceneController.SceneChange(sceneController.getDASHBOARD_LINK());
        }else {
            System.out.println("Login Failed");
            Username = null;
            Password = null;
        }
    }

    public double getTinggiBadan() {
        return tinggiBadan;
    }

    public void setTinggiBadan(double tinggiBadan) {
        this.tinggiBadan = tinggiBadan;
    }

    public double getBeratBadan() {
        return beratBadan;
    }

    public void setBeratBadan(double beratBadan) {
        this.beratBadan = beratBadan;
    }

    public double getBMIIndeksBadan() {
        return BMIIndeksBadan;
    }

    public void setBMIIndeksBadan(double BMIIndeksBadan) {
        this.BMIIndeksBadan = BMIIndeksBadan;
    }

    public double getGulaDarah() {
        return gulaDarah;
    }

    public void setGulaDarah(double gulaDarah) {
        this.gulaDarah = gulaDarah;
    }
}
