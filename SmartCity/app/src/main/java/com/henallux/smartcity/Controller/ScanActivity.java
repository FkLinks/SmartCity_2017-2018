package com.henallux.smartcity.Controller;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

/*import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;*/

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.henallux.smartcity.DAO.PlantDAO;
import com.henallux.smartcity.DAO.PlantJSONDAO;
import com.henallux.smartcity.Model.Plant;
import com.henallux.smartcity.R;

import org.json.JSONException;

import java.io.IOException;

public class ScanActivity extends AppCompatActivity {

    private String token;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ConnectivityManager connectivityManager;
    private NetworkInfo activeNetwork;
    private boolean isConnected;
    private SurfaceView cameraPreview;
    private TextView txtView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    //to avoid spamming scan of a qr code
    private boolean hasScanned = false;
    final int RequestCameraPermissionID = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.henallux.smartcity.R.layout.activity_scan);

        connectivityManager = (ConnectivityManager) ScanActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        token = preferences.getString("token", "");
        editor=preferences.edit();

        cameraPreview = (SurfaceView) findViewById(R.id.scanner);
        txtView = (TextView) findViewById(R.id.explanationQRDestination);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();

        //Add event
        cameraPreview.getHolder().addCallback(cameraCallback);
        barcodeDetector.setProcessor(scanDetector);
    }

    private SurfaceHolder.Callback cameraCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //Request permission
                ActivityCompat.requestPermissions(ScanActivity.this,
                        new String[]{Manifest.permission.CAMERA}, RequestCameraPermissionID);
                return;
            }
            try {
                cameraSource.start(cameraPreview.getHolder());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            cameraSource.stop();
            hasScanned=false;
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        Toast.makeText(ScanActivity.this, R.string.error_camera, Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
        }
    }

    private Detector.Processor<Barcode> scanDetector = new Detector.Processor<Barcode>() {
        @Override
        public void release() {

        }

        //TO DO ON SCAN ITEM
        @Override
        public void receiveDetections(Detector.Detections<Barcode> detections) {
            final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
            if(qrcodes.size() != 0 && !hasScanned){
                txtView.post(new Runnable(){
                    @Override
                    public void run(){
                        PlantDAO plantDAO = new PlantJSONDAO();

                        //Create vibrate
                        Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(500);

                        try {
                            Intent plantInfo = new Intent(ScanActivity.this, PlantInformationActivity.class);
                            Plant plant = plantDAO.getPlantInfos(qrcodes.valueAt(0).displayValue);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("plant", plant);
                            plantInfo.putExtras(bundle);
                            startActivity(plantInfo);
                        }
                        catch (JSONException e) {
                            Toast.makeText(ScanActivity.this, R.string.json_exception_encountered, Toast.LENGTH_LONG).show();
                        }
                    }
                });
                hasScanned = true;
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
                activeNetwork = connectivityManager.getActiveNetworkInfo();
                isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                if(isConnected) {
                    Intent profile = new Intent(ScanActivity.this, UserProfileActivity.class);
                    startActivity(profile);
                }
                else{
                    Toast.makeText(ScanActivity.this, R.string.connectionMessage, Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.sign_in:
                startActivity(new Intent(ScanActivity.this, LoginActivity.class));
                return true;
            case R.id.sign_out:
                editor.putString("token", "");
                editor.commit();
                startActivity(new Intent(ScanActivity.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
