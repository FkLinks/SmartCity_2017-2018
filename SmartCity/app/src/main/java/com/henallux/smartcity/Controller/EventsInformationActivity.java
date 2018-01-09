package com.henallux.smartcity.Controller;

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
import android.widget.TextView;
import android.widget.Toast;

import com.henallux.smartcity.Model.Event;
import com.henallux.smartcity.R;

public class EventsInformationActivity extends AppCompatActivity {

    private String token;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ConnectivityManager connectivityManager;
    private NetworkInfo activeNetwork;
    private boolean isConnected;
    private Event event;
    private TextView nameEvent;
    private TextView descrEvent;
    private TextView startEventTime;
    private TextView auEventTimeLabel;
    private TextView endEventTimeLabel;
    private TextView moreInfosEventLabel;
    private TextView websiteEvent;
    String[] DateWOHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_information);

        connectivityManager = (ConnectivityManager) EventsInformationActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        token = preferences.getString("token", "");
        editor=preferences.edit();

        Bundle bundle = this.getIntent().getExtras();
        event = (Event)bundle.getSerializable("event");

        nameEvent = (TextView) findViewById(R.id.nameEvent);
        nameEvent.setText(event.getName());

        descrEvent = (TextView) findViewById(R.id.descrEvent);
        descrEvent.setText(event.getDescription());

        DateWOHour = event.getStartTime().split("T");

        startEventTime = (TextView) findViewById(R.id.startEventTime);
        startEventTime.setText(DateWOHour[0]);

        auEventTimeLabel = (TextView) findViewById(R.id.auEventTimeLabel);
        endEventTimeLabel = (TextView) findViewById(R.id.endEventTimeLabel);
        if(event.getEndTime()!=null){
            DateWOHour = event.getEndTime().split("T");
            endEventTimeLabel.setText(DateWOHour[0]);
        }
        else{
            endEventTimeLabel.setText("...");
        }

        moreInfosEventLabel = (TextView) findViewById(R.id.moreInfosEventLabel);
        websiteEvent = (TextView) findViewById(R.id.websiteEvent);
        if(event.getUrl()!=null){
            websiteEvent.setText(event.getUrl());
        }
        else{
            moreInfosEventLabel.setText("");
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
            case R.id.profile:
                activeNetwork = connectivityManager.getActiveNetworkInfo();
                isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                if(isConnected) {
                    Intent profile = new Intent(EventsInformationActivity.this, UserProfileActivity.class);
                    startActivity(profile);
                }
                else{
                    Toast.makeText(EventsInformationActivity.this, R.string.connectionMessage, Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.sign_in:
                startActivity(new Intent(EventsInformationActivity.this, LoginActivity.class));
                return true;
            case R.id.sign_out:
                editor.putString("token", "");
                editor.commit();
                startActivity(new Intent(EventsInformationActivity.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
