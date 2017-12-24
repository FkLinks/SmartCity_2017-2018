package com.henallux.smartcity.DAO;

import com.henallux.smartcity.Model.Event;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

//URL de base pour db azur :
//http://smartcity-jardin-20172018.azurewebsites.net/api/
public class EventDAO {
    public ArrayList<Event> getAllEvents() throws Exception{
        URL url = new URL("http://smartcity-jardin-20172018.azurewebsites.net/api/Events");
        URLConnection connection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String stringJSON = "",line;
        while((line = bufferedReader.readLine())!=null){
            stringBuilder.append(line);
        }
        bufferedReader.close();
        stringJSON = stringBuilder.toString();
        return jsonToEvents(stringJSON);
    }

    private ArrayList<Event> jsonToEvents(String stringJSON) throws Exception{
        ArrayList<Event> events = new ArrayList<>();
        Event event;
        JSONArray jsonArray = new JSONArray(stringJSON);
        for(int i = 0; i<jsonArray.length();i++){
            JSONObject jsonGarden = jsonArray.getJSONObject(i);

            event = new Event(jsonGarden.getString("name"), jsonGarden.getString("description"), jsonGarden.getString("startTime"), jsonGarden.getString("endTime"), jsonGarden.getString("url"));

            events.add(event);
        }
        return events;
    }
}
