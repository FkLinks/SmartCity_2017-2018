package com.henallux.smartcity.DAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.henallux.smartcity.Exceptions.LoginUserException;
import com.henallux.smartcity.Model.TokenReceived;
import com.henallux.smartcity.Model.User;
import com.henallux.smartcity.R;
import com.henallux.smartcity.RegisterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

//URL de base pour db azur :
//http://smartcity-jardin-20172018.azurewebsites.net/api/
public class UserDAO {
    public TokenReceived checkUserExist(String login_password) throws IOException, LoginUserException, JSONException {
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
        catch (IOException e){
            throw new LoginUserException();
        }
        catch (JSONException je){
            je.printStackTrace();
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
            connection.setDoInput(true);

            connection.connect();

            OutputStream outputStream = connection.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);

            Gson object = new GsonBuilder().create();
            String jsonUser = object.toJson(user);

            writer.write(jsonUser);
            writer.flush();
            writer.close();

            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            String token = convertStreamToString(inputStream);

            outputStream.close();
            connection.disconnect();

            JSONObject tokenReceived = new JSONObject(token);

            if(tokenReceived.getString("ReasonPhrase").equals("OK")){
                codeReceived = 200;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return codeReceived;
    }

    private static String convertStreamToString(java.io.InputStream inputStream){
        java.util.Scanner scanner = new java.util.Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext()? scanner.next():"";
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
/*
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String string = "",line;
            while((line = bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
            bufferedReader.close();
            string = stringBuilder.toString();
            stringJSON = string;*/
        }
        catch (Exception e){
            e.getStackTrace();
        }
        return jsonToUser(stringJSON);
    }

    private User jsonToUser(String stringJSON) throws Exception{
        User user = new User();

        try {
            JSONArray jsonArray = new JSONArray(stringJSON);
            JSONObject jsonUser = jsonArray.getJSONObject(0);

            Gson object = new GsonBuilder().create();
            user = object.fromJson(jsonUser.toString(), User.class); //new User(jsonUser.getString("UserName"), jsonUser.getString("Password"), jsonUser.getString("Email"), jsonUser.getString("Sex"), jsonUser.getString("BirthDate"), jsonUser.getString("GeographicOrigins"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
}