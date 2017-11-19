package com.henallux.smartcity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private Button logIn;
    private TextView register;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        logIn = (Button) findViewById(R.id.log_in);
        logIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent home = new Intent(LoginActivity.this, HomeActivity.class);
                editor.putBoolean("login", true);
                editor.commit();
                startActivity(home);
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
