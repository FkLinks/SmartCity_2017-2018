package com.henallux.smartcity.DAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.henallux.smartcity.Model.Event;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static com.henallux.smartcity.Constants.convertStreamToString;

//URL de base pour db azur :
//http://smartcity-jardin-20172018.azurewebsites.net/api/
public class EventDAO {
    public ArrayList<Event> getAllEvents() throws Exception{
        URL url = new URL("http://smartcity-jardin-20172018.azurewebsites.net/api/Events");
        URLConnection connection = url.openConnection();

        InputStream inputStream = new BufferedInputStream(connection.getInputStream());
        String stringJSON = convertStreamToString(inputStream);

        return jsonToEvents(stringJSON);
    }

    private ArrayList<Event> jsonToEvents(String stringJSON) throws Exception{
        ArrayList<Event> events = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(stringJSON);
        for(int i = 0; i<jsonArray.length();i++){
            JSONObject jsonGarden = jsonArray.getJSONObject(i);

            Gson object = new GsonBuilder().create();

            events.add(object.fromJson(jsonGarden.toString(), Event.class));
        }
        return events;
    }
}
