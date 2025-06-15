package API;


import java.util.ArrayList;

public class PenyakitAPI {
    public static String gejalaUser;
    public static String feedback;
    public static String diagnosa;
    public static String penanganan;
    public static ArrayList<String> kodeGejalaKonfirmasi;

    public PenyakitAPI(String gejalaUser, String feedback, String diagnosa, String penanganan, ArrayList<String> kodeGejalaKonfirmasi){
        this.gejalaUser = gejalaUser;
        this.feedback = feedback;
        this.diagnosa = diagnosa;
        this.penanganan = penanganan;
        this.kodeGejalaKonfirmasi = kodeGejalaKonfirmasi;
    }





}
