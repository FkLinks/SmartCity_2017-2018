package com.henallux.smartcity.DAO;

import com.henallux.smartcity.Model.Plant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlantDAO {
    public Plant jsonToPlant(String stringJSON) throws Exception{
        Plant plant;

        JSONArray jsonArray = new JSONArray(stringJSON);
        JSONObject jsonPlant = jsonArray.getJSONObject(0);
        plant = new Plant(jsonPlant.getString("name"),jsonPlant.getString("description"),jsonPlant.getString("urlAudioGuide"));

        return plant;
    }
}
