package chatBotEngine;

import java.util.HashSet;
import java.lang.String;

public class Tokenization {
    public static void Gejala(String inputGejala){
        HashSet<String> gejala = new HashSet<>();
        String userInput = Main.inputGejala;
        userInput = userInput.replaceAll("[\\p{Punct}]", " ");
        String[] division = userInput.split(" ");
        for(String data : division){
            gejala.add(data);
        }
        for (String a : gejala){
            System.out.println(a);
        }
    }







}
