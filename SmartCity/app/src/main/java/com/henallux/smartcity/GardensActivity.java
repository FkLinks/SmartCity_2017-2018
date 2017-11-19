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
import android.widget.TabHost;

import java.util.ArrayList;

public class GardensActivity extends AppCompatActivity {
    private TabHost tabHost;
    private TabHost.TabSpec spec;
    private Boolean login;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ListView listChoices;
    private ArrayList<String> listItems= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gardens);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        login = preferences.getBoolean("login", false);
        editor=preferences.edit();

        tabHost = (TabHost)findViewById(R.id.showGardensTabHost);
        tabHost.setup();

        //FirstTab - Listing of gardens
        spec = tabHost.newTabSpec("Listing");
        spec.setContent(R.id.tabListingGarden);
        spec.setIndicator("Listing");
        tabHost.addTab(spec);

        //SecondTab - Mapping of gardens
        spec = tabHost.newTabSpec("Mapping");
        spec.setContent(R.id.tabMappingGarden);
        spec.setIndicator("Mapping");
        tabHost.addTab(spec);


        listItems.add("Jean Chalon");
        listItems.add("Jardin 2");
        listItems.add("Jardin 3");
        listItems.add("Jardin 4");
        listItems.add("Jardin 5");

        listChoices = (ListView) findViewById(android.R.id.list);
        listChoices.setAdapter(new Custom_Home_Adapter(this, listItems));
        listChoices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*switch( position )
                {
                    case 0:  Intent garden = new Intent(GardensActivity.this, GardensActivity.class);
                        startActivity(garden);
                        break;
                    case 1:  Intent scan = new Intent(GardensActivity.this, SettingsActivity.class);
                        startActivity(scan);
                        break;
                    case 2:  Intent question = new Intent(GardensActivity.this, SettingsActivity.class);
                        startActivity(question);
                        break;
                    case 3:  Intent events = new Intent(GardensActivity.this, SettingsActivity.class);
                        startActivity(events);
                        break;
                    case 4:  Intent profile = new Intent(GardensActivity.this, SettingsActivity.class);
                        startActivity(profile);
                        break;
                }*/
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
                startActivity(new Intent(GardensActivity.this, SettingsActivity.class));
                return true;
            case R.id.sign_in:
                startActivity(new Intent(GardensActivity.this, LoginActivity.class));
                return true;
            case R.id.sign_out:
                editor.putBoolean("login", false);
                editor.commit();
                startActivity(new Intent(GardensActivity.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
