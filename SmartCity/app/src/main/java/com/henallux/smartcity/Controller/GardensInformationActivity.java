package com.henallux.smartcity.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.henallux.smartcity.Model.Garden;
import com.henallux.smartcity.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class GardensInformationActivity extends AppCompatActivity {

    private String token;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ConnectivityManager connectivityManager;
    private NetworkInfo activeNetwork;
    private boolean isConnected;
    private ImageView picture;
    private TextView nameGarden, note, superficie, adress, descr;
    private Button audioGuid;
    private MediaPlayer mPlayer = new MediaPlayer();
    private AudioAttributes audioAttributes;
    private Garden garden;
    private int currentPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gardens_information);

        connectivityManager = (ConnectivityManager) GardensInformationActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        token = preferences.getString("token", "");
        editor=preferences.edit();

        Bundle bundle = this.getIntent().getExtras();
        garden = (Garden)bundle.getSerializable("garden");

        picture = (ImageView) findViewById(R.id.mainPic);
        Picasso
                .with(this)
                .load(garden.getUrlImg())//"https://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/Vue_statue_jardin_des_plantes_le_mans.JPG/1200px-Vue_statue_jardin_des_plantes_le_mans.JPG")
                .into(picture);

        nameGarden = (TextView) findViewById(R.id.nameGarden);
        nameGarden.setText(garden.getName());

        note = (TextView) findViewById(R.id.note);
        note.setText(""+garden.getNote());

        superficie = (TextView) findViewById(R.id.superficie);
        superficie.setText(""+garden.getSuperficie());

        adress = (TextView) findViewById(R.id.adress);
        adress.setText(garden.getStreet()+", "+garden.getNumStreet());

        descr = (TextView) findViewById(R.id.descr);
        descr.setText(garden.getDescription());

        audioGuid = (Button) findViewById(R.id.audioGuidBtn);
        audioGuid.setOnClickListener(launchStopAudioClickListener);
        String url = garden.getUrlAudio();
        try {
            mPlayer.setDataSource(url);
            mPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener launchStopAudioClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mPlayer.isPlaying()){
                audioGuid.setForeground(getDrawable(R.drawable.ic_volume_off_black_24dp));
                currentPos = mPlayer.getCurrentPosition();
                mPlayer.pause();
            }
            else{
                audioGuid.setForeground(getDrawable(R.drawable.ic_volume_up_black_24dp));

                mPlayer.seekTo(currentPos);
                mPlayer.start();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if(currentPos!=0) {
            mPlayer.seekTo(currentPos);
            mPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentPos = mPlayer.getCurrentPosition();
        mPlayer.pause();
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
                    Intent profile = new Intent(GardensInformationActivity.this, UserProfileActivity.class);
                    startActivity(profile);
                }
                else{
                    Toast.makeText(GardensInformationActivity.this, R.string.connectionMessage, Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.sign_in:
                startActivity(new Intent(GardensInformationActivity.this, LoginActivity.class));
                return true;
            case R.id.sign_out:
                editor.putString("token", "");
                editor.commit();
                startActivity(new Intent(GardensInformationActivity.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
