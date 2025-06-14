package DataBaseController;

import API.TestingDb.ConnectionData;


import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PenyakitConnecting extends ConnectionData implements SQLConnection{
    private final String QUERY_selectTabelGejala = "SELECT kodeGejala, gejala FROM gejala";
    private final String QUERY_selectTabelPenyakitGejala = "SELECT Id_penyakit, kodeGejala FROM penyakit_gejala";

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

    public LinkedHashMap<String, ArrayList<String>> addMapGejala() {
        LinkedHashMap<String, ArrayList<String>> mapGejala = new LinkedHashMap<>();

        try {
            Connection connection = DriverManager.getConnection(getPENYAKIT_DATA());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY_selectTabelGejala);

            while (resultSet.next()) {
                String kodeGejala = resultSet.getString("kodeGejala");
                String gejala = resultSet.getString("gejala");

                // Split dan bersihkan kata dari tanda baca
                String[] splitGejala = gejala.toLowerCase().split("[\\p{Punct}\\s]+");
                ArrayList<String> kataList = new ArrayList<>(Arrays.asList(splitGejala));

                mapGejala.put(kodeGejala, kataList);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mapGejala;
    }

    public ArrayList<String> getMatchedKodeGejala(
            HashSet<String> inputUser,
            HashMap<String, ArrayList<String>> mapGejala
    ) {
        ArrayList<String> matchedKode = new ArrayList<>();

        for (String kode : mapGejala.keySet()) {
            ArrayList<String> gejalaWords = mapGejala.get(kode);

            // Cek apakah ada irisan antara HashSet dan value (ArrayList)
            for (String kata : gejalaWords) {
                if (inputUser.contains(kata)) {
                    matchedKode.add(kode);
                    break; // cukup 1 kata cocok, langsung ambil key
                }
            }
        }

        return matchedKode;
    }

    public LinkedHashMap<Integer, ArrayList<String>> tabelPenyakitGejala(){
        LinkedHashMap<String, ArrayList<String>> mapPenyakitGejala = new LinkedHashMap<>();

        try {
            Connection connection = DriverManager.getConnection(getPENYAKIT_DATA());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY_selectTabelPenyakitGejala);

            while (resultSet.next()){
                Integer Id_Penyakit = resultSet.getString("Id_penyakit");
                String kodeGejala = resultSet.getS
            }






        }catch (SQLException e){
            e.printStackTrace();
        }

    }








}
