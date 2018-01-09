package com.henallux.smartcity.DAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.henallux.smartcity.Model.Plant;
import com.henallux.smartcity.Service.Converter;
import com.henallux.smartcity.Service.ConverterJSON;

import org.json.JSONException;
import org.json.JSONObject;

public class PlantJSONDAO implements PlantDAO {
    private Converter converter = new ConverterJSON();

    public Plant getPlantInfos(String string) throws JSONException {
        return converter.formatToPlant(string);
    }
}
