package chatBotEngine;

import DataBaseController.PenyakitConnecting;

import java.util.*;
import java.util.ArrayList;
import java.util.Map;


public class Main {

    static public String inputGejala;

    public static void main(String[] args) {

//        DataConnecting dataConnecting = new DataConnecting();
//        dataConnecting.ConnectToDatabase(dataConnecting.getPENYAKIT_DATA());

        System.out.println("Masukkan gejala: ");
        Scanner input = new Scanner(System.in);
        String userInput = input.nextLine();
        inputGejala = userInput.toLowerCase();
        Tokenization.Gejala(inputGejala);

       PenyakitConnecting penyakitConnecting = new PenyakitConnecting();

       //Tes isi tabel gejala
        LinkedHashMap<String, ArrayList<String>> dataGejala = penyakitConnecting.addMapGejala();
//        for (Map.Entry<String, ArrayList<String>> entry : dataGejala.entrySet()) {
//            System.out.println("Kode: " + entry.getKey());
//            System.out.println("Gejala: " + entry.getValue());
//        }

        //Tes kode gejala match
        HashSet<String> InputUser = Tokenization.Gejala(inputGejala);
        ArrayList<String> resultMatchCode = penyakitConnecting.getMatchedKodeGejala(InputUser, dataGejala);
        if (resultMatchCode.isEmpty()) {
            System.out.println("Tidak ada gejala yang cocok.");
        } else {
            System.out.println("Gejala cocok ditemukan untuk kode:");
            for (String kode : resultMatchCode) {
                System.out.println("- " + kode);
            }
        }

        


    }
}