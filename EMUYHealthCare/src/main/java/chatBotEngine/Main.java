package chatBotEngine;

import API.PenyakitAPI;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static public String inputGejala;

    public static void main(String[] args) {

//        UserConnecting connecting = new UserConnecting();
//        connecting.ConnectToDatabase(connecting.getPENYAKIT_DATA());
        System.out.println("Masukkan gejala: ");
        Scanner input = new Scanner(System.in);
        String userInput = input.nextLine();
        inputGejala = userInput.toLowerCase();
        Tokenization.Gejala(inputGejala);

        ArrayList<Integer> resultMatches = PenyakitAPI.match();
        for (int i = 0; i < resultMatches.size(); i++) {
            System.out.println("Baris " + i + ": " + resultMatches.get(i) + " gejala cocok.");
        }






    }
}