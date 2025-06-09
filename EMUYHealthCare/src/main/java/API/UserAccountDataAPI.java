package API;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class UserAccountDataAPI {
    String username;
    double tinggiBadan;
    double beratBadan;
    double BMIIndeksBadan;
    double gulaDarah;
    Map<Double, String> Target = new LinkedHashMap<Double, String>();
    String jdbc = "SELECT username,tinggibadan,beratbadan,guladarah,targetLari,tanggalLari FROM dataUser where username = ? ";
    String Db = "jdbc:sqlite:EMUYHealthCare/Database/Testing/AccountTesting.db";

    public void testConnection() {
        try{
            Connection conn = DriverManager.getConnection(Db);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public void getDataUser(String username) {
        try{
            Connection conn = DriverManager.getConnection(Db);
            PreparedStatement ps = conn.prepareStatement(jdbc);
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                this.username = rs.getString("username");
                tinggiBadan = rs.getDouble("tinggibadan");
                beratBadan = rs.getDouble("beratbadan");
                gulaDarah = rs.getDouble("guladarah");
                Target.put(rs.getDouble("targetLari"),rs.getString("TanggalLari"));


            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Map<Double, String> getTarget() {
        return Target;
    }

    public void setTarget(Map<Double, String> target) {
        Target = target;
    }
}
