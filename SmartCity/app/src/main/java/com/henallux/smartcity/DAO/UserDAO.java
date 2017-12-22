package com.henallux.smartcity.DAO;

import com.henallux.smartcity.Model.TokenReceived;
import com.henallux.smartcity.Model.User;
import com.henallux.smartcity.RegisterActivity;

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

//URL de base pour db azur :
//http://smartcity-jardin-20172018.azurewebsites.net/api/
public class UserDAO {
    public TokenReceived checkUserExist(String login_password) throws Exception{
        TokenReceived tokenReceivedCode =new TokenReceived();
        try{
            URL url = new URL("http://smartcity-jardin-20172018.azurewebsites.net/api/Jwt");
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
                tokenReceivedCode.setCode(200);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return tokenReceivedCode;
    }

    //enlever token
    public TokenReceived registerUser(String user) throws Exception{
        TokenReceived tokenReceivedCode = new TokenReceived();
        try{
            URL url = new URL("http://smartcity-jardin-20172018.azurewebsites.net/api/Account");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            connection.setDoOutput(true);
            //connection.setDoInput(true);

            OutputStream outputStream = connection.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);

            connection.connect();

            String i = connection.getResponseMessage();

            writer.write(user);
            writer.flush();
            writer.close();

            /*InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            String token = convertStreamToString(inputStream);*/

            outputStream.close();
            connection.disconnect();

            /*JSONObject tokenReceived = new JSONObject(token);
            tokenReceivedCode.setToken(tokenReceived.getString("ReasonPhrase"));

            if(tokenReceivedCode.getToken().equals("OK")){
                tokenReceivedCode.setCode(200);
            }*/
            tokenReceivedCode.setCode(200);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return tokenReceivedCode;
    }

    private static String convertStreamToString(java.io.InputStream inputStream){
        java.util.Scanner scanner = new java.util.Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext()? scanner.next():"";
    }

    public User getUserByName(String userName) throws Exception{
        String stringJSON = "";

        try {
            URL url = new URL("http://smartcity-jardin-20172018.azurewebsites.net/api/Account/" + userName);
            URLConnection connection = url.openConnection();
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            stringJSON = convertStreamToString(inputStream);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return jsonToUser(stringJSON);
    }

    private User jsonToUser(String stringJSON) throws Exception{
        User user;

        JSONArray jsonArray = new JSONArray(stringJSON);
        JSONObject jsonUser = jsonArray.getJSONObject(0);

        user = new User(jsonUser.getString("UserName"),jsonUser.getString("Password"), jsonUser.getString("Email"), jsonUser.getString("Sex"), jsonUser.getString("BirthDate"), jsonUser.getString("GeographicOrigins"));

        return user;
    }
}
