package chatBotEngine;

import API.PenyakitAPI;
import DataBaseController.PenyakitConnecting;
import DataBaseController.UserConnecting;

import java.util.Map;
import java.util.Scanner;

public class Main {

    static public String inputGejala;

    public static void main(String[] args) {
       /* UserConnecting connecting = new UserConnecting();
        connecting.ConnectToDatabase(connecting.getPENYAKIT_DATA());
        System.out.println("Masukkan gejala: ");
        Scanner input = new Scanner(System.in);
        String userInput = input.nextLine();
        inputGejala = userInput.toLowerCase();
        Tokenization.Gejala(inputGejala);*/
        PenyakitConnecting penyakitConnecting = new PenyakitConnecting();
        penyakitConnecting.AddPenyakit();
        for (Map.Entry<String, String> entry : PenyakitAPI.DataPenyakit.entrySet()){
            System.out.println(entry.getKey() + entry.getValue());
        }



    }
}
