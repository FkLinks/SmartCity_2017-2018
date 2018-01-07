package com.henallux.smartcity.DAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.henallux.smartcity.Model.Plant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlantDAO {
    public Plant jsonToPlant(String stringJSON) throws Exception{
        //JSONArray jsonArray = new JSONArray(stringJSON);
        JSONObject jsonPlant = new JSONObject(stringJSON);//jsonArray.getJSONObject(0);

        Gson object = new GsonBuilder().create();

        return object.fromJson(jsonPlant.toString(), Plant.class);
    }
}
