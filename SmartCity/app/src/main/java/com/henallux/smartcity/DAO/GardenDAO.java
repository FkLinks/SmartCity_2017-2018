package com.henallux.smartcity.DAO;

import com.henallux.smartcity.Model.Garden;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static com.henallux.smartcity.Constants.convertStreamToString;

//URL de base pour db azur :
//http://smartcity-jardin-20172018.azurewebsites.net/api/
public class GardenDAO {
    public ArrayList<Garden> getAllGardens() throws Exception{
        URL url = new URL("http://smartcity-jardin-20172018.azurewebsites.net/api/Gardens");
        URLConnection connection = url.openConnection();

        InputStream inputStream = new BufferedInputStream(connection.getInputStream());
        String stringJSON = convertStreamToString(inputStream);

        return jsonToGardens(stringJSON);
    }

    private ArrayList<Garden> jsonToGardens(String stringJSON) throws Exception{
        ArrayList<Garden> gardens = new ArrayList<>();
        Garden garden;
        JSONArray jsonArray = new JSONArray(stringJSON);
        for(int i = 0; i<jsonArray.length();i++){
            JSONObject jsonGarden = jsonArray.getJSONObject(i);

            garden = new Garden(jsonGarden.getString("name"), jsonGarden.getDouble("superficie"), jsonGarden.getString("street"), jsonGarden.getInt("numStreet"), jsonGarden.getString("description"), jsonGarden.getDouble("note"), jsonGarden.getString("geographicalCoordinate"), jsonGarden.getString("urlImg"),jsonGarden.getString("urlAudio"));

            gardens.add(garden);
        }
        return gardens;
    }
}