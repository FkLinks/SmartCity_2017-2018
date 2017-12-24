package com.henallux.smartcity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.henallux.smartcity.DAO.GardenDAO;
import com.henallux.smartcity.Model.Garden;

import java.io.Serializable;
import java.util.ArrayList;
//cle map : AIzaSyD_nlRp5JKykNjDJ2YyZgkO5fwhKjJZjoU
//https://www.youtube.com/watch?time_continue=3&v=uOKLUu1Jjco
public class GardensActivity extends AppCompatActivity implements LocationListener{
    private static final int PERMS_CALL_ID = 1234;

    private TabHost tabHost;
    private TabHost.TabSpec spec;
    private String token;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ListView gardenList;
    private ArrayList<Garden> listItems= new ArrayList<>();
    private MapFragment mapFragment;
    private GoogleMap googleMap;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gardens);

        new LoadGarden().execute();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        token = preferences.getString("token", "");
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

        gardenList = (ListView) findViewById(R.id.listGardens);
        gardenList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent garden = new Intent(GardensActivity.this, GardensInformationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("garden", (Serializable)gardenList.getItemAtPosition(position));
                garden.putExtras(bundle);
                startActivity(garden);
            }
        });

        FragmentManager fragmentManager = getFragmentManager();
        mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void verifPermission()
    {
        //Sert à vérifier si on bénéficie bien des permissions de localisation !
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, PERMS_CALL_ID);
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

        loadMap();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMS_CALL_ID){
            verifPermission();
        }
    }

    @SuppressWarnings("MissingPermission")
    private void loadMap()
    {
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                GardensActivity.this.googleMap = googleMap;
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(50.463335, 4.881568), 10.0f));
                googleMap.setMyLocationEnabled(true);
                for (Garden garden : listItems) {
                    String[] latLong = garden.getGeographicalCoordinates().split(",");

                    //iconGoToMarker = getDrawable(R.drawable.arrow);
                    googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latLong[0]), Double.parseDouble(latLong[1])))
                            .title(garden.getName()));

                    googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            Intent gardenInfos = new Intent(GardensActivity.this, GardensInformationActivity.class);
                            Bundle bundle = new Bundle();
                            for(Garden garden : listItems) {
                                if(marker.getTitle().equals(garden.getName())){
                                    bundle.putSerializable("garden", (Serializable)garden);
                                }
                            }
                            gardenInfos.putExtras(bundle);
                            startActivity(gardenInfos);
                        }
                    });

                }
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
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        /*remet l'ecran sur l'emplacement*/
        /*if(googleMap != null)
        {
            LatLng googleLocation = new LatLng(latitude, longitude);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(googleLocation));
        }*/
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

    private class LoadGarden extends AsyncTask<String, Void, ArrayList<Garden>>{
        @Override
        protected ArrayList<Garden> doInBackground(String... strings) {
            GardenDAO gardenDAO = new GardenDAO();
            ArrayList<Garden> gardens = new ArrayList<>();
            try{
                gardens = gardenDAO.getAllGardens();
                for(Garden garden:gardens){
                    listItems.add(garden);
                }
            }
            catch (Exception e){
                Toast.makeText(GardensActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

            return gardens;
        }

        @Override
        protected void onPostExecute(ArrayList<Garden> gardens) {
            super.onPostExecute(gardens);
            gardenList.setAdapter(new Custom_Gardens_Adapter(GardensActivity.this, listItems));
            verifPermission();
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
                startActivity(new Intent(GardensActivity.this, SettingsActivity.class));
                return true;
            case R.id.sign_in:
                startActivity(new Intent(GardensActivity.this, LoginActivity.class));
                return true;
            case R.id.sign_out:
                editor.putString("token", "");
                editor.putString("userName", "");
                editor.commit();
                startActivity(new Intent(GardensActivity.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
