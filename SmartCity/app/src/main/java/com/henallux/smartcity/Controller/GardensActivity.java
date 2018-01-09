package com.henallux.smartcity.Controller;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.henallux.smartcity.DAO.GardenDAO;
import com.henallux.smartcity.DAO.GardenJSONDAO;
import com.henallux.smartcity.Exceptions.GetAllGardensException;
import com.henallux.smartcity.Model.Custom_Gardens_Adapter;
import com.henallux.smartcity.Model.Garden;
import com.henallux.smartcity.R;

import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
//cle map : AIzaSyD_nlRp5JKykNjDJ2YyZgkO5fwhKjJZjoU
public class GardensActivity extends AppCompatActivity implements LocationListener{
    private static final int PERMS_CALL_ID = 1234;

    private TabHost tabHost;
    private TabHost.TabSpec spec;
    private String token;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ConnectivityManager connectivityManager;
    private NetworkInfo activeNetwork;
    private boolean isConnected;
    private ListView gardenList;
    private ArrayList<Garden> listItems= new ArrayList<>();
    private MapFragment mapFragment;
    private GoogleMap googleMap;
    private LocationManager locationManager;
    private TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gardens);

        connectivityManager = (ConnectivityManager) GardensActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        errorMessage = (TextView) findViewById(R.id.errorMessageGarden);

        activeNetwork = connectivityManager.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(isConnected) {
            new LoadGarden().execute();
        }
        else{
            Toast.makeText(GardensActivity.this, R.string.errorMissInternetCo, Toast.LENGTH_LONG).show();
            errorMessage.setText(R.string.connectionMessage);
        }

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
        gardenList.setOnItemClickListener(goToInformationPage);

        FragmentManager fragmentManager = getFragmentManager();
        mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);
    }

    private AdapterView.OnItemClickListener goToInformationPage = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent garden = new Intent(GardensActivity.this, GardensInformationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("garden", (Serializable)gardenList.getItemAtPosition(position));
            garden.putExtras(bundle);
            startActivity(garden);
        }
    };

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
                    String[] latLong = garden.getGeographicalCoordinate().split(",");

                    googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latLong[0]), Double.parseDouble(latLong[1])))
                            .title(garden.getName()));
                }

                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Intent gardenInfos = new Intent(GardensActivity.this, GardensInformationActivity.class);
                        Bundle bundle = new Bundle();
                        for(Garden garden : listItems) {
                            if(marker.getTitle().equals(garden.getName())){
                                bundle.putSerializable("garden", garden);
                                break;
                            }
                        }
                        gardenInfos.putExtras(bundle);
                        startActivity(gardenInfos);
                    }
                });
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
    private class LoadGarden extends AsyncTask<Void, Void, ArrayList<Garden>>{
        private String error = "";

        @Override
        protected ArrayList<Garden> doInBackground(Void... strings) {
            GardenDAO gardenDAO = new GardenJSONDAO();
            ArrayList<Garden> gardens = new ArrayList<>();

            try{
                gardens = gardenDAO.getAllGardens();
            }
            catch (GetAllGardensException e){
                error = getString(R.string.error_get_all_gardens_exception);
            }
            catch (JSONException e){
                error = getString(R.string.json_exception_encountered);
            }

            return gardens;
        }

        @Override
        protected void onPostExecute(ArrayList<Garden> gardens) {
            super.onPostExecute(gardens);
            if(error.equals("")) {
                gardenList.setAdapter(new Custom_Gardens_Adapter(GardensActivity.this, gardens));
                listItems = gardens;
                verifPermission();
            }
            else{
                Toast.makeText(GardensActivity.this, error, Toast.LENGTH_LONG).show();
            }
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
                    Intent profile = new Intent(GardensActivity.this, UserProfileActivity.class);
                    startActivity(profile);
                }
                else{
                    Toast.makeText(GardensActivity.this, R.string.connectionMessage, Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.sign_in:
                startActivity(new Intent(GardensActivity.this, LoginActivity.class));
                return true;
            case R.id.sign_out:
                editor.putString("token", "");
                editor.commit();
                startActivity(new Intent(GardensActivity.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
