package com.henallux.smartcity;

import android.app.usage.UsageEvents;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.henallux.smartcity.DAO.EventDAO;
import com.henallux.smartcity.Model.Event;

import java.io.Serializable;
import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity {

    private String token;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ListView eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        new LoadEvent().execute();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        token = preferences.getString("token", "");
        editor=preferences.edit();

        eventList = (ListView) findViewById(R.id.listEvents);
        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent event = new Intent(EventsActivity.this, EventsInformationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("event", (Serializable)eventList.getItemAtPosition(position));
                event.putExtras(bundle);
                startActivity(event);
            }
        });
    }

    private class LoadEvent extends AsyncTask<String, Void, ArrayList<Event>> {
        @Override
        protected ArrayList<Event> doInBackground(String... strings) {
            EventDAO eventDAO = new EventDAO();
            ArrayList<Event> events = new ArrayList<>();
            try{
                events = eventDAO.getAllEvents();
            }
            catch (Exception e){
                Toast.makeText(EventsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

            return events;
        }

        @Override
        protected void onPostExecute(ArrayList<Event> events) {
            super.onPostExecute(events);
            eventList.setAdapter(new Custom_Events_Adapter(EventsActivity.this, events));
        }
    }

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
            case R.id.settings:
                startActivity(new Intent(EventsActivity.this, SettingsActivity.class));
                return true;
            case R.id.sign_in:
                startActivity(new Intent(EventsActivity.this, LoginActivity.class));
                return true;
            case R.id.sign_out:
                editor.putString("token", "");
                editor.putString("userName", "");
                editor.commit();
                startActivity(new Intent(EventsActivity.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
