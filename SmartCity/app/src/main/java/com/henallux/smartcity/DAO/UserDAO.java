package com.henallux.smartcity.DAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.henallux.smartcity.Exceptions.LoginUserException;
import com.henallux.smartcity.Model.TokenReceived;
import com.henallux.smartcity.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.henallux.smartcity.Constants.convertStreamToString;

//URL de base pour db azur :
//http://smartcity-jardin-20172018.azurewebsites.net/api/
public class UserDAO {
    public TokenReceived checkUserExist(String login_password) throws LoginUserException, JSONException {
        TokenReceived tokenReceivedCode =new TokenReceived();
        try{
            URL url = new URL("http://smartcity-jardin-20172018.azurewebsites.net/api/Jwt");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/json");

            connection.setDoOutput(true);
            connection.setDoInput(true);

            connection.connect();

            OutputStream outputStream = connection.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);

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
                tokenReceivedCode.setCode(200);

                tokenReceivedCode.setExpirationDate(getExpirationDateForToken (tokenReceived));
            }
        }
        catch (IOException ioe){
            throw new LoginUserException();
        }

        return tokenReceivedCode;
    }

    private Date getExpirationDateForToken (JSONObject tokenReceived){
        DateFormat dateFormatForToken = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.FRENCH);
        Date currentDate = new Date();
        Date expirationDate = new Date();

        try{
            int durationOfToken = Integer.parseInt(tokenReceived.getString("expires_in"));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.SECOND, durationOfToken);

            expirationDate = calendar.getTime();
            dateFormatForToken.format(expirationDate);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return expirationDate;
    }

    public int registerUser(User user) throws Exception{
        int codeReceived = 0;
        try{
            URL url = new URL("http://smartcity-jardin-20172018.azurewebsites.net/api/Account");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            connection.setDoOutput(true);
            //connection.setDoInput(true);

            connection.connect();

            OutputStream outputStream = connection.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);

            Gson object = new GsonBuilder().create();
            String jsonUser = object.toJson(user);

            writer.write(jsonUser);
            writer.flush();
            writer.close();

            /*InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            String token = convertStreamToString(inputStream);*/

            outputStream.close();
            connection.disconnect();
/*
            JSONObject tokenReceived = new JSONObject(token);*/

            /*if(tokenReceived.getString("ReasonPhrase").equals("OK")){*/
                codeReceived = 200;
            //}
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return codeReceived;
    }

    public User getUserByName(String userName, String token) throws Exception{
        String stringJSON = "";

        try {
            URL url = new URL("http://smartcity-jardin-20172018.azurewebsites.net/api/Account/" + userName);
            URLConnection connection = url.openConnection();

            connection.setRequestProperty("Authorization", "Bearer" + token);
            connection.setRequestProperty("Accept", "application/json");

            connection.setDoInput(true);

            connection.connect();

            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            stringJSON = convertStreamToString(inputStream);
        }
        catch (Exception e){
            e.getStackTrace();
        }
        return jsonToUser(stringJSON);
    }

    private User jsonToUser(String stringJSON) throws Exception{
        JSONArray jsonArray = new JSONArray(stringJSON);
        JSONObject jsonUser = jsonArray.getJSONObject(0);

        Gson object = new GsonBuilder().create();
        //new User(jsonUser.getString("UserName"), jsonUser.getString("Password"), jsonUser.getString("Email"), jsonUser.getString("Sex"), jsonUser.getString("BirthDate"), jsonUser.getString("GeographicOrigins"));

        return object.fromJson(jsonUser.toString(), User.class);
    }
}