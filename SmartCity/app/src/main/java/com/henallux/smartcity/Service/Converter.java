package com.henallux.smartcity.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.henallux.smartcity.Model.Event;
import com.henallux.smartcity.Model.Garden;
import com.henallux.smartcity.Model.Plant;
import com.henallux.smartcity.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public interface Converter {
    String convertStreamToString(java.io.InputStream inputStream);

    User formatToUser(String stringJSON) throws JSONException;

    ArrayList<Garden> formatToGardens(String stringJSON) throws JSONException;

    ArrayList<Event> formatToEvents(String stringJSON) throws JSONException;

    Plant formatToPlant(String stringJSON) throws JSONException;
}
