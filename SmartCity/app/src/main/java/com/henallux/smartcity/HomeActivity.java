package com.henallux.smartcity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private Boolean login;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ListView listChoices;
    private ArrayList<String> listItems= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        login = preferences.getBoolean("login", false);
        editor=preferences.edit();

        listItems.add(getString(R.string.home_menu_gardens));
        listItems.add(getString(R.string.home_menu_scan));
        listItems.add(getString(R.string.home_menu_ask_questions));
        listItems.add(getString(R.string.home_menu_events_inc));
        listItems.add(getString(R.string.home_menu_my_profile));
        listItems.add(getString(R.string.home_menu_prefs));
        listItems.add(getString(R.string.home_menu_contact_us));
        listItems.add(getString(R.string.home_menu_about));

        listChoices = (ListView) findViewById(android.R.id.list);
        listChoices.setAdapter(new Custom_Home_Adapter(this, listItems));
        listChoices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch( position )
                {
                    case 0:  Intent garden = new Intent(HomeActivity.this, GardensActivity.class);
                        startActivity(garden);
                        break;
                    case 1:  Intent scan = new Intent(HomeActivity.this, ScanActivity.class);
                        startActivity(scan);
                        break;
                    case 2:  Intent question = new Intent(HomeActivity.this, SettingsActivity.class);
                        startActivity(question);
                        break;
                    case 3:  Intent events = new Intent(HomeActivity.this, SettingsActivity.class);
                        startActivity(events);
                        break;
                    case 4:  Intent profile = new Intent(HomeActivity.this, SettingsActivity.class);
                        startActivity(profile);
                        break;
                    case 5:  Intent settings = new Intent(HomeActivity.this, SettingsActivity.class);
                        startActivity(settings);
                        break;
                    case 6:  Intent contact = new Intent(HomeActivity.this, SettingsActivity.class);
                        startActivity(contact);
                        break;
                    case 7:  Intent about = new Intent(HomeActivity.this, SettingsActivity.class);
                        startActivity(about);
                        break;
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (login)
            getMenuInflater().inflate(R.menu.menu_main_sign_out, menu);
        else
            getMenuInflater().inflate(R.menu.menu_main_sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId())
        {
            case R.id.settings:
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                return true;
            case R.id.sign_in:
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                return true;
            case R.id.sign_out:
                editor.putBoolean("login", false);
                editor.commit();
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
