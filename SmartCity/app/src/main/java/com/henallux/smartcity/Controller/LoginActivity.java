package com.henallux.smartcity.Controller;

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

import com.henallux.smartcity.DAO.UserJSONDAO;
import com.henallux.smartcity.Exceptions.LoginUserException;
import com.henallux.smartcity.Model.TokenReceived;
import com.henallux.smartcity.Model.UserLogin;
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
    private UserJSONDAO userJSONDAO = new UserJSONDAO();

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
            if(!(userName.getText().toString().equals("") || password.getText().toString().equals(""))) {
                activeNetwork = connectivityManager.getActiveNetworkInfo();
                isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                if (isConnected) {

                    new CheckUser().execute(new UserLogin(userName.getText().toString(), password.getText().toString()));

                } else {
                    Toast.makeText(LoginActivity.this, R.string.errorMissInternetCo, Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(LoginActivity.this, R.string.login_empty_fields, Toast.LENGTH_LONG).show();
            }
        }
    };

    private class CheckUser extends AsyncTask<UserLogin, Void, TokenReceived> {
        @Override
        protected TokenReceived doInBackground(UserLogin... userParams) {
            TokenReceived tokenReceived = new TokenReceived();

            try {
                tokenReceived = userJSONDAO.checkUserExist(userParams[0]);

            }
            catch (LoginUserException e){
                tokenReceived.setErrorException(getString(R.string.invalid_username_password));
            }
            catch (JSONException e) {
                tokenReceived.setErrorException(getString(R.string.json_exception_encountered));
            }
            return tokenReceived;
        }

        @Override
        protected void onPostExecute(TokenReceived tokenReceived) {
            super.onPostExecute(tokenReceived);

            if(tokenReceived.getErrorException().equals("") && tokenReceived.getCode() == 200) {
                Intent home = new Intent(LoginActivity.this, HomeActivity.class);
                editor.putString("token", tokenReceived.getToken());
                editor.putString("userName", userName.getText().toString());
                if (rememberMe.isChecked()) {
                    editor.putString("password", password.getText().toString());
                } else {
                    editor.putString("password", "");
                }
                editor.commit();

                startActivity(home);

                Toast.makeText(LoginActivity.this, getString(R.string.welcomeMessage) + " " + userName.getText().toString(), Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(LoginActivity.this, tokenReceived.getErrorException(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
