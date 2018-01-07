package com.henallux.smartcity.DAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.henallux.smartcity.Exceptions.GetEventsException;
import com.henallux.smartcity.Model.Event;

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

public class EventDAO {
    public ArrayList<Event> getAllEvents() throws GetEventsException, JSONException{
        String stringJSON;
        try {
            URL url = new URL(URL_API_BASE +"Events");
            URLConnection connection = url.openConnection();

            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            stringJSON = convertStreamToString(inputStream);
        }
        catch (IOException e){
            throw new GetEventsException();
        }

        return jsonToEvents(stringJSON);
    }

    private ArrayList<Event> jsonToEvents(String stringJSON) throws JSONException{
        ArrayList<Event> events = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(stringJSON);
        for(int i = 0; i<jsonArray.length();i++){
            JSONObject jsonGarden = jsonArray.getJSONObject(i);

            Gson object = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

            events.add(object.fromJson(jsonGarden.toString(), Event.class));
        }
        return events;
    }
}
