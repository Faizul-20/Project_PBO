package API;

import API.Testing.TestingDb1;
import API.Testing.UserConnectingtest;
import Controller.SceneController;
import DataBaseController.UserConnecting;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class LoginApiV2 {
    public static String Username;
    public static String Password;
    public static double tinggiBadan;
    public static double beratBadan;
    public static double BMIIndeksBadan;
    public static double gulaDarah;
    public static double TekananDarah;
    public static Map<String,Double> Target = new LinkedHashMap<>();

    UserConnecting userConnecting = new UserConnecting();
    SceneController sceneController = new SceneController();

    public LoginApiV2(String Username, String Password){
        this.Username = Username;
        this.Password = Password;
    }

    public void CekValue(){
        System.out.println("Username : " + Username);
        System.out.println("Password : " + Password);
    }

    public void Login(){
        if (userConnecting.SignInV2(Username,Password)){
            System.out.println("Login Successful");
            System.out.println("Username : " + Username);
            System.out.println("Password : " + Password);
            System.out.println("BMI : " + BMICalculate(tinggiBadan,beratBadan));
            System.out.println("Gula Darah : " + gulaDarah);
            System.out.println("Tekanan Darah : " + TekananDarah);
            System.out.println("Tinggi Badan : " + tinggiBadan);
            System.out.println("Berat Badan : " + beratBadan);
            cetakTarget();
            sceneController.SceneChange(sceneController.getDASHBOARD_LINK());

        }else {
            System.out.println("Login Failed");
            Username = null;
            Password = null;
        }
    }


    public double BMICalculate(double TinggiBadan,double BeratBadan){
        double BMI = BeratBadan/Math.pow((TinggiBadan/100),2);
        BigDecimal bigDecimal = new BigDecimal(BMI).setScale(1, BigDecimal.ROUND_HALF_UP);
        BMIIndeksBadan = bigDecimal.doubleValue();
        return BMIIndeksBadan;
    }
    private void cetakTarget(){
        Iterator it = Target.entrySet().iterator();
        System.out.println("Cetak Target " +  Target.size() + "Buah");
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " : " + pair.getValue());
        }
    }



    public static String getUsername() {
        return Username;
    }

    public static String getPassword() {
        return Password;
    }

    public static double getTinggiBadan() {
        return tinggiBadan;
    }

    public static double getBeratBadan() {
        return beratBadan;
    }

    public static double getBMIIndeksBadan() {
        return BMIIndeksBadan;
    }
}
