package chatBotEngine;

import DataBaseController.UserConnecting;
import java.util.Scanner;

public class Main {

    static public String inputGejala;

    public static void main(String[] args) {
        //UserConnecting connecting = new UserConnecting();
        //connecting.ConnectToDatabase(connecting.getPENYAKIT_DATA());
        System.out.println("Masukkan gejala: ");
        Scanner input = new Scanner(System.in);
        String userInput = input.nextLine();
        inputGejala = userInput.toLowerCase();
        Tokenization.Gejala(inputGejala);


    }
}
