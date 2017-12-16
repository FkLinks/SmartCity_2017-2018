package com.henallux.smartcity.DAO;

import com.henallux.smartcity.Model.Plant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

//URL de base pour db azur :
//http://smartcity-jardin-20172018.azurewebsites.net/api/
public class PlantDAO {
    public Plant jsonToPlant(String stringJSON) throws Exception{
        Plant plant;

        JSONArray jsonArray = new JSONArray(stringJSON);
        JSONObject jsonPlant = jsonArray.getJSONObject(0);
        plant = new Plant(jsonPlant.getString("name"),jsonPlant.getString("description"));

        return plant;
    }
}
