package com.henallux.smartcity.DAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.henallux.smartcity.Model.Plant;

import org.json.JSONObject;

public class PlantDAO {
    public Plant jsonToPlant(String stringJSON) throws Exception{
        JSONObject jsonPlant = new JSONObject(stringJSON);

        Gson object = new GsonBuilder().create();

        return object.fromJson(jsonPlant.toString(), Plant.class);
    }
}
