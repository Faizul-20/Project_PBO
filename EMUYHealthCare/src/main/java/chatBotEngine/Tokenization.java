package chatBotEngine;

import API.PenyakitAPI;

import java.util.*;
import java.lang.String;
import java.util.regex.Pattern;

public class Tokenization {

    public static Set<String> gejala = new HashSet<>();

    public static void Gejala(String inputGejala) {
        String[] stopWords = {
                "saya", "aku", "kami", "kita", "anda", "gue", "nih", "ya", "lah", "deh", "dong",
                "cuma", "aja", "kok", "lagi", "kayaknya", "kayak", "seperti", "macam", "mirip",
                "yang", "itu", "ini", "tersebut", "terasa", "rasanya", "begitu", "banget", "sekali",
                "sedikit", "agak", "lumayan", "sangat", "sudah", "belum", "baru", "akan", "lagi",
                "mau", "tidak", "nggak", "ga", "tak", "tidaklah", "bukan", "dan", "atau", "dengan",
                "karena", "jadi", "lalu", "kemudian", "jika", "apabila", "padahal", "namun", "tetapi",
                "mungkin", "bisa", "dapat", "kayanya", "kurasa", "kupikir", "menurutku", "terkadang",
                "sering", "jarang", "setiap", "selalu", "hampir", "sekitar", "pada", "di", "ke", "dari",
                "untuk", "oleh", "selama", "hingga", "bahwa", "adalah", "ialah",
                "yaitu", "yakni", "apakah", "kenapa", "mengapa", "dimana", "bagaimana", "kapan",
                "dalam", "tentang", "mengenai", "lebih", "kurang", "ada", "tidak ada", "pernah",
                "belum pernah", "sudah pernah", "terlalu", "cukup", "gitu", "loh", "hmm", "eh", "uh",
                "uhm", "hanya", "malah", "bahkan", "sebenarnya", "seharusnya", "mustinya",
                "mending", "lebih baik", "aneh", "biasanya", "umumnya",
                "normalnya", "awalnya", "mulanya", "tiba-tiba", "seketika", "langsung", "perlahan",
                "terus", "kemarin", "tadi", "barusan", "besok", "nanti", "setelah", "sebelum", "waktu",
                "hari", "malam", "pagi", "siang", "sore", "jam", "detik", "menit", "kadang", "kadang-kadang"
        };

        Set<String> stopWordSet = new HashSet<>(Arrays.asList(stopWords));

        String cleanedInput = inputGejala.replaceAll("[\\p{Punct}]", " ");
        String[] tokens = cleanedInput.toLowerCase().split("\\s+");

        for (String word : tokens) {
            if (!word.isEmpty() && !stopWordSet.contains(word)) {
                gejala.add(word);
            }
        }

    }
    









}
