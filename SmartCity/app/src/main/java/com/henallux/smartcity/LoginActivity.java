package com.henallux.smartcity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.henallux.smartcity.DAO.UserDAO;
import com.henallux.smartcity.Model.TokenReceived;
import com.henallux.smartcity.Model.User;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private Button logIn;
    private TextView register;
    private EditText userName;
    private EditText password;
    private UserDAO userDAO = new UserDAO();
    private JSONObject toSend = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        userName = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);

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

    private View.OnClickListener loggingInListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            try {
                toSend.put("UserName", userName.getText());
                toSend.put("Password", password.getText());

                new CheckUser().execute(toSend.toString());
            }
            catch (Exception e){
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
            catch (Exception e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                editor.commit();
                startActivity(home);
            }
            else{
                Toast.makeText(LoginActivity.this, "mot de passe ou username invalide", Toast.LENGTH_LONG).show();
            }
        }
    }
}
