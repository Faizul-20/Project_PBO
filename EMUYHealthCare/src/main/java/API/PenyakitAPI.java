package API;

import DataBaseController.PenyakitConnecting;
import chatBotEngine.Tokenization;

import java.util.ArrayList;


public class PenyakitAPI {
    private String nama;
    private String gejala;

    public PenyakitAPI(String gejala, String nama){
        this.gejala = gejala;
        this.nama = nama;
    }

    public static ArrayList<Integer> matches = new ArrayList<>();

    public static ArrayList<Integer> match(){
        Tokenization tokenization = new Tokenization();
        PenyakitConnecting penyakitConnecting = new PenyakitConnecting();

        for (ArrayList<String> barisGejala : penyakitConnecting.result){
            int count = 0;
           for (String gejala : barisGejala){
               if (tokenization.gejala.contains(PenyakitConnecting.result)){
                   count++;
               }
           }
           matches.add(count);
        }

        return matches;

    }
}
