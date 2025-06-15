package DataBaseController;

import API.PenyakitAPI;
import API.TestingDb.ConnectionData;
import API.TestingDb.DataConnecting;
import chatBotEngine.Tokenization;


import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PenyakitConnecting extends ConnectionData implements SQLConnection{
    private final String QUERY_selectTabelGejala = "SELECT kodeGejala, gejala FROM gejala";
    private final String QUERY_selectTabelPenyakitGejala = "SELECT Id_penyakit, kodeGejala FROM penyakit_gejala";
    private final String QUERY_selectTabelPenyakit = "SELECT Id_penyakit, nama_penyakit FROM penyakit";

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

    public HashMap<String, String> getKodeKeGejalaAsli() {
        HashMap<String, String> map = new HashMap<>();
        try {
            Connection connection = DriverManager.getConnection(getPENYAKIT_DATA());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY_selectTabelGejala);

            while (resultSet.next()) {
                String kodeGejala = resultSet.getString("kodeGejala").trim();
                String gejala = resultSet.getString("gejala").trim();
                map.put(kodeGejala, gejala);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return map;
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

        // ✅ Bersihkan spasi tambahan (jika ada)
        for (int i = 0; i < matchedKode.size(); i++) {
            matchedKode.set(i, matchedKode.get(i).trim());
        }

        return matchedKode;
    }


    public LinkedHashMap<Integer, ArrayList<String>> tabelPenyakitGejala(){
        LinkedHashMap<Integer, ArrayList<String>> mapPenyakitGejala = new LinkedHashMap<>();

        try {
            Connection connection = DriverManager.getConnection(getPENYAKIT_DATA());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY_selectTabelPenyakitGejala);

            while (resultSet.next()){
                Integer Id_Penyakit = resultSet.getInt("Id_penyakit");
                String kodeGejala = resultSet.getString("kodeGejala");
                String[] kodeSplit = kodeGejala.split("\\s*,\\s*"); // sudah bagus: hilangkan spasi antar koma
                ArrayList<String> kode = new ArrayList<>();
                for (String k : kodeSplit) {
                    kode.add(k.trim()); // jika yakin semua angka, bisa tambahkan .toString() atau biarkan sebagai String
                }


                mapPenyakitGejala.put(Id_Penyakit,kode);
            }

            resultSet.close();
            statement.close();
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return mapPenyakitGejala;

    }

    public LinkedHashMap<Integer, String> tabelPenyakit(){
        LinkedHashMap<Integer, String> mapPenyakit = new LinkedHashMap<>();

        try {
            Connection connection = DriverManager.getConnection(getPENYAKIT_DATA());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY_selectTabelPenyakit);

            while (resultSet.next()){
                Integer Id_Penyakit = resultSet.getInt("Id_penyakit");
                String NamaPenyakit = resultSet.getString("nama_penyakit");
                mapPenyakit.put(Id_Penyakit, NamaPenyakit);
            }

            resultSet.close();
            statement.close();
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return mapPenyakit;

    }

    public Map<String, Object> getTop3PenyakitBesertaGejalaTidakTerpakai(ArrayList<String> matchedKode) {
        LinkedHashMap<Integer, ArrayList<String>> dataPenyakit = tabelPenyakitGejala();
        HashMap<Integer, Integer> skorPenyakit = new HashMap<>();

        // Hitung skor kesesuaian
        for (Map.Entry<Integer, ArrayList<String>> entry : dataPenyakit.entrySet()) {
            Integer idPenyakit = entry.getKey();
            ArrayList<String> daftarKode = entry.getValue();

            int skor = 0;
            for (String kode : daftarKode) {
                if (matchedKode.contains(kode.trim())) {
                    skor++;
                }
            }

            if (skor > 0) {
                skorPenyakit.put(idPenyakit, skor);
            }
        }

        // Urutkan berdasarkan skor
        List<Map.Entry<Integer, Integer>> sortedList = new ArrayList<>(skorPenyakit.entrySet());
        sortedList.sort((a, b) -> b.getValue() - a.getValue());

        // Ambil 3 teratas
        ArrayList<Integer> top3IdPenyakit = new ArrayList<>();
        for (int i = 0; i < Math.min(3, sortedList.size()); i++) {
            top3IdPenyakit.add(sortedList.get(i).getKey());
        }

        // Gabungkan semua kode gejala dari top 3 penyakit
        HashSet<String> kodeGejalaTop3 = new HashSet<>();
        for (Integer id : top3IdPenyakit) {
            ArrayList<String> kodeGejala = dataPenyakit.get(id);
            if (kodeGejala != null) {
                for (String kode : kodeGejala) {
                    kodeGejalaTop3.add(kode.trim());
                }
            }
        }

        // Cari kode gejala di top 3 yang tidak cocok dengan input user
        ArrayList<String> kodeTidakTerpakai = new ArrayList<>();
        for (String kode : kodeGejalaTop3) {
            if (!matchedKode.contains(kode)) {
                kodeTidakTerpakai.add(kode);
            }
        }

        // Simpan hasil
        Map<String, Object> hasil = new HashMap<>();
        hasil.put("top3IdPenyakit", top3IdPenyakit);
        hasil.put("kodeTidakTerpakai", kodeTidakTerpakai);

        return hasil;
    }




    public void feedbackChatBot(String userInput) {
        // Koneksi ke DB
        DataConnecting dataConnecting = new DataConnecting();
        dataConnecting.ConnectToDatabase(dataConnecting.getPENYAKIT_DATA());

        // Tokenisasi input user jadi kode gejala
        String inputGejala = userInput.toLowerCase();
        HashSet<String> inputUserSet = Tokenization.Gejala(inputGejala); // set hasil input user
        PenyakitConnecting penyakitConnecting = new PenyakitConnecting();
        LinkedHashMap<String, ArrayList<String>> dataGejala = penyakitConnecting.addMapGejala();

        // Ambil kode gejala hasil pencocokan
        ArrayList<String> matchedKode = penyakitConnecting.getMatchedKodeGejala(inputUserSet, dataGejala);

        // Ambil data semua penyakit dan kode gejalanya
        LinkedHashMap<Integer, ArrayList<String>> tabelPenyakitGejala = penyakitConnecting.tabelPenyakitGejala();

        // ===== IF: Cek apakah matchedKode cocok 100% dengan salah satu baris gejala penyakit =====
        boolean FullMatch = false;
        int IdFullMatch = -1;

        for (Map.Entry<Integer, ArrayList<String>> entry : tabelPenyakitGejala.entrySet()) {
            ArrayList<String> kodeGejalaPenyakit = entry.getValue();
            if (kodeGejalaPenyakit.containsAll(matchedKode)) {
                FullMatch = true;
                IdFullMatch = entry.getKey();
                break;
            }
        }

        //Jika inputan tidak cocok dengan gejala apapun
        if (getMatchedKodeGejala(inputUserSet,dataGejala).isEmpty()){
            PenyakitAPI.gejalaUser = inputGejala;
            PenyakitAPI.feedback = "Gejala tidak cocok dengan penyakait manapun / Gejala diluar data yang ada";
            PenyakitAPI.diagnosa = "Tidak bisa disimpulkan";
        }

        // ===== IF: Semua gejala cocok pada 1 penyakit =====
        if (FullMatch) {
            HashMap<String, String> kodeKeGejalaAsli = penyakitConnecting.getKodeKeGejalaAsli();

            ArrayList<String> daftarGejalaUser = new ArrayList<>();
            for (String kode : matchedKode) {
                if (kodeKeGejalaAsli.containsKey(kode)) {
                    daftarGejalaUser.add(kodeKeGejalaAsli.get(kode));
                }
            }

            // Ambil nama penyakit dari IdFullMatch
            LinkedHashMap<Integer, String> mapPenyakit = penyakitConnecting.tabelPenyakit();
            String namaPenyakit = mapPenyakit.getOrDefault(IdFullMatch, "Tidak Diketahui");

            // Simpan ke API
            PenyakitAPI.gejalaUser = inputGejala;
            PenyakitAPI.feedback = "Anda tampaknya terkena " + namaPenyakit;
            PenyakitAPI.diagnosa = namaPenyakit;
        }

        // Feedback untuk menanyakan sisa gejala dari top3 penyakit yg disimpulkan
        else {
            Map<String, Object> result = penyakitConnecting.getTop3PenyakitBesertaGejalaTidakTerpakai(matchedKode);
            ArrayList<Integer> top3 = (ArrayList<Integer>) result.get("top3IdPenyakit");
            ArrayList<String> tidakTerpakai = (ArrayList<String>) result.get("kodeTidakTerpakai");

            System.out.println("⚠ Tidak ada penyakit yang cocok 100%.");
            System.out.println("🥇 Top 3 kemungkinan penyakit: " + top3);
            System.out.println("❌ Kode gejala tidak terpakai dalam top 3: " + tidakTerpakai);
        }
    }












}
