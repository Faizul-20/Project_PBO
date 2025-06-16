package chatBotEngine;

import java.util.*;
import java.lang.String;

public class Tokenization {

    public static HashSet<String> Gejala(String inputGejala) {
        String[] stopWords = {
                "saya", "kami", "kita", "anda", "gue", "nih", "ya", "lah", "deh", "dong",
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
                "hari", "malam", "pagi", "siang", "sore", "jam", "detik", "menit", "kadang", "kadang-kadang",
                "kayaknya", "kayak", "gitu", "gini", "begituan", "begini", "gitulah",
                "gimana", "gitu aja", "aja sih", "pokoknya", "intinya", "semacam", "seolah", "gak ngerti",
                "udah", "udah deh", "udah sih", "udah banget", "udah parah", "udah lama", "udah banget deh",
                "masih", "masih sih", "udah mulai", "udah kerasa", "udah kerasa banget", "masih terasa",
                "parah", "parahan", "banget", "bangetan", "banget banget", "lumayan", "nggak tau", "entah",
                "ampun", "ya ampun", "ya Allah", "duh", "aduh", "astaga", "aduhai", "yah", "weleh", "waduh", "alah", "beuh",
                "terus", "abis itu", "habis itu", "makanya", "soalnya", "so", "lah", "loh", "dong", "kan", "pun",
                "padahal", "cuman", "doang", "aja", "malah", "sampe", "sampai", "sebelumnya", "sekarang", "sekarang-sekarang",
                "tolong", "mohon", "gimana ya", "menurut dokter", "apa ya", "apa bisa", "boleh", "bisakah", "kira-kira",
                "mungkin ya", "barangkali", "harap", "kayak gimana", "yang cocok apa", "apa penyebabnya", "apa solusinya",
                "rasanya", "kayaknya", "kurasa", "menurutku", "kupikir", "ngira", "mikir", "sepertinya", "sepertinya sih",
                "perasaanku", "feelingku", "insting", "pikiranku", "intuisiku", "bisa jadi",
                "sering", "jarang", "kadang", "kadang-kadang", "setiap hari", "tiap malam", "pagi-pagi", "malem-malem",
                "beberapa kali", "berulang", "udah lama", "baru aja", "kemarin", "tadi", "barusan", "besok", "nanti",
                "badan", "tubuh", "bagian", "area", "daerah", "sebagian", "semua bagian",
                "soalnya", "gimana ya", "gak ngerti", "bingung", "aneh", "nggak biasa", "nggak normal", "berbeda", "beda",
                "ya gitu", "yaudah", "jadi gini", "makanya", "kalau", "ya gitu deh", "sakit", "merasa"
        };

        Set<String> stopWordSet = new HashSet<>(Arrays.asList(stopWords));

        String cleanedInput = inputGejala.replaceAll("[\\p{Punct}]", " ");
        String[] tokens = cleanedInput.toLowerCase().split("\\s+");

        HashSet<String> gejala = new HashSet<>();

        for (String word : tokens) {
            if (!word.isEmpty() && !stopWordSet.contains(word)) {
                gejala.add(word);
            }
        }
        return gejala;

    }
    









}
