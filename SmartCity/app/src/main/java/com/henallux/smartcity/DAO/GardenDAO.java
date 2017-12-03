package com.henallux.smartcity.DAO;

import com.henallux.smartcity.Model.Garden;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class GardenDAO {
    public ArrayList<Garden> getAllGardens() throws Exception{
        URL url = new URL("");
        URLConnection connection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String stringJSON = "",line;
        while((line = bufferedReader.readLine())!=null){
            stringBuilder.append(line);
        }
        bufferedReader.close();
        stringJSON = stringJSON.toString();
        return jsonToGardens(stringJSON);
    }

    private ArrayList<Garden> jsonToGardens(String strinJSON) throws Exception{
        ArrayList<Garden> gardens = new ArrayList<>();
        Garden garden;
        JSONArray jsonArray = new JSONArray(strinJSON);
        for(int i = 0; i<jsonArray.length();i++){
            JSONObject jsonGarden = jsonArray.getJSONObject(i);
            garden = new Garden(jsonGarden.getString("name"),jsonGarden.getDouble("surfaceArea"),jsonGarden.getString("street"),jsonGarden.getInt("numStreet"),jsonGarden.getString("description"),jsonGarden.getDouble("note"),jsonGarden.getString("geographicalCoordinates"));
            gardens.add(garden);
        }
        return gardens;
    }
}
