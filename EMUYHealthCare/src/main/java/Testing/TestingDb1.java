package Testing;

import Controller.SceneController;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestingDb1 {
    public static String Username;
    public static String Password;
    public static double tinggiBadan;
    public static double beratBadan;
    public static double BMIIndeksBadan;
    public static double gulaDarah;
    public static Map<String,Double> Target = new LinkedHashMap<>();
    private final String SIGN_IN ="SELECT * FROM users WHERE Username = ? AND Password = ?";

    UserConnectingtest  userConnecting = new UserConnectingtest();
    SceneController sceneController = new SceneController();

    public TestingDb1(String Username, String Password){
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
            System.out.println("Username : " + Username);
            System.out.println("Password : " + Password);
            System.out.println("BMI : " + BMICalculate(tinggiBadan,beratBadan));
            System.out.println("Gula Darah : " + gulaDarah);
            System.out.println("Tinggi Badan : " + tinggiBadan);
            System.out.println("Berat Badan : " + beratBadan);
            cetakTarget();

        }else {
            System.out.println("Login Failed");
            Username = null;
            Password = null;
        }
    }

    public String getSIGN_IN() {
        return SIGN_IN;
    }

    public double BMICalculate(double TinggiBadan,double BeratBadan){

        return BMIIndeksBadan = BeratBadan/Math.pow((TinggiBadan/100),2);
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
