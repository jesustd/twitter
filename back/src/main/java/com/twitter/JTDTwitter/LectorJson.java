package com.twitter.JTDTwitter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class LectorJson {
    public ArrayList<Tweet> LecturaJSONsimple(){
        String json;
        ArrayList<Tweet> Datas = new ArrayList<>();
        Type datas = new TypeToken<ArrayList<Tweet>>(){}.getType();

        Gson gson = new Gson();

        json = "";

        try (BufferedReader br = new BufferedReader(new FileReader("twitter.json"))){
            String linea;

            while ((linea = br.readLine()) != null){
                json+= linea;
                //System.out.println(linea);
            }

            Datas = gson.fromJson(json, datas);
            System.out.println("Sesion guardada con exito");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Datas;
    }
}
