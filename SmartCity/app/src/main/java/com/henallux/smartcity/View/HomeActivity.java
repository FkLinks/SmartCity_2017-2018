package com.henallux.smartcity.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.henallux.smartcity.Model.Custom_Home_Adapter;
import com.henallux.smartcity.R;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private String token;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ConnectivityManager connectivityManager;
    private NetworkInfo activeNetwork;
    private boolean isConnected;
    private ListView listChoices;
    private ArrayList<String> listItems= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        connectivityManager = (ConnectivityManager) HomeActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        token = preferences.getString("token", "");
        editor = preferences.edit();

        addItemsToHomeMenu();

        listChoices = (ListView) findViewById(android.R.id.list);
        listChoices.setAdapter(new Custom_Home_Adapter(this, listItems));
        listChoices.setOnItemClickListener(onItemMenuClickListener);

    }

    private void addItemsToHomeMenu(){
        listItems.add(getString(R.string.home_menu_gardens));
        listItems.add(getString(R.string.home_menu_scan));
        /*listItems.add(getString(R.string.home_menu_ask_questions));*/
        listItems.add(getString(R.string.home_menu_events_inc));
        listItems.add(getString(R.string.home_menu_my_profile));
        listItems.add(getString(R.string.home_menu_contact_us));
        listItems.add(getString(R.string.home_menu_about));
    }

    private AdapterView.OnItemClickListener onItemMenuClickListener = new AdapterView.OnItemClickListener() {
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
                /*not implemented yet*/
                /*case 2:  Intent question = new Intent(HomeActivity.this, ChatActivity.class);
                        startActivity(question);
                    break;*/
                case 2:  Intent events = new Intent(HomeActivity.this, EventsActivity.class);
                        startActivity(events);
                    break;
                case 3:
                    activeNetwork = connectivityManager.getActiveNetworkInfo();
                    isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                    if(isConnected) {
                        if (!token.equals("")) {
                            Intent profile = new Intent(HomeActivity.this, UserProfileActivity.class);
                            startActivity(profile);
                        } else {
                            Toast.makeText(HomeActivity.this, R.string.connection_toast, Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(HomeActivity.this, R.string.connectionMessage, Toast.LENGTH_LONG).show();
                    }
                    break;
                case 4:  Intent contact = new Intent(HomeActivity.this, ContactActivity.class);
                        startActivity(contact);
                    break;
                case 5:  Intent about = new Intent(HomeActivity.this, AboutActivity.class);
                        startActivity(about);
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!token.equals(""))
            getMenuInflater().inflate(R.menu.menu_main_sign_out, menu);
        else
            getMenuInflater().inflate(R.menu.menu_main_sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId())
        {
            case R.id.profile:
                startActivity(new Intent(HomeActivity.this, UserProfileActivity.class));
                return true;
            case R.id.sign_in:
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                return true;
            case R.id.sign_out:
                editor.putString("token", "");
                editor.commit();
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
