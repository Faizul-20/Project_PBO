package DataBaseController;

import API.PenyakitAPI;
import API.TestingDb.ConnectionData;
import API.TestingDb.DataConnecting;
import chatBotEngine.Tokenization;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PenyakitConnecting extends ConnectionData implements SQLConnection {
    private final String QUERY_selectTabelGejala = "SELECT kodeGejala, gejala FROM gejala";
    private final String QUERY_selectTabelPenyakitGejala = "SELECT Id_penyakit, kodeGejala FROM penyakit_gejala";
    private final String QUERY_selectTabelPenyakit = "SELECT Id_penyakit, nama_penyakit FROM penyakit";
    private final String QUERY_selectPenanganan = "SELECT Id_penyakit, penanganan FROM penyakit";

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
        try (Connection connection = DriverManager.getConnection(getPENYAKIT_DATA());
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_selectTabelGejala)) {

            while (resultSet.next()) {
                String kodeGejala = resultSet.getString("kodeGejala");
                String gejala = resultSet.getString("gejala");
                String[] splitGejala = gejala.toLowerCase().split("[\\p{Punct}\\s]+");
                mapGejala.put(kodeGejala.trim(), new ArrayList<>(Arrays.asList(splitGejala)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mapGejala;
    }

    public HashMap<String, String> getKodeKeGejalaAsli() {
        HashMap<String, String> map = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(getPENYAKIT_DATA());
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_selectTabelGejala)) {

            while (resultSet.next()) {
                map.put(resultSet.getString("kodeGejala").trim(), resultSet.getString("gejala").trim());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public ArrayList<String> getMatchedKodeGejala(HashSet<String> inputUser, HashMap<String, ArrayList<String>> mapGejala) {
        ArrayList<String> matchedKode = new ArrayList<>();
        for (Map.Entry<String, ArrayList<String>> entry : mapGejala.entrySet()) {
            for (String kata : entry.getValue()) {
                if (inputUser.contains(kata)) {
                    matchedKode.add(entry.getKey().trim());
                    break;
                }
            }
        }
        return matchedKode;
    }

    public LinkedHashMap<Integer, ArrayList<String>> tabelPenyakitGejala() {
        LinkedHashMap<Integer, ArrayList<String>> map = new LinkedHashMap<>();
        try (Connection connection = DriverManager.getConnection(getPENYAKIT_DATA());
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_selectTabelPenyakitGejala)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("Id_penyakit");
                String[] kodeArray = resultSet.getString("kodeGejala").split("\\s*,\\s*");
                ArrayList<String> kodeList = new ArrayList<>();
                for (String k : kodeArray) {
                    kodeList.add(k.trim());
                }
                map.put(id, kodeList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public LinkedHashMap<Integer, String> tabelPenyakit() {
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
        try (Connection connection = DriverManager.getConnection(getPENYAKIT_DATA());
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_selectTabelPenyakit)) {

            while (resultSet.next()) {
                map.put(resultSet.getInt("Id_penyakit"), resultSet.getString("nama_penyakit"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public LinkedHashMap<Integer, String> addPenanganan() {
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
        try (Connection connection = DriverManager.getConnection(getPENYAKIT_DATA());
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_selectPenanganan)) {

            while (resultSet.next()) {
                map.put(resultSet.getInt("Id_penyakit"), resultSet.getString("penanganan"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public Map<String, Object> getTop3PenyakitBesertaGejalaTidakTerpakai(ArrayList<String> matchedKode) {
        LinkedHashMap<Integer, ArrayList<String>> data = tabelPenyakitGejala();
        HashMap<Integer, Integer> skor = new HashMap<>();

        for (Map.Entry<Integer, ArrayList<String>> entry : data.entrySet()) {
            int s = 0;
            for (String kode : entry.getValue()) {
                if (matchedKode.contains(kode.trim())) s++;
            }
            if (s > 0) skor.put(entry.getKey(), s);
        }

        List<Map.Entry<Integer, Integer>> sorted = new ArrayList<>(skor.entrySet());
        sorted.sort((a, b) -> b.getValue() - a.getValue());

        ArrayList<Integer> top3 = new ArrayList<>();
        for (int i = 0; i < Math.min(3, sorted.size()); i++) {
            top3.add(sorted.get(i).getKey());
        }

        HashSet<String> semuaGejalaTop3 = new HashSet<>();
        for (Integer id : top3) {
            semuaGejalaTop3.addAll(data.getOrDefault(id, new ArrayList<>()));
        }

        ArrayList<String> tidakTerpakai = new ArrayList<>();
        for (String kode : semuaGejalaTop3) {
            if (!matchedKode.contains(kode.trim())) tidakTerpakai.add(kode.trim());
        }

        Map<String, Object> hasil = new HashMap<>();
        hasil.put("top3IdPenyakit", top3);
        hasil.put("kodeTidakTerpakai", tidakTerpakai);
        return hasil;
    }

    public void feedbackChatBot(String userInput) {
        String inputGejala = userInput.toLowerCase();
        HashSet<String> inputUser = Tokenization.Gejala(inputGejala);

        //tes inputan
        System.out.println(inputUser);
        //

        LinkedHashMap<String, ArrayList<String>> dataGejala = addMapGejala();

        //Tes add map gejala
        System.out.println("Map gejala: " + dataGejala);

        ArrayList<String> matchedKode = new ArrayList<>();
        try {
            matchedKode = getMatchedKodeGejala(inputUser, dataGejala);
            System.out.println("Matched kode: " + matchedKode);
        } catch (Exception e) {
            e.printStackTrace();  // <-- ini penting
        }


        LinkedHashMap<Integer, ArrayList<String>> dataPenyakit = tabelPenyakitGejala();

        boolean fullMatch = false;
        int idFullMatch = -1;

        for (Map.Entry<Integer, ArrayList<String>> entry : dataPenyakit.entrySet()) {
            ArrayList<String> list = new ArrayList<>();
            for (String s : entry.getValue()) list.add(s.trim());
            Collections.sort(list);

            ArrayList<String> matchedCopy = new ArrayList<>();
            for (String s : matchedKode) matchedCopy.add(s.trim());
            Collections.sort(matchedCopy);

            System.out.println("list: " + list);
            System.out.println("matched: " + matchedCopy);
            System.out.println("Apakah sama? " + list.equals(matchedCopy));

            if (list.equals(matchedCopy)) {
                fullMatch = true;
                idFullMatch = entry.getKey();
                break;
            }
        }


        if (matchedKode.isEmpty()) {
            PenyakitAPI.gejalaUser = inputGejala;
            PenyakitAPI.feedback = "Gejala tidak cocok dengan penyakit manapun.";
            PenyakitAPI.diagnosa = "Tidak dapat disimpulkan";
            return;
        }

        if (fullMatch) {
            HashMap<String, String> mapKodeGejala = getKodeKeGejalaAsli();
            ArrayList<String> daftarGejalaUser = new ArrayList<>();
            for (String kode : matchedKode) {
                daftarGejalaUser.add(mapKodeGejala.getOrDefault(kode, kode));
            }
            String namaPenyakit = tabelPenyakit().getOrDefault(idFullMatch, "Tidak Diketahui");
            String penanganan = addPenanganan().getOrDefault(idFullMatch, "Belum ada penanganan");

            PenyakitAPI.gejalaUser = inputGejala;
            PenyakitAPI.feedback = "Anda tampaknya terkena " + namaPenyakit;
            PenyakitAPI.diagnosa = namaPenyakit;
            PenyakitAPI.penanganan = penanganan;
            return;
        }

        Map<String, Object> hasilTop3 = getTop3PenyakitBesertaGejalaTidakTerpakai(matchedKode);
        ArrayList<Integer> top3Id = (ArrayList<Integer>) hasilTop3.get("top3IdPenyakit");
        ArrayList<String> sisaKode = (ArrayList<String>) hasilTop3.get("kodeTidakTerpakai");

        if (top3Id.isEmpty()) {
            PenyakitAPI.feedback = "Gejala Anda tidak cocok dengan data penyakit manapun.";
            PenyakitAPI.diagnosa = "Tidak dapat disimpulkan";
            return;
        }

        HashMap<String, String> mapKodeKeGejala = getKodeKeGejalaAsli();
        StringBuilder sisaList = new StringBuilder();
        for (int i = 0; i < sisaKode.size(); i++) {
            sisaList.append(i + 1).append(". ").append(mapKodeKeGejala.getOrDefault(sisaKode.get(i), sisaKode.get(i))).append("\n");
        }

        StringBuilder kemungkinan = new StringBuilder();
        for (Integer id : top3Id) {
            kemungkinan.append("- ").append(tabelPenyakit().getOrDefault(id, "Tidak Diketahui")).append("\n");
        }

        PenyakitAPI.gejalaUser = inputGejala;
        PenyakitAPI.feedback = "Gejala Anda belum cocok seluruhnya.\nKemungkinan penyakit:\n" + kemungkinan + "\nSisa gejala yang perlu dikonfirmasi:\n" + sisaList + "\nSilakan pilih/konfirmasi gejala yang Anda alami.";
        PenyakitAPI.diagnosa = "Perlu konfirmasi lanjutan";
    }

    public boolean konfirmasiDanEvaluasiUlangGejala(String inputAngkaUser, ArrayList<String> sisaKode, ArrayList<String> matchedKode) {
        // Step 1: Bersihkan input angka dari tanda baca dan spasi
        inputAngkaUser = inputAngkaUser.replaceAll("[^0-9\\s,]", " ");
        String[] inputNumbers = inputAngkaUser.trim().split("[\\s,]+");

        // Step 2: Ubah ke kode gejala dari daftar sisa
        for (String s : inputNumbers) {
            try {
                int index = Integer.parseInt(s) - 1; // karena ditampilkan dari angka 1
                if (index >= 0 && index < sisaKode.size()) {
                    String kode = sisaKode.get(index).trim();
                    if (!matchedKode.contains(kode)) {
                        matchedKode.add(kode); // tambahkan ke matchedKode jika belum ada
                    }
                }
            } catch (NumberFormatException ignored) {
            }
        }

        // Step 3: Ambil ulang semua data penyakit
        LinkedHashMap<Integer, ArrayList<String>> dataPenyakit = tabelPenyakitGejala();

        // Step 4: Cek apakah matched kode sekarang sama persis dengan salah satu data penyakit
        for (Map.Entry<Integer, ArrayList<String>> entry : dataPenyakit.entrySet()) {
            ArrayList<String> listGejala = new ArrayList<>();
            for (String kode : entry.getValue()) listGejala.add(kode.trim());
            Collections.sort(listGejala);

            ArrayList<String> matchedCopy = new ArrayList<>();
            for (String kode : matchedKode) matchedCopy.add(kode.trim());
            Collections.sort(matchedCopy);

            if (listGejala.equals(matchedCopy)) {
                // Match ditemukan
                String namaPenyakit = tabelPenyakit().getOrDefault(entry.getKey(), "Tidak Diketahui");
                String penanganan = addPenanganan().getOrDefault(entry.getKey(), "Belum ada penanganan");

                PenyakitAPI.feedback = "Setelah konfirmasi, Anda kemungkinan besar mengalami " + namaPenyakit + ".";
                PenyakitAPI.diagnosa = namaPenyakit;
                PenyakitAPI.penanganan = penanganan;

                return true;
            }
        }

        // Jika tidak cocok penuh
        PenyakitAPI.feedback = "Setelah konfirmasi, gejala Anda masih belum cocok sepenuhnya.";
        PenyakitAPI.diagnosa = "Perlu pemeriksaan lanjutan.";
        return false;
    }

}
