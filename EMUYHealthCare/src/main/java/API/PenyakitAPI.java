package API;

import java.util.HashMap;

public class PenyakitAPI {
    private String nama;
    private String gejala;

    public PenyakitAPI(String gejala, String nama){
        this.gejala = gejala;
        this.nama = nama;
    }
    public static HashMap<String,String> DataPenyakit = new HashMap<>();
}
