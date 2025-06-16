package chatBotEngine;

import API.PenyakitAPI;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.*;

public class CakapEmuyService {
    private final String dbUrl;
    private final List<QAEntry> qaList = new ArrayList<>();

    public static class QAEntry {
        private final Set<String> keywords;
        private final String answer;
        private final int keywordCount;

        public QAEntry(String question, String answer) {
            this.keywords = new HashSet<>();
            this.answer = answer;

            if (question != null && !question.isEmpty()) {
                String[] parts = question.split(",");
                for (String part : parts) {
                    String cleanedKeyword = part.trim().toLowerCase();
                    if (!cleanedKeyword.isEmpty()) {
                        keywords.add(cleanedKeyword);
                    }
                }
            }
            this.keywordCount = keywords.size();
        }

        public Set<String> getKeywords() {
            return Collections.unmodifiableSet(keywords);
        }

        public String getAnswer() {
            return answer;
        }

        public int getKeywordCount() {
            return keywordCount;
        }
    }

    // Konstruktor default
    public CakapEmuyService() throws SQLException {
        this(getDatabasePath());
    }

    // Konstruktor dengan custom path
    public CakapEmuyService(String dbUrl) throws SQLException {
        this.dbUrl = "jdbc:sqlite:" + dbUrl;
        initializeChatBot();
    }

    // Ambil path absolut ke database
    private static String getDatabasePath() {
        try {
            File jarDir = new File(CakapEmuyService.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            File projectRoot = jarDir.getParentFile().getParentFile(); // naik dari /out/
            File dbFile = new File(projectRoot, "Database/sapaEmuy/Emuycakap.db");
            return dbFile.getAbsolutePath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return "Database/sapaEmuy/Emuycakap.db"; // fallback (IDE)
        }
    }

    private void initializeChatBot() throws SQLException {
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
            loadQAData(conn);
        }
    }

    private void loadQAData(Connection conn) throws SQLException {
        String sql = "SELECT question, answer FROM Emuy_cakap";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                qaList.add(new QAEntry(
                        rs.getString("question"),
                        rs.getString("answer")
                ));
            }
        }
    }

    public String processInput(String userInput) {
        if (userInput == null || userInput.trim().isEmpty()) {
            return "Coba ketik sesuatu dulu ya!";
        }

        Set<String> tokens = tokenizeInput(userInput);
        if (tokens.isEmpty()) {
            return "Aku belum paham maksudmu";
        }

        return findBestResponse(tokens);
    }

    private Set<String> tokenizeInput(String input) {
        String cleanedInput = input.toLowerCase().replaceAll("[^a-z0-9\\s]", "");
        String[] words = cleanedInput.split("\\s+");
        return new HashSet<>(Arrays.asList(words));
    }

    private String findBestResponse(Set<String> tokens) {
        QAEntry bestMatch = null;
        int highestMatchCount = 0;

        for (QAEntry entry : qaList) {
            if (entry.getKeywords().isEmpty()) continue;

            if (tokens.containsAll(entry.getKeywords())) {
                if (bestMatch == null || entry.getKeywordCount() > highestMatchCount) {
                    bestMatch = entry;
                    highestMatchCount = entry.getKeywordCount();
                }
            }
        }

        return (bestMatch != null) ? bestMatch.getAnswer() : PenyakitAPI.diagnosa;
    }

    public List<QAEntry> getAllQAEntries() {
        return Collections.unmodifiableList(qaList);
    }
}
