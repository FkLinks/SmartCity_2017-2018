package com.henallux.smartcity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView enterAsGuest;
    private Button log_in;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        enterAsGuest = (TextView) findViewById(R.id.enterAsGuest);
        enterAsGuest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent home = new Intent(MainActivity.this, HomeActivity.class);
                editor.putString("token", "");
                editor.putString("userName", "");
                editor.commit();
                startActivity(home);
            }
        });

        log_in = (Button) findViewById(R.id.log_in);
        log_in.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent login = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });
    }
}
