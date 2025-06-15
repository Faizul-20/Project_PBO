package API;

import Controller.SceneController;
import DataBaseController.UserConnecting;
import javafx.scene.control.Alert;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class LoginApiV2 {
    public static int ID = 0;
    public static String Username;
    public static String Password;
    public static double tinggiBadan;
    public static double beratBadan;
    public static double BMIIndeksBadan;
    public static double gulaDarah;
    public static double TekananDarah;
    public static Map<String,Double> Target = new LinkedHashMap<>();
    private final String USER_DATA = "jdbc:sqlite:EMUYHealthCare/Database/User/Users.db";

    UserConnecting userConnecting = new UserConnecting();
    SceneController sceneController = new SceneController();

    public LoginApiV2(String Username, String Password){
        this.Username = Username;
        this.Password = Password;
    }
    public LoginApiV2(){}

    public void RefreshData(){

    }
    public static void Logout() {
        Map<String,Double> target = new LinkedHashMap<>();
        setID(0);
        setTarget(target);
        setUsername(null);
        setPassword(null);
        setBeratBadan(0);
        setBMIIndeksBadan(0);
        setGulaDarah(0);
        setTarget(target);
    }

    public static void CetakValue() {
        System.out.println("===================Cek Value=======================");
        System.out.println("Id : " + ID);
        System.out.println("Username: " + Username + "\n Password: " + Password);
        System.out.println("Target: " + Target);
        System.out.println("Berat: " + beratBadan);
        System.out.println("BMI: " + BMIIndeksBadan);
        System.out.println("Gula Darah : " + gulaDarah);
        System.out.println("Tekanan Darah : " + TekananDarah);
        System.out.println("===================================================");
    }

    public void CekValue(){
        System.out.println("Username : " + Username);
        System.out.println("Password : " + Password);
    }

    public void Login(){
        if (userConnecting.SignInV2(Username,Password)){
            System.out.println("Login Successful");
            System.out.println("======================User Masuk============================");
            System.out.println("Id : " + ID);
            System.out.println("Username : " + Username);
            System.out.println("Password : " + Password);
            System.out.println("BMI : " + BMICalculate(tinggiBadan,beratBadan));
            System.out.println("Gula Darah : " + gulaDarah);
            System.out.println("Tekanan Darah : " + TekananDarah);
            System.out.println("Tinggi Badan : " + tinggiBadan);
            System.out.println("Berat Badan : " + beratBadan);
            System.out.println("===========================================================");
            sceneController.SceneChange(sceneController.getDASHBOARD_LINK(),"DashBoard");
            cetakTarget();

        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Username or Password is incorrect");
            alert.showAndWait();
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
        System.out.println("================= Daftar Target " + getUsername() +" ============");
        Iterator it = Target.entrySet().iterator();
        System.out.println("Cetak Target " +  Target.size() + "Buah\n");
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " : " + pair.getValue());
        }
        System.out.println("=============================================================================");
    }

    public void updateGulaDarah(double GulaDarah){
        String query = "UPDATE riwayat_gula_tekanan SET gula_darah = ?, tekanan_darah = ? WHERE id = ?";

        try {
            Connection connection = DriverManager.getConnection(getUserData());
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1,GulaDarah);
            preparedStatement.setDouble(2,this.TekananDarah);
            preparedStatement.setInt(3,this.ID);

            preparedStatement.executeUpdate();
            System.out.println("Data Gula Darah Berhasil Diupdate");
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("\nTerjadi Kesalahan");
            System.out.println("Data Tekanan Darah Tidak Berhasil Diupdate");
            System.err.println("Pesan Eror : " + e.getMessage() + "\n");
        }
    }
    public void updateTekananDarah(double TekananDarah){
        String query = "UPDATE riwayat_gula_tekanan SET gula_darah = ?, tekanan_darah = ? WHERE id = ?";

        try {
            Connection connection = DriverManager.getConnection(getUserData());
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1,this.gulaDarah);
            preparedStatement.setDouble(2,TekananDarah);
            preparedStatement.setInt(3,this.ID);

            preparedStatement.executeUpdate();
            System.out.println("Data Gula Darah Berhasil Diupdate");
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("\nTerjadi Kesalahan");
            System.out.println("Data Tekanan Darah Tidak Berhasil Diupdate");
            System.err.println("Pesan Eror : " + e.getMessage() + "\n");
        }
    }

    public void updateTinggiBadan(double TinggiBadan){
        String Query = "UPDATE users SET tinggi_badan = ? WHERE id = ?";
        try{
            Connection connection = DriverManager.getConnection(getUserData());
            PreparedStatement preparedStatement = connection.prepareStatement(Query);

            preparedStatement.setDouble(1,TinggiBadan);
            preparedStatement.setInt(2,this.ID);
            preparedStatement.executeUpdate();
            System.out.println("Data Tinggi Badan Berhasil Diupdate");
            preparedStatement.close();
            connection.close();
        }catch (SQLException e){
            System.out.println("\nTerjadi Kesalahan");
            System.out.println("Data Tinggi anda Tidak Berhasil Diupdate");
            System.err.println("Pesan Eror : " + e.getMessage() + "\n");

        }
    }

    public void updateBeratBadan(double BeratBadan){
        String Query = "UPDATE users SET tinggi_badan = ? WHERE id = ?";
        try{
            Connection connection = DriverManager.getConnection(getUserData());
            PreparedStatement preparedStatement = connection.prepareStatement(Query);

            preparedStatement.setDouble(1,BeratBadan);
            preparedStatement.setInt(2,this.ID);
            preparedStatement.executeUpdate();
            System.out.println("Data Berat Badan Berhasil Diupdate");
            preparedStatement.close();
            connection.close();
        }catch (SQLException e){
            System.out.println("\nTerjadi Kesalahan");
            System.out.println("Data Berat Badan Tidak Berhasil Diupdate");
            System.err.println("Pesan Eror : " + e.getMessage() + "\n");

        }
    }
    public void updateTargetLari(String TanggalLari,int JarakLari){
        String Query = "INSERT INTO riwayat VALUES(?,?,?)";
        try{
            Connection connection = DriverManager.getConnection(getUserData());
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setDouble(3,JarakLari);
            preparedStatement.setInt(1,this.ID);
            preparedStatement.setString(2,TanggalLari);
            LoginApiV2.Target.put(TanggalLari,(double)JarakLari);
            preparedStatement.executeUpdate();
            System.out.println("Data Target Lari Berhasil Diupdate");
            preparedStatement.close();
            connection.close();
        }catch (SQLException e){
            System.out.println("\nTerjadi Kesalahan");
            System.out.println("Data Target Lari Tidak Berhasil Diupdate");
            System.err.println("Pesan Eror : " + e.getMessage() + "\n");
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

    public static void setUsername(String username) {
        Username = username;
    }

    public static void setPassword(String password) {
        Password = password;
    }

    public static void setTinggiBadan(double tinggiBadan) {
        LoginApiV2.tinggiBadan = tinggiBadan;
    }

    public static void setBeratBadan(double beratBadan) {
        LoginApiV2.beratBadan = beratBadan;
    }

    public static void setBMIIndeksBadan(double BMIIndeksBadan) {
        LoginApiV2.BMIIndeksBadan = BMIIndeksBadan;
    }

    public static void setGulaDarah(double gulaDarah) {
        LoginApiV2.gulaDarah = gulaDarah;
    }

    public static void setTarget(Map<String, Double> target) {
        Target = target;
    }
    public static void setID(int Id) {
        ID = Id;
    }
    public String getUserData() {
        return USER_DATA;
    }
}
