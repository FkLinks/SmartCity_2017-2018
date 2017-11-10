package com.henallux.smartcity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private Boolean login;

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

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        login = preferences.getBoolean("login", false);

        ListView listChoices = (ListView) findViewById(android.R.id.list);
        /*listChoices.setAdapter(new ListAdapter() {            C nul
            @Override
            public boolean areAllItemsEnabled() {
                return true;
            }

            @Override
            public boolean isEnabled(int i) {
                return true;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver dataSetObserver) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

            }

            @Override
            public int getCount() {
                return listItems.length;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                view=getLayoutInflater().inflate(R.layout.activity_home,null);
                ImageView imageView=(ImageView)view.findViewById(R.id.arrow);
                TextView textView=(TextView)view.findViewById(R.id.txtChoixListView);
                imageView.setImageResource(R.drawable.arrow);
                textView.setText(listItems[i]);
                return null;
            }

            @Override
            public int getItemViewType(int i) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return listItems.length;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        });*/
        listChoices.setAdapter(new ArrayAdapter<>(this, R.layout.activity_home, R.id.txtChoixListView, listItems));
        listChoices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch( position )
                {
                    case 0:  Intent garden = new Intent(HomeActivity.this, ShowGardensActivity.class);
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
