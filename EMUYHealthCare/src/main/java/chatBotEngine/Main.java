package chatBotEngine;

import API.PenyakitAPI;
import DataBaseController.PenyakitConnecting;
import DataBaseController.UserConnecting;

import java.util.Map;
import java.util.Scanner;

public class Main {

    static public String inputGejala;

    public static void main(String[] args) {
        /*
        UserConnecting connecting = new UserConnecting();
        connecting.ConnectToDatabase(connecting.getPENYAKIT_DATA());
        System.out.println("Masukkan gejala: ");
        Scanner input = new Scanner(System.in);
        String userInput = input.nextLine();
        inputGejala = userInput.toLowerCase();
        Tokenization.Gejala(inputGejala);


        PenyakitConnecting penyakitConnecting = new PenyakitConnecting();
        penyakitConnecting.AddPenyakit();
        for (Map.Entry<String, String> entry : PenyakitAPI.DataPenyakit.entrySet()) {
            System.out.println(entry.getKey());
        }

*/      PenyakitConnecting penyakitConnecting = new PenyakitConnecting();
        penyakitConnecting.AddPenyakit();
        Map<String, String[]> result = Tokenization.KeyMap();
        for (Map.Entry<String, String[]> entry : result.entrySet()) {
            System.out.print("Key: " + entry.getKey() + " => ");
            for (String kata : entry.getValue()) {
                System.out.print(kata + ", ");
            }
            System.out.println();
        }

    }
}