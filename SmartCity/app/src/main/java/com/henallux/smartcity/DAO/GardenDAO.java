package com.henallux.smartcity.DAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.henallux.smartcity.Exceptions.GetAllGardensException;
import com.henallux.smartcity.Model.Garden;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static com.henallux.smartcity.Constants.convertStreamToString;
import static com.henallux.smartcity.Constants.URL_API_BASE;

public class GardenDAO {
    public ArrayList<Garden> getAllGardens() throws GetAllGardensException, JSONException{
        String stringJSON;
        try {
            URL url = new URL(URL_API_BASE +"Gardens");
            URLConnection connection = url.openConnection();

            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            stringJSON = convertStreamToString(inputStream);
        }
        catch (IOException e){
            throw new GetAllGardensException();
        }
        return jsonToGardens(stringJSON);
    }

    private ArrayList<Garden> jsonToGardens(String stringJSON) throws JSONException{
        ArrayList<Garden> gardens = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(stringJSON);

        Gson object = new GsonBuilder().create();
        for(int i = 0; i<jsonArray.length();i++){
            JSONObject jsonGarden = jsonArray.getJSONObject(i);

            gardens.add(object.fromJson(jsonGarden.toString(), Garden.class));
        }
        return gardens;
    }
}
