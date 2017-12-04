package com.henallux.smartcity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class GardensActivity extends AppCompatActivity implements LocationListener {
    private TabHost tabHost;
    private TabHost.TabSpec spec;
    private Boolean login;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ListView listChoices;
    private ArrayList<String> listItems= new ArrayList<>();
    private MapView mapView;
    private GoogleMap googleMap;
    private LocationManager locationManager;

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

        mapView = (MapView) findViewById(R.id.map);
    }

    @Override
    protected void onResume() {
        super.onResume();
        verifPermission();
        loadMap();
    }

    private void verifPermission()
    {
        //Sert à vérifier si on bénéficie bien des permissions de localisation !

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
        }
        if(locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER))
        {
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 10000,0,this);
        }
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
        {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000,0,this);
        }
    }

    @SuppressWarnings("MissingPermission")
    private void loadMap()
    {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                GardensActivity.this.googleMap = googleMap;
                googleMap.moveCamera(CameraUpdateFactory.zoomBy(15));
                googleMap.setMyLocationEnabled(true);
                googleMap.addMarker(new MarkerOptions().position(new LatLng(78.36436, 25.95733)).title("Premier Point"));
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(locationManager != null)
        {
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        double latitude = 50;
        double longitude = 2;

        Toast.makeText(this, latitude+" / "+ longitude, Toast.LENGTH_LONG).show();
        if(googleMap != null)
        {
            LatLng googleLocation = new LatLng(latitude, longitude);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(googleLocation));
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

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
