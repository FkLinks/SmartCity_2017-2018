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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.henallux.smartcity.Model.Plant;
import com.henallux.smartcity.R;
import com.squareup.picasso.Picasso;

public class PlantInformationActivity extends AppCompatActivity {

    private Boolean login;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ConnectivityManager connectivityManager;
    private NetworkInfo activeNetwork;
    private boolean isConnected;
    ImageView picture;
    TextView namePlant, descr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_information);

        connectivityManager = (ConnectivityManager) PlantInformationActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        login = preferences.getBoolean("login", false);
        editor=preferences.edit();

        Bundle bundle = this.getIntent().getExtras();
        Plant plant = (Plant)bundle.getSerializable("plant");

        picture = (ImageView) findViewById(R.id.picturePlant);
        Picasso
                .with(this)
                .load("http://res.cloudinary.com/vnckcloud/image/upload/v1513530594/Graines-de-tournesol_nzp4gx.jpg")
                .into(picture);

        namePlant = (TextView) findViewById(R.id.namePlant);
        namePlant.setText(plant.getName());

        descr = (TextView) findViewById(R.id.descriptionPlant);
        descr.setText(plant.getDescription());
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
            case R.id.profile:
                activeNetwork = connectivityManager.getActiveNetworkInfo();
                isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                if(isConnected) {
                    Intent profile = new Intent(PlantInformationActivity.this, UserProfileActivity.class);
                    startActivity(profile);
                }
                else{
                    Toast.makeText(PlantInformationActivity.this, R.string.connectionMessage, Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.sign_in:
                startActivity(new Intent(PlantInformationActivity.this, LoginActivity.class));
                return true;
            case R.id.sign_out:
                editor.putString("token", "");
                editor.commit();
                startActivity(new Intent(PlantInformationActivity.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
