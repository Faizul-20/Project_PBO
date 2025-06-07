package chatBotEngine;

import java.util.HashMap;
import java.util.HashSet;
import java.lang.String;
import java.util.Map;
import java.util.regex.Pattern;

import API.PenyakitAPI;
import DataBaseController.PenyakitConnecting;
import DataBaseController.UserConnecting;

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

//    public static void KeyMap(HashMap<String, String> penyakit){
//       // Map.Entry<String, String[]> keyWordsMap = new HashMap<>();
//        Pattern pattern =Pattern.compile("\\s+");
//        for (Map<String, String> row : PenyakitAPI.DataPenyakit.entrySet()){
//            for (String key : row.keySet()){
//                String[] words = pattern.split(key.toLowerCase());
//                keyWordsMap.put(key, words);
//            }
//        }
//    }







}
