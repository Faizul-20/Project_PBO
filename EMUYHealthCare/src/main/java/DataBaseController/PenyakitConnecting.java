package DataBaseController;

import API.PenyakitAPI;
import DataBaseController.ConnectionData;
import chatBotEngine.Tokenization;


import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


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

        // Jika ini adalah respons konfirmasi, kembalikan list kosong
        if (inputUser.contains("konfirmasi")) {
            return matchedKode;
        }

        for (String kode : mapGejala.keySet()) {
            ArrayList<String> gejalaWords = mapGejala.get(kode);

            for (String kata : gejalaWords) {
                if (inputUser.contains(kata)) {
                    matchedKode.add(kode);
                    break;
                }
            }
        }

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

    public LinkedHashMap<Integer, String> addPenanganan(){
        LinkedHashMap<Integer, String> mapPenanganan = new LinkedHashMap<>();

        try {
            Connection connection = DriverManager.getConnection(getPENYAKIT_DATA());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY_selectPenanganan);

            while (resultSet.next()){
                Integer Id_Penyakit = resultSet.getInt("Id_penyakit");
                String penanganan = resultSet.getString("penanganan");
                mapPenanganan.put(Id_Penyakit, penanganan);
            }

            resultSet.close();
            statement.close();
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return mapPenanganan;


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
        main.java.API.TestingDb.DataConnecting dataConnecting = new main.java.API.TestingDb.DataConnecting();
        dataConnecting.ConnectToDatabase(dataConnecting.getPENYAKIT_DATA());

        if (PenyakitAPI.diagnosa != null &&
                PenyakitAPI.diagnosa.equals("Perlu konfirmasi lanjutan") &&
                PenyakitAPI.kodeGejalaKonfirmasi != null) {

            ArrayList<String> selectedKodes = processNumberInput(userInput, PenyakitAPI.kodeGejalaKonfirmasi);

            if (selectedKodes != null && !selectedKodes.isEmpty()) {
                // Gabungkan dengan matchedKode sebelumnya yang disimpan di PenyakitAPI
                ArrayList<String> matchedKode = new ArrayList<>(PenyakitAPI.matchedKodeAwal);
                matchedKode.addAll(selectedKodes);

                // Debugging gejala gabungan
                System.out.println("\n===== DEBUG: GEJALA GABUNGAN =====");
                System.out.println("Gejala awal: " + PenyakitAPI.matchedKodeAwal);
                System.out.println("Gejala terkonfirmasi: " + selectedKodes);
                System.out.println("Hasil gabungan: " + matchedKode);

                // Proses ulang dengan gejala yang telah dikonfirmasi
                processConfirmedSymptoms(matchedKode, userInput);
                return;
            }
        }

        // Tokenisasi input user jadi kode gejala
        String inputGejala = userInput.toLowerCase();
        HashSet<String> inputUserSet = Tokenization.Gejala(inputGejala);

        // Debugging inputan user yg diclean
        System.out.println("\n=====Debugging inputan user yg diclean=====");
        System.out.println(inputUserSet);
        System.out.println("===========================================");

        PenyakitConnecting penyakitConnecting = new PenyakitConnecting();
        LinkedHashMap<String, ArrayList<String>> dataGejala = penyakitConnecting.addMapGejala();

        // Ambil kode gejala hasil pencocokan
        ArrayList<String> matchedKode = penyakitConnecting.getMatchedKodeGejala(inputUserSet, dataGejala);

        // Simpan matchedKode awal untuk kebutuhan konfirmasi
        PenyakitAPI.matchedKodeAwal = new ArrayList<>(matchedKode);

        // Debugging matchedKode dari input user
        System.out.println("\n===== DEBUGGING: matchedKode dari input user =====");
        System.out.println("matchedKode: " + matchedKode);

        // Ambil seluruh tabel penyakit_gejala
        LinkedHashMap<Integer, ArrayList<String>> semuaPenyakitGejala = penyakitConnecting.tabelPenyakitGejala();

        // Tampilkan baris penyakit yang mengandung matchedKode
        System.out.println("\n===== DEBUGGING: Penyakit yang mengandung matchedKode =====");
        for (Map.Entry<Integer, ArrayList<String>> entry : semuaPenyakitGejala.entrySet()) {
            boolean mengandung = false;
            for (String kode : matchedKode) {
                if (entry.getValue().contains(kode)) {
                    mengandung = true;
                    break;
                }
            }
            if (mengandung) {
                System.out.println("ID Penyakit: " + entry.getKey() + " -> " + entry.getValue());
            }
        }
        System.out.println("===================================================");

        // Cek FullMatch dengan semua penyakit
        boolean FullMatch = false;
        int IdFullMatch = -1;
        for (Map.Entry<Integer, ArrayList<String>> entry : semuaPenyakitGejala.entrySet()) {
            if (matchedKode.containsAll(entry.getValue())) {
                FullMatch = true;
                IdFullMatch = entry.getKey();
                break;
            }
        }

        // Jika inputan tidak cocok dengan gejala apapun
        if (matchedKode.isEmpty()){
            PenyakitAPI.gejalaUser = inputGejala;
            PenyakitAPI.feedback = "Gejala tidak cocok dengan penyakit manapun / Gejala diluar data yang ada";
            PenyakitAPI.diagnosa = "Tidak bisa disimpulkan";
            return;
        }

        // Jika semua gejala cocok pada 1 penyakit
        if (FullMatch) {
            handleFullMatch(IdFullMatch, matchedKode, inputGejala);
            return;
        }

        // Jika tidak full match, cari penyakit dengan gejala terbanyak
        List<Map.Entry<Integer, Integer>> topMatches = findTop3PartialMatches(matchedKode);
        if (!topMatches.isEmpty()) {
            System.out.println("\n===== DEBUG: TOP MATCHES =====");
            for (Map.Entry<Integer, Integer> match : topMatches) {
                System.out.println("Penyakit: ID " + match.getKey() + " dengan " + match.getValue() + " gejala cocok");
            }
            handleTop3PartialMatches(topMatches, matchedKode, inputGejala);
        } else {
            handleNoMatchFound(matchedKode);
        }
    }

    // Method untuk menangani kasus full match
    private void handleFullMatch(int IdFullMatch, ArrayList<String> matchedKode, String inputGejala) {
        HashMap<String, String> kodeKeGejalaAsli = getKodeKeGejalaAsli();
        LinkedHashMap<Integer, String> mapPenyakit = tabelPenyakit();
        LinkedHashMap<Integer, String> mapPenanganan = addPenanganan();

        String namaPenyakit = mapPenyakit.getOrDefault(IdFullMatch, "Tidak Diketahui");
        String penanganan = mapPenanganan.getOrDefault(IdFullMatch, "Belum ada penanganan");

        // Format gejala untuk ditampilkan
        StringBuilder gejalaStr = new StringBuilder();
        for (String kode : matchedKode) {
            gejalaStr.append("\n- ").append(kodeKeGejalaAsli.getOrDefault(kode, kode));
        }

        PenyakitAPI.gejalaUser = inputGejala;
        PenyakitAPI.feedback = "Diagnosis: " + namaPenyakit;
        PenyakitAPI.diagnosa = namaPenyakit;
        PenyakitAPI.penanganan = penanganan;
        PenyakitAPI.kodeGejalaKonfirmasi = null;
    }

    // Method untuk menangani kasus partial match
    private void handlePartialMatch(int diseaseId, List<String> symptoms, String userInput) {
        // 1. Dapatkan gejala penyakit dari database
        ArrayList<String> diseaseSymptoms = tabelPenyakitGejala().get(diseaseId);
        if (diseaseSymptoms == null) {
            PenyakitAPI.feedback = "Data penyakit tidak valid";
            PenyakitAPI.diagnosa = "Error";
            return;
        }

        // 2. Cari gejala yang belum dikonfirmasi
        ArrayList<String> missingSymptoms = new ArrayList<>(diseaseSymptoms);
        missingSymptoms.removeAll(symptoms);

        // 3. Siapkan pesan untuk user
        StringBuilder message = new StringBuilder();
        message.append("Kemungkinan penyakit: ")
                .append(tabelPenyakit().getOrDefault(diseaseId, "Penyakit tidak diketahui"))
                .append("\n\nGejala tambahan yang perlu dikonfirmasi:\n");

        HashMap<String, String> symptomMap = getKodeKeGejalaAsli();
        for (int i = 0; i < missingSymptoms.size(); i++) {
            message.append(i+1).append(". ")
                    .append(symptomMap.getOrDefault(missingSymptoms.get(i), "Gejala tidak diketahui"))
                    .append("\n");
        }

        // 4. Update PenyakitAPI
        PenyakitAPI.kodeGejalaKonfirmasi = new ArrayList<>(missingSymptoms); // Pastikan ArrayList
        PenyakitAPI.feedback = message.toString();
        PenyakitAPI.diagnosa = "Perlu konfirmasi lanjutan";
        PenyakitAPI.gejalaUser = userInput;
    }


    private ArrayList<String> processNumberInput(String userInput, ArrayList<String> kodeGejalaKonfirmasi) {
        ArrayList<String> selectedKodes = new ArrayList<>();

        // Jika ada mapping nomor ke kode gejala, gunakan itu
        if (PenyakitAPI.gejalaNumberToCode != null && !PenyakitAPI.gejalaNumberToCode.isEmpty()) {
            String[] choices = userInput.split("[,\\s]+");
            for (String choiceStr : choices) {
                try {
                    int choice = Integer.parseInt(choiceStr.trim());
                    if (PenyakitAPI.gejalaNumberToCode.containsKey(choice)) {
                        String selectedKode = PenyakitAPI.gejalaNumberToCode.get(choice);
                        selectedKodes.add(selectedKode);
                    }
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        // Fallback ke cara lama jika mapping tidak ada
        else {
            String[] choices = userInput.split("[,\\s]+");
            for (String choiceStr : choices) {
                try {
                    int choice = Integer.parseInt(choiceStr.trim());
                    if (choice > 0 && choice <= kodeGejalaKonfirmasi.size()) {
                        String selectedKode = kodeGejalaKonfirmasi.get(choice - 1);
                        selectedKodes.add(selectedKode);
                    }
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }

        return selectedKodes.isEmpty() ? null : selectedKodes;
    }

    private void processConfirmedSymptoms(ArrayList<String> matchedKode, String userInput) {
        System.out.println("\n===== PROSES KONFIRMASI GEJALA =====");
        System.out.println("Gejala awal: " + PenyakitAPI.matchedKodeAwal);
        System.out.println("Input konfirmasi: " + userInput);

        // 1. Proses input konfirmasi user
        ArrayList<String> selectedKodes = processNumberInput(userInput, PenyakitAPI.kodeGejalaKonfirmasi);
        if (selectedKodes == null || selectedKodes.isEmpty()) {
            PenyakitAPI.feedback = "Input tidak valid. Harap masukkan nomor gejala yang sesuai.";
            return;
        }

        // 2. Gabungkan gejala awal dan terkonfirmasi (tanpa duplikasi)
        Set<String> allSymptoms = new LinkedHashSet<>(PenyakitAPI.matchedKodeAwal);
        allSymptoms.addAll(selectedKodes);
        ArrayList<String> combinedSymptoms = new ArrayList<>(allSymptoms);

        System.out.println("Gabungan semua gejala: " + combinedSymptoms);

        // 3. Cari penyakit yang match TEPAT dengan semua gejala
        Map.Entry<Integer, ArrayList<String>> matchedDisease = findExactMatch(combinedSymptoms);
        if (matchedDisease != null) {
            showDiagnosisResult(matchedDisease.getKey(), combinedSymptoms);
            return;
        }

        // 4. Jika tidak ada match tepat, cari penyakit dengan gejala terbanyak
        List<Map.Entry<Integer, Integer>> topMatches = findTop3PartialMatches(combinedSymptoms);
        if (!topMatches.isEmpty()) {
            handleTop3PartialMatches(topMatches, combinedSymptoms, userInput);
        } else {
            handleNoMatchFound(combinedSymptoms);
        }
    }

    private void handleNoMatchFound(List<String> symptoms) {
        StringBuilder symptomList = new StringBuilder();
        HashMap<String, String> symptomMap = getKodeKeGejalaAsli();
        for (String code : symptoms) {
            symptomList.append("\n- ").append(symptomMap.getOrDefault(code, code));
        }

        PenyakitAPI.gejalaUser = symptomList.toString();
        PenyakitAPI.feedback = "Tidak ditemukan penyakit yang cocok dengan gejala:\n" + symptomList;
        PenyakitAPI.diagnosa = "Tidak dapat disimpulkan";
        PenyakitAPI.kodeGejalaKonfirmasi = null;
    }

    // Helper method to find exact match
    private Map.Entry<Integer, ArrayList<String>> findExactMatch(List<String> symptoms) {
        for (Map.Entry<Integer, ArrayList<String>> disease : tabelPenyakitGejala().entrySet()) {
            if (new HashSet<>(symptoms).containsAll(disease.getValue())) {
                return disease;
            }
        }
        return null;
    }

    private void showDiagnosisResult(int diseaseId, List<String> allSymptoms) {
        String diseaseName = tabelPenyakit().getOrDefault(diseaseId, "Tidak Diketahui");
        String treatment = addPenanganan().getOrDefault(diseaseId, "Belum ada penanganan");

        // Format detail gejala
        StringBuilder symptomDetails = new StringBuilder();
        HashMap<String, String> symptomMap = getKodeKeGejalaAsli();
        for (String code : allSymptoms) {
            symptomDetails.append("\n- ").append(symptomMap.getOrDefault(code, code));
        }

        PenyakitAPI.gejalaUser = symptomDetails.toString();
        PenyakitAPI.feedback = "Diagnosis: " + diseaseName;
        PenyakitAPI.diagnosa = diseaseName;
        PenyakitAPI.penanganan = treatment;
        PenyakitAPI.kodeGejalaKonfirmasi = null;

        System.out.println("Diagnosis final: " + diseaseName);
    }

    // Helper method to find best partial match
    // Ubah parameter menjadi List<String> untuk fleksibilitas
    private List<Map.Entry<Integer, Integer>> findTop3PartialMatches(List<String> symptoms) {
        List<Map.Entry<Integer, Integer>> matches = new ArrayList<>();

        for (Map.Entry<Integer, ArrayList<String>> disease : tabelPenyakitGejala().entrySet()) {
            int matchCount = 0;
            for (String symptom : symptoms) {
                if (disease.getValue().contains(symptom)) {
                    matchCount++;
                }
            }

            if (matchCount > 0) {
                matches.add(new AbstractMap.SimpleEntry<>(disease.getKey(), matchCount));
            }
        }

        // Urutkan berdasarkan match count tertinggi
        matches.sort((a, b) -> b.getValue() - a.getValue());

        // Ambil maksimal 3 teratas
        return matches.subList(0, Math.min(3, matches.size()));
    }

    // Method baru untuk menangani multiple penyakit
    private void handleTop3PartialMatches(List<Map.Entry<Integer, Integer>> topMatches,
                                          List<String> currentSymptoms,
                                          String userInput) {
        // 1. Dapatkan info penyakit
        LinkedHashMap<Integer, String> penyakitMap = tabelPenyakit();
        LinkedHashMap<Integer, ArrayList<String>> gejalaPenyakitMap = tabelPenyakitGejala();

        // 2. Siapkan pesan untuk user
        StringBuilder message = new StringBuilder();
        message.append("Kemungkinan penyakit:\n");

        // 3. Kumpulkan semua gejala yang perlu dikonfirmasi dari top 3 penyakit
        Set<String> allMissingSymptoms = new LinkedHashSet<>();
        Map<Integer, List<String>> penyakitToMissingSymptoms = new HashMap<>();

        for (Map.Entry<Integer, Integer> match : topMatches) {
            Integer penyakitId = match.getKey();
            List<String> penyakitSymptoms = gejalaPenyakitMap.get(penyakitId);

            // Cari gejala yang belum dikonfirmasi
            List<String> missingSymptoms = new ArrayList<>(penyakitSymptoms);
            missingSymptoms.removeAll(currentSymptoms);

            penyakitToMissingSymptoms.put(penyakitId, missingSymptoms);
            allMissingSymptoms.addAll(missingSymptoms);

            // Tambahkan ke pesan
            message.append("- ").append(penyakitMap.get(penyakitId))
                    .append(" (").append(match.getValue())
                    .append("/").append(penyakitSymptoms.size())
                    .append(" gejala cocok)\n");
        }

        // 4. Format gejala yang perlu dikonfirmasi
        message.append("\nGejala tambahan yang perlu dikonfirmasi:\n");

        HashMap<String, String> symptomMap = getKodeKeGejalaAsli();
        List<String> allMissingSymptomsList = new ArrayList<>(allMissingSymptoms);

        int counter = 1;
        for (String kode : allMissingSymptomsList) {
            message.append(counter).append(". ")
                    .append(symptomMap.getOrDefault(kode, "Gejala tidak diketahui"))
                    .append("\n");
            counter++;
        }

        // 5. Update PenyakitAPI
        PenyakitAPI.kodeGejalaKonfirmasi = new ArrayList<>(allMissingSymptomsList);
        PenyakitAPI.feedback = message.toString();
        PenyakitAPI.diagnosa = "Perlu konfirmasi lanjutan";
        PenyakitAPI.gejalaUser = userInput;

        // Simpan mapping antara nomor gejala dengan kode gejala
        PenyakitAPI.gejalaNumberToCode = new HashMap<>();
        for (int i = 0; i < allMissingSymptomsList.size(); i++) {
            PenyakitAPI.gejalaNumberToCode.put(i+1, allMissingSymptomsList.get(i));
        }

        // Simpan info top 3 penyakit untuk referensi
        PenyakitAPI.top3Penyakit = topMatches.stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private void logGejalaDetails(ArrayList<String> kodeGejala) {
        HashMap<String, String> kodeToGejala = getKodeKeGejalaAsli();
        System.out.println("Detail gejala:");
        for (String kode : kodeGejala) {
            System.out.println("- " + kode + ": " + kodeToGejala.getOrDefault(kode, "Tidak diketahui"));
        }
    }

    private void checkDiagnosisAfterConfirmation(ArrayList<String> matchedKode, String inputGejala) {
        // 1. Cek exact match dulu
        Map.Entry<Integer, ArrayList<String>> fullMatch = findExactMatch(matchedKode);
        if (fullMatch != null) {
            handleFullMatchCase(fullMatch.getKey(), matchedKode);
            return;
        }

        // 2. Cari penyakit dengan match terbanyak
        List<Map.Entry<Integer, Integer>> topMatches = findTop3PartialMatches(matchedKode);
        if (!topMatches.isEmpty()) {
            handleTop3PartialMatches(topMatches, matchedKode, inputGejala);
        } else {
            handleNoMatchFound(matchedKode);
        }
    }

    private Map.Entry<Integer, ArrayList<String>> findDiseaseMatch(ArrayList<String> matchedKode) {
        for (Map.Entry<Integer, ArrayList<String>> entry : tabelPenyakitGejala().entrySet()) {
            if (new HashSet<>(matchedKode).containsAll(entry.getValue())) {
                return entry;
            }
        }
        return null;
    }

    private void handleFullMatchCase(int diseaseId, List<String> allSymptoms) {
        String diseaseName = tabelPenyakit().getOrDefault(diseaseId, "Tidak Diketahui");
        String treatment = addPenanganan().getOrDefault(diseaseId, "Belum ada penanganan");

        StringBuilder symptomDetails = new StringBuilder();
        HashMap<String, String> symptomMap = getKodeKeGejalaAsli();
        for (String code : allSymptoms) {
            symptomDetails.append("\n- ").append(symptomMap.getOrDefault(code, code));
        }

        PenyakitAPI.gejalaUser = symptomDetails.toString();
        PenyakitAPI.feedback = "Diagnosis: " + diseaseName;
        PenyakitAPI.diagnosa = diseaseName;
        PenyakitAPI.penanganan = treatment;
        PenyakitAPI.kodeGejalaKonfirmasi = null;
    }

    private void handleNoDiagnosisCase(List<String> symptoms) {
        StringBuilder symptomList = new StringBuilder();
        HashMap<String, String> symptomMap = getKodeKeGejalaAsli();
        for (String code : symptoms) {
            symptomList.append("\n- ").append(symptomMap.getOrDefault(code, code));
        }

        PenyakitAPI.gejalaUser = symptomList.toString();
        PenyakitAPI.feedback = "Tidak ditemukan penyakit yang cocok dengan gejala:\n" + symptomList;
        PenyakitAPI.diagnosa = "Tidak dapat disimpulkan";
        PenyakitAPI.kodeGejalaKonfirmasi = null;
    }

    private void prepareNextConfirmation(ArrayList<String> matchedKode) {
        Map<String, Object> top3Results = getTop3PenyakitBesertaGejalaTidakTerpakai(matchedKode);
        ArrayList<Integer> top3Diseases = (ArrayList<Integer>) top3Results.get("top3IdPenyakit");
        ArrayList<String> remainingSymptoms = (ArrayList<String>) top3Results.get("kodeTidakTerpakai");

        if (top3Diseases.isEmpty()) {
            PenyakitAPI.feedback = "Gejala Anda tidak cocok dengan data penyakit manapun.";
            PenyakitAPI.diagnosa = "Tidak dapat disimpulkan";
            return;
        }

        PenyakitAPI.kodeGejalaKonfirmasi = remainingSymptoms;
        PenyakitAPI.feedback = buildConfirmationMessage(
                getPenyakitListAsString(top3Diseases),
                getGejalaListAsString(remainingSymptoms, true)
        );
        PenyakitAPI.diagnosa = "Perlu konfirmasi lanjutan";

        System.out.println("Penyakit potensial: " + top3Diseases);
        System.out.println("Gejala yang perlu dikonfirmasi: " + remainingSymptoms);
    }

    // Method pembantu untuk format daftar penyakit
    private String getPenyakitListAsString(ArrayList<Integer> top3Id) {
        LinkedHashMap<Integer, String> mapPenyakit = tabelPenyakit();
        StringBuilder sb = new StringBuilder();
        for (Integer id : top3Id) {
            sb.append("- ").append(mapPenyakit.get(id)).append("\n");
        }
        return sb.toString();
    }

    private String getGejalaListAsString(ArrayList<String> kodeGejala) {
        return getGejalaListAsString(kodeGejala, false); // Memanggil method utama dengan withNumbers=false
    }

    // Method pembantu untuk format daftar gejala
    private String getGejalaListAsString(ArrayList<String> kodeGejala, boolean withNumbers) {
        HashMap<String, String> kodeKeGejala = getKodeKeGejalaAsli();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < kodeGejala.size(); i++) {
            if (withNumbers) {
                sb.append(i + 1).append(". ");
            } else {
                sb.append("- ");
            }
            sb.append(kodeKeGejala.getOrDefault(kodeGejala.get(i), kodeGejala.get(i))).append("\n");
        }
        return sb.toString();
    }

    // Ubah method buildConfirmationMessage untuk menerima String biasa
    private String buildConfirmationMessage(String daftarPenyakit, String daftarGejala) {
        return "Gejala Anda belum cocok seluruhnya.\n" +
                "Kemungkinan penyakit:\n" + daftarPenyakit +
                "\nSisa gejala yang perlu dikonfirmasi:\n" + daftarGejala +
                "\nSilakan pilih gejala dengan memasukkan NOMOR saja (contoh: 1 atau 1,2,3).";
    }

    private void checkDiagnosis(ArrayList<String> matchedKode) {
        // Cek apakah matchedKode cocok 100% dengan suatu penyakit
        boolean isFullMatch = false;
        int idPenyakitMatch = -1;

        LinkedHashMap<Integer, ArrayList<String>> tabelPenyakitGejala = tabelPenyakitGejala();
        for (Map.Entry<Integer, ArrayList<String>> entry : tabelPenyakitGejala.entrySet()) {
            if (matchedKode.containsAll(entry.getValue())) {
                isFullMatch = true;
                idPenyakitMatch = entry.getKey();
                break;
            }
        }

        // Jika cocok, isi feedback dengan diagnosis
        if (isFullMatch) {
            LinkedHashMap<Integer, String> mapPenyakit = tabelPenyakit();
            String namaPenyakit = mapPenyakit.get(idPenyakitMatch);

            PenyakitAPI.feedback = "Diagnosis: " + namaPenyakit;
            PenyakitAPI.diagnosa = namaPenyakit;
            PenyakitAPI.kodeGejalaKonfirmasi = null; // Reset konfirmasi
        }
        // Jika tidak cocok, beri pesan "tidak ditemukan"
        else {
            PenyakitAPI.feedback = "Tidak ditemukan penyakit yang cocok dengan gejala yang dimasukkan.";
            PenyakitAPI.diagnosa = "Tidak dapat disimpulkan";
            PenyakitAPI.kodeGejalaKonfirmasi = null;
        }
    }















}