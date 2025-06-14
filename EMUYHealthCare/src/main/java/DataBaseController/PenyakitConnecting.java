package DataBaseController;

import API.PenyakitAPI;
import java.sql.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PenyakitConnecting extends ConnectionData implements SQLConnection{
    private final String QUERY_selectData = "SELECT Nama, Gejala FROM JenisPenyakit";

    @Override
    public void ConnectToDatabase(String Url) {
        try {
            Connection connection = DriverManager.getConnection(Url);
            System.out.println("Data Berhasil Terhubung!!");
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Data Tidak Terhubung Periksa Koneksi Anda");
            System.out.println("Pesan Eror : " + e.getMessage());
        }
    }

    public HashMap<String, String> AddPenyakit(){
        try{
            Connection connection = DriverManager.getConnection(getPENYAKIT_DATA());
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(QUERY_selectData);
            while (rs.next()){
                String nama = rs.getString("Nama");
                String gejala = rs.getString("Gejala");
                PenyakitAPI penyakitAPI = new PenyakitAPI(gejala, nama);
                PenyakitAPI.DataPenyakit.put(gejala, nama);
            }
            rs.close();
            statement.close();
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return PenyakitAPI.DataPenyakit;
    }
    
}
