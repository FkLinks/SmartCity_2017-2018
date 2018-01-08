package com.henallux.smartcity.DAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.henallux.smartcity.Exceptions.LoginUserException;
import com.henallux.smartcity.Exceptions.RegisterUserException;
import com.henallux.smartcity.Exceptions.ShowInfosUserException;
import com.henallux.smartcity.Model.TokenReceived;
import com.henallux.smartcity.Model.User;

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
import static com.henallux.smartcity.Constants.URL_API_BASE;

public class UserDAO {

    private Gson gsonForUser = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .serializeNulls()
            .create();

    public TokenReceived checkUserExist(String login_password) throws LoginUserException, JSONException {
        TokenReceived tokenReceivedCode =new TokenReceived();
        try{
            URL url = new URL(URL_API_BASE +"Jwt");
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
                tokenReceivedCode.setCode(connection.getResponseCode());

                tokenReceivedCode.setExpirationDate(getExpirationDateForToken(tokenReceived));
            }
        }
        catch (IOException ioe){
            throw new LoginUserException();
        }
        catch (JSONException e) {
            e.getStackTrace();
        }

        return tokenReceivedCode;
    }

    private Date getExpirationDateForToken (JSONObject tokenReceived)throws JSONException{
        DateFormat dateFormatForToken = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.FRENCH);
        Date currentDate = new Date();
        Date expirationDate;

        int durationOfToken = Integer.parseInt(tokenReceived.getString("expires_in"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.SECOND, durationOfToken);

        expirationDate = calendar.getTime();
        dateFormatForToken.format(expirationDate);

        return expirationDate;
    }

    public int registerUser(User user) throws RegisterUserException, JSONException{
        int code;
        try{
            URL url = new URL(URL_API_BASE +"Account");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            connection.setDoOutput(true);
            connection.setDoInput(true);

            connection.connect();

            OutputStream outputStream = connection.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);

            String jsonUser = gsonForUser.toJson(user);

            writer.write(jsonUser);
            writer.flush();
            writer.close();

            code=connection.getResponseCode();

            outputStream.close();
            connection.disconnect();
        }
        catch (IOException e){
            throw new RegisterUserException();
        }

        return code;
    }

    public User getUserByName(String userName, String token) throws ShowInfosUserException, JSONException{
        String stringJSON = "";

        try {
            URL url = new URL(URL_API_BASE +"Account/" + userName);
            URLConnection connection = url.openConnection();

            connection.setRequestProperty("Authorization", "Bearer" + token);
            connection.setRequestProperty("Accept", "application/json");

            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            stringJSON = convertStreamToString(inputStream);
        }
        catch (IOException e){
            throw new ShowInfosUserException();
        }

        return jsonToUser(stringJSON);
    }

    private User jsonToUser(String stringJSON) throws JSONException{
        JSONObject jsonUser = new JSONObject(stringJSON);

        return gsonForUser.fromJson(jsonUser.toString(), User.class);
    }
}