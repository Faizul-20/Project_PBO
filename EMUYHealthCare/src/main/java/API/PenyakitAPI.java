package API;

import java.util.HashMap;

public class PenyakitAPI {
    private String nama;
    private String gejala;

    public PenyakitAPI(String nama, String gejala){
        this.nama = nama;
        this.gejala = gejala;
    }
    public static HashMap<String,String> DataPenyakit = new HashMap<>();
}
