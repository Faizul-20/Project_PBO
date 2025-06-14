package chatBotEngine.TestingCakapEmuy;

import java.sql.*;
import java.util.*;

public class cakapEmuyPrototype {
    // URL database SQLite
    private static final String DB_URL = "jdbc:sqlite:EMUYHealthCare/Database/sapaEmuy/Emuycakap.db";

    // Menyimpan semua pertanyaan dan jawaban dari database
    private static List<QAEntry> qaList = new ArrayList<>();

    // Untuk membaca input dari pengguna
    private static Scanner scanner = new Scanner(System.in);

    // Menyimpan hasil tokenisasi input pengguna
    private static Set<String> userTokens = new HashSet<>();

    // Kelas untuk menyimpan setiap pertanyaan dan jawaban
    static class QAEntry {
        private final Set<String> keywords;  // Kata kunci dari pertanyaan
        private final String answer;         // Jawaban yang sesuai
        private final int keywordCount;      // Jumlah kata kunci

        public QAEntry(String question, String answer) {
            this.keywords = new HashSet<>();
            this.answer = answer;

            // Memproses pertanyaan menjadi kata kunci
            if (question != null && !question.isEmpty()) {
                // Memisahkan kata kunci berdasarkan koma
                String[] parts = question.split(",");

                for (String part : parts) {
                    // Membersihkan dan mengubah ke huruf kecil
                    String cleanedKeyword = part.trim().toLowerCase();

                    if (!cleanedKeyword.isEmpty()) {
                        keywords.add(cleanedKeyword);
                    }
                }
            }
            this.keywordCount = keywords.size();
        }

        // Getter untuk mengakses kata kunci
        public Set<String> getKeywords() {
            return keywords;
        }

        // Getter untuk mengakses jawaban
        public String getAnswer() {
            return answer;
        }

        // Getter untuk jumlah kata kunci
        public int getKeywordCount() {
            return keywordCount;
        }
    }

    public static void main(String[] args) {
        // Langkah 1: Inisialisasi chatbot
        initializeChatBot();

        // Langkah 2: Menjalankan sesi percakapan
        runChatSession();

        // Langkah 3: Membersihkan sumber daya
        closeResources();
    }

    // Inisialisasi chatbot dan koneksi database
    private static void initializeChatBot() {
        try {
            // Membuat koneksi ke database
            Connection conn = DriverManager.getConnection(DB_URL);

            // Memuat data pertanyaan dan jawaban
            loadQAData(conn);

            // Menutup koneksi setelah selesai
            conn.close();

            System.out.println("Halo, aku Emuy! 😊");
            System.out.println("Ketik sesuatu untuk mulai ngobrol (ketik 'keluar' untuk keluar):");
        } catch (SQLException e) {
            System.out.println("Error koneksi database: " + e.getMessage());
            System.exit(1);
        }
    }

    // Memuat data dari database ke dalam memori
    private static void loadQAData(Connection conn) throws SQLException {
        String sql = "SELECT question, answer FROM Emuy_cakap";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Memproses setiap baris hasil query
            while (rs.next()) {
                String question = rs.getString("question");
                String answer = rs.getString("answer");

                // Menambahkan ke daftar QA
                qaList.add(new QAEntry(question, answer));
            }
        }
    }

    // Menjalankan sesi percakapan utama
    private static void runChatSession() {
        boolean isChatting = true;

        while (isChatting) {
            // Membaca input pengguna
            String userInput = getUserInput();

            // Cek jika pengguna ingin keluar
            if (shouldExit(userInput)) {
                isChatting = false;
                continue;
            }

            // Cek jika input kosong
            if (isInputEmpty(userInput)) {
                continue;
            }

            // Langkah penting: Tokenisasi input dan simpan di HashSet
            tokenizeInput(userInput);

            // Cek jika token kosong
            if (userTokens.isEmpty()) {
                printResponse("Aku belum paham maksudmu 😅");
                continue;
            }

            // Mencari jawaban terbaik berdasarkan token
            String response = findBestResponse();
            printResponse(response);
        }
    }

    // Membaca input dari pengguna
    private static String getUserInput() {
        System.out.print("> ");
        return scanner.nextLine().trim();
    }

    // Cek jika pengguna ingin keluar
    private static boolean shouldExit(String input) {
        return input.equalsIgnoreCase("keluar");
    }

    // Cek jika input kosong
    private static boolean isInputEmpty(String input) {
        if (input.isEmpty()) {
            printResponse("Coba ketik sesuatu dulu ya!");
            return true;
        }
        return false;
    }

    // Tokenisasi input dan simpan di HashSet
    private static void tokenizeInput(String input) {
        // Bersihkan input: ubah ke huruf kecil dan hapus karakter khusus
        String cleanedInput = input.toLowerCase().replaceAll("[^a-z0-9\\s]", "");

        // Memisahkan input menjadi kata-kata
        String[] words = cleanedInput.split("\\s+");

        // Reset HashSet sebelum menyimpan token baru
        userTokens.clear();

        // Menambahkan semua kata ke HashSet
        Collections.addAll(userTokens, words);

        // [DEBUG] Tampilkan token jika diperlukan
        // System.out.println("Token: " + userTokens);
    }

    // Mencari jawaban terbaik berdasarkan token
    private static String findBestResponse() {
        QAEntry bestMatch = null;
        int highestMatchCount = 0;

        // Cari di semua entri QA
        for (QAEntry entry : qaList) {
            // Lewati entri tanpa kata kunci
            if (entry.getKeywords().isEmpty()) continue;

            // Cek jika semua kata kunci ada di input pengguna
            if (userTokens.containsAll(entry.getKeywords())) {
                // Prioritas untuk yang memiliki lebih banyak kata kunci
                if (bestMatch == null || entry.getKeywordCount() > highestMatchCount) {
                    bestMatch = entry;
                    highestMatchCount = entry.getKeywordCount();
                }
            }
        }

        // Kembalikan jawaban atau pesan default
        return (bestMatch != null) ? bestMatch.getAnswer() : "Maaf, aku belum mengerti itu 🥲";
    }

    // Menampilkan respons chatbot
    private static void printResponse(String message) {
        System.out.println("Emuy: " + message);
    }

    // Membersihkan sumber daya
    private static void closeResources() {
        scanner.close();
        System.out.println("\nSampai jumpa! 👋");
    }
}