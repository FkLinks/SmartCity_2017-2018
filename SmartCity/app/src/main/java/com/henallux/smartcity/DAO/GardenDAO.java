package com.henallux.smartcity.DAO;

import com.henallux.smartcity.Exceptions.GetAllGardensException;
import com.henallux.smartcity.Model.Garden;

import org.json.JSONException;

import java.util.ArrayList;

public interface GardenDAO {
    ArrayList<Garden> getAllGardens() throws GetAllGardensException, JSONException;
}
