package DataBaseController;

import API.TestingDb.ConnectionData;


import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PenyakitConnecting extends ConnectionData implements SQLConnection{
    private final String QUERY_selectGejala = "SELECT gejalaPenyakit FROM gejala";
    private final String QUERY_selectKode = "SELECT kodeGejala FROM gejala";
    private final String QUERY_selectPenyakit = "SELECT nama FROM penyakit";

    public static ArrayList<ArrayList<String>> result = new ArrayList<>();
    public static ArrayList<String> kodeGejala = new ArrayList<>();

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

   public ArrayList<ArrayList<String>> addGejala(){
        try {
            Connection connection = DriverManager.getConnection(getPENYAKIT_DATA());
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(QUERY_selectGejala);

            while(rs.next()){
                String gejala = rs.getString("gejalaPenyakit");
                String[] splitGejala = gejala.split("[\\p{Punct}\\s]+");
                ArrayList<String> listGejala = new ArrayList<>(Arrays.asList(splitGejala));
                result.add(listGejala);
            }

            rs.close();
            statement.close();
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();;
        }

        return result;
   }

   public ArrayList<String> addKode(){
        try {
            Connection connection = DriverManager.getConnection(getPENYAKIT_DATA());
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(QUERY_selectKode);

            while (rs.next()) {
                String kode = rs.getString("kodeGejala");
                kodeGejala.add(kode);
            }

            rs.close();
            statement.close();
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return kodeGejala;
   }

   public ArrayList<String> addPenyakit(){
        ArrayList<String> listPenyakit = new ArrayList<>();

        try{
            Connection connection = DriverManager.getConnection(getPENYAKIT_DATA());
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(QUERY_selectPenyakit);

            while (rs.next()){
                String penyakit = rs.getString("nama");
                listPenyakit.add(penyakit);
            }

            rs.close();
            statement.close();
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return listPenyakit;

   }


    
}
