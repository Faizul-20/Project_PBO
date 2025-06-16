package main.java.API;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PenyakitAPI {
    public static String gejalaUser;
    public static String feedback;
    public static String diagnosa;
    public static String penanganan;
    public static ArrayList<String> kodeGejalaKonfirmasi;
    public static ArrayList<String> matchedKodeAwal;
    public static List<Integer> top3Penyakit;
    public static Map<Integer, String> gejalaNumberToCode;

    public PenyakitAPI(String gejalaUser, String feedback, String diagnosa, String penanganan, ArrayList<String> kodeGejalaKonfirmasi, List<Integer> top3Penyakit, Map<Integer, String> gejalaNumberToCode){
        this.gejalaUser = gejalaUser;
        this.feedback = feedback;
        this.diagnosa = diagnosa;
        this.penanganan = penanganan;
        this.kodeGejalaKonfirmasi = kodeGejalaKonfirmasi;
        this.top3Penyakit = top3Penyakit;
        this.gejalaNumberToCode = gejalaNumberToCode;
    }





}
