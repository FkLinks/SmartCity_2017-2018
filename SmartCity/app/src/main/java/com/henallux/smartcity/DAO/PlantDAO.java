package com.henallux.smartcity.DAO;

import com.henallux.smartcity.Model.Plant;

import org.json.JSONException;

public interface PlantDAO {
    Plant getPlantInfos(String string) throws JSONException;
}
