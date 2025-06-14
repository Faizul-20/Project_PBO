package Testing;

import java.util.*;

public class ChatNLP {

    private static final Map<Set<String>, String> qaMap = new LinkedHashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initQA();

        System.out.println("Halo! Aku AI kecil. Tanyakan sesuatu (ketik 'keluar' untuk keluar)");

        while (true) {
            System.out.print("\nKamu: ");
            String input = scanner.nextLine().toLowerCase().trim();

            if (input.equals("keluar")) {
                System.out.println("AI: Sampai jumpa, semoga harimu menyenangkan!");
                break;
            }

            Set<String> tokens = tokenize(input);
            String response = searchAnswer(tokens);

            System.out.println("AI: " + response);
        }

        scanner.close();
    }

    // Tokenizer sederhana
    private static Set<String> tokenize(String input) {
        String[] words = input.split("\\s+");
        return new HashSet<>(Arrays.asList(words));
    }

    // Pencocokan token
    private static String searchAnswer(Set<String> tokens) {
        for (Map.Entry<Set<String>, String> entry : qaMap.entrySet()) {
            if (tokens.containsAll(entry.getKey())) {
                return entry.getValue();
            }
        }
        return "Maaf, aku belum tahu jawabannya.";
    }

    // Inisialisasi basis pengetahuan
    private static void initQA() {
        qaMap.put(Set.of("apa", "kabar"), "Saya baik, terima kasih! Kamu sendiri bagaimana?");
        qaMap.put(Set.of("kamu", "siapa"), "Aku asisten virtual yang siap bantu kamu kapan pun.");
        qaMap.put(Set.of("sedang", "apa"), "Lagi bantuin kamu nih! Ada yang bisa kubantu?");
        qaMap.put(Set.of("merasa", "sedih"), "Tidak apa-apa merasa sedih. Aku di sini kalau kamu mau cerita.");
        qaMap.put(Set.of("cara", "hilangkan", "stres"), "Coba tarik napas dalam, dengarkan musik, atau istirahat.");
        qaMap.put(Set.of("kamu", "dari", "mana"), "Aku tinggal di server, tapi dekat di hati kamu.");
        qaMap.put(Set.of("apa", "itu", "ai"), "AI adalah kecerdasan buatan yang meniru cara pikir manusia.");
        qaMap.put(Set.of("hari", "ini", "tanggal"), "Hari ini tanggal 14 Juni 2025.");
        qaMap.put(Set.of("jam", "berapa"), "Sekarang sekitar jam 10 malam. Jangan begadang ya!");
        qaMap.put(Set.of("bingung", "jurusan"), "Coba pikirkan apa yang kamu suka dan minati jangka panjang.");
        qaMap.put(Set.of("suka", "nonton"), "Aku suka bantu nyari film bagus, kamu suka genre apa?");
        qaMap.put(Set.of("rekomendasi", "film"), "Coba tonton *Interstellar*, *Your Name*, atau *The Social Network*.");
        // Tambahkan pertanyaan dan jawaban lainnya jika mau
    }
}
