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
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private Boolean login;
    private ListView listChoices;

    private String[] listItems={
            getString(R.string.home_menu_gardens),
            getString(R.string.home_menu_scan),
            getString(R.string.home_menu_ask_questions),
            getString(R.string.home_menu_events_inc),
            getString(R.string.home_menu_my_profile),
            getString(R.string.home_menu_prefs),
            getString(R.string.home_menu_contact_us),
            getString(R.string.home_menu_about)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        login = preferences.getBoolean("login", false);

        listChoices = (ListView) findViewById(R.id.list);
        listChoices.setAdapter(new ArrayAdapter<>(this, R.layout.activity_home, R.id.text1, listItems));
        listChoices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch( position )
                {
                    case 0:  Intent garden = new Intent(HomeActivity.this, SettingsActivity.class);
                        startActivity(garden);
                        break;
                    case 1:  Intent scan = new Intent(HomeActivity.this, SettingsActivity.class);
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
                //startActivity(new Intent(AcceuilActivity.this, MessagerieActivity.class));
                return true;
            case R.id.sign_out:
                //startActivity(new Intent(AcceuilActivity.this, VisiteActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
