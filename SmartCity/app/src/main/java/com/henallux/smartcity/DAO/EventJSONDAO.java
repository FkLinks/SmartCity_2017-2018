package com.henallux.smartcity.DAO;

import com.henallux.smartcity.Exceptions.GetEventsException;
import com.henallux.smartcity.Model.Event;
import com.henallux.smartcity.Service.Converter;
import com.henallux.smartcity.Service.ConverterJSON;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static com.henallux.smartcity.Constants.URL_API_BASE;

public class EventJSONDAO implements EventDAO {
    private Converter converter = new ConverterJSON();

    public ArrayList<Event> getAllEvents() throws GetEventsException, JSONException{
        String stringJSON;
        try {
            URL url = new URL(URL_API_BASE +"Events");
            URLConnection connection = url.openConnection();

            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            stringJSON = converter.convertStreamToString(inputStream);
        }
        catch (IOException e){
            throw new GetEventsException();
        }

        return converter.formatToEvents(stringJSON);
    }
}
