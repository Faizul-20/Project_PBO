package chatBotEngine;

import java.util.HashMap;
import java.util.HashSet;
import java.lang.String;
import java.util.Map;
import java.util.regex.Pattern;

import API.PenyakitAPI;

public class Tokenization {


    public static void Gejala(String inputGejala){
        HashSet<String> gejala = new HashSet<>();
        String userInput = Main.inputGejala;
        userInput = userInput.replaceAll("[\\p{Punct}]", " ");
        String[] division = userInput.split(" ");
        for(String data : division){
            gejala.add(data);
        }
    }

    public static Map<String, String[]> KeyMap(){
        Map<String, String[]> keyWordsMap = new HashMap<>();
        Pattern pattern =Pattern.compile("\\s+");

        for (Map.Entry<String, String> entry : PenyakitAPI.DataPenyakit.entrySet()){
                String key = entry.getKey();
                String[] words = pattern.split(key.toLowerCase());
                keyWordsMap.put(key, words);
        }
        return keyWordsMap;
    }

    public static void Compare()







}
