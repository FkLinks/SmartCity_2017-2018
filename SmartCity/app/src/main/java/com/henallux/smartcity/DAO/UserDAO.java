package com.henallux.smartcity.DAO;

import com.henallux.smartcity.Model.TokenReceived;
import com.henallux.smartcity.Model.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//URL de base pour db azur :
//http://smartcity-jardin-20172018.azurewebsites.net/api/
public class UserDAO {
    public TokenReceived checkUserExist(String login_password, TokenReceived tokenReceivedCode) throws Exception{
        URL url = new URL("http://smartcity-jardin-20172018.azurewebsites.net/api/jwt");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-type", "application/json");

        connection.setDoOutput(true);
        connection.setDoInput(true);

        OutputStream outputStream = connection.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);

        connection.connect();

        writer.write(login_password);
        writer.flush();
        writer.close();

        InputStream inputStream = new BufferedInputStream(connection.getInputStream());
        String token = convertStreamToString(inputStream);

        outputStream.close();
        connection.disconnect();

        JSONObject tokenReceived = new JSONObject(token);
        tokenReceivedCode.setToken(tokenReceived.getString("access_token"));

        if(!tokenReceivedCode.getToken().equals("")){
            tokenReceivedCode.setCode(202);
        }

        return tokenReceivedCode;
    }

    private static String convertStreamToString(java.io.InputStream inputStream){
        java.util.Scanner scanner = new java.util.Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext()? scanner.next():"";
    }
}
