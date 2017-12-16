package com.henallux.smartcity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    private Boolean login;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Button register;
    private TextView goToLogin;
    private EditText userName;
    private EditText password;
    private EditText email;
    private RadioGroup sex;
    private EditText birthdate;
    private EditText geographicalOrigins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        userName = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        sex = (RadioGroup) findViewById(R.id.sexChoice);
        birthdate = (EditText) findViewById(R.id.birthDate);
        geographicalOrigins = (EditText) findViewById(R.id.whereHeFrom);


        register = (Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean("login", true);
                editor.commit();
                startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
            }
        });

        goToLogin = (TextView) findViewById(R.id.sign_in);
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}
