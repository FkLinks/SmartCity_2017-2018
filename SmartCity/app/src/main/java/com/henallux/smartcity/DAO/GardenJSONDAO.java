package com.henallux.smartcity.DAO;

import com.henallux.smartcity.Exceptions.GetAllGardensException;
import com.henallux.smartcity.Model.Garden;
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

public class GardenJSONDAO implements GardenDAO {
    private Converter converter = new ConverterJSON();

    public ArrayList<Garden> getAllGardens() throws GetAllGardensException, JSONException{
        String stringJSON;
        try {
            URL url = new URL(URL_API_BASE +"Gardens");
            URLConnection connection = url.openConnection();

            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            stringJSON = converter.convertStreamToString(inputStream);
        }
        catch (IOException e){
            throw new GetAllGardensException();
        }
        return converter.formatToGardens(stringJSON);
    }
}
