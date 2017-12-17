package com.henallux.smartcity;

import android.content.Intent;
import android.content.SharedPreferences;
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
    private User user;
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        userName = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);

        logIn = (Button) findViewById(R.id.log_in);
        logIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                JSONObject toSend = new JSONObject();
                TokenReceived tokenReceived = new TokenReceived();
                try {
                    toSend.put("UserName", userName.getText());
                    toSend.put("Password", password.getText());

                    tokenReceived = userDAO.checkUserExist(toSend.toString(), tokenReceived);

                    if(tokenReceived.getCode() == 202){
                        Intent home = new Intent(LoginActivity.this, HomeActivity.class);
                        editor.putBoolean("login", true);
                        editor.commit();
                        startActivity(home);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Mauvais numero ou mdp", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e){

                }
            }
        });

        register = (TextView)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });
    }
}
