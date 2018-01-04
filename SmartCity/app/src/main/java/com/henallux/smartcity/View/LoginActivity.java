package com.henallux.smartcity.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.henallux.smartcity.DAO.UserDAO;
import com.henallux.smartcity.Exceptions.LoginUserException;
import com.henallux.smartcity.Model.TokenReceived;
import com.henallux.smartcity.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ConnectivityManager connectivityManager;
    private NetworkInfo activeNetwork;
    private boolean isConnected;
    private String userNameSharedPreferences;
    private String passwordSharedPreferences;
    private Button logIn;
    private TextView register;
    private EditText userName;
    private EditText password;
    private CheckBox rememberMe;
    private UserDAO userDAO = new UserDAO();
    private JSONObject toSend = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        userNameSharedPreferences = preferences.getString("userName", "");
        passwordSharedPreferences = preferences.getString("password", "");
        editor = preferences.edit();

        connectivityManager = (ConnectivityManager) LoginActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        userName = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        rememberMe = (CheckBox) findViewById(R.id.rememberMe);

        completeUserData();

        logIn = (Button) findViewById(R.id.log_in);
        logIn.setOnClickListener(loggingInListener);

        register = (TextView)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void completeUserData(){
        if(!passwordSharedPreferences.equals("")){
            userName.setText(userNameSharedPreferences);
            userName.setBackgroundColor(Color.YELLOW);
            password.setText(passwordSharedPreferences);
            password.setBackgroundColor(Color.YELLOW);
            rememberMe.setChecked(true);
        }
    }

    private View.OnClickListener loggingInListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            activeNetwork = connectivityManager.getActiveNetworkInfo();
            isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if(isConnected) {
                try {
                    toSend.put("UserName", userName.getText());
                    toSend.put("Password", password.getText());
                }
                catch (JSONException e) {
                    Toast.makeText(LoginActivity.this, R.string.json_exception_encountered, Toast.LENGTH_LONG).show();
                }

                new CheckUser().execute(toSend.toString());
            }
            else{
                Toast.makeText(LoginActivity.this, R.string.errorMissInternetCo, Toast.LENGTH_LONG).show();
            }
        }
    };

    private class CheckUser extends AsyncTask<String, Void, TokenReceived> {
        @Override
        protected TokenReceived doInBackground(String... userParams) {
            TokenReceived tokenReceived = new TokenReceived();

            try {
                tokenReceived = userDAO.checkUserExist(userParams[0]);

            }
            catch (LoginUserException e){
                Toast.makeText(LoginActivity.this, getString(R.string.errorCheckingUser), Toast.LENGTH_LONG).show();
            }
            catch (JSONException e) {
                Toast.makeText(LoginActivity.this, R.string.json_exception_encountered, Toast.LENGTH_LONG).show();
            }
            return tokenReceived;
        }

        @Override
        protected void onPostExecute(TokenReceived tokenReceived) {
            super.onPostExecute(tokenReceived);

            if(tokenReceived.getCode() == 200){
                Intent home = new Intent(LoginActivity.this, HomeActivity.class);
                editor.putString("token", tokenReceived.getToken());
                editor.putString("userName", userName.getText().toString());
                if(rememberMe.isChecked()) {
                    editor.putString("password", password.getText().toString());
                }
                else{
                    editor.putString("password", "");
                }
                editor.commit();
                startActivity(home);
            }
            else{
                Toast.makeText(LoginActivity.this, R.string.invalid_username_password, Toast.LENGTH_LONG).show();
            }
        }
    }
}
