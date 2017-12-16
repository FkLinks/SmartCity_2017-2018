package com.henallux.smartcity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.games.Player;
import com.henallux.smartcity.Model.Garden;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static android.media.AudioAttributes.USAGE_MEDIA;

public class GardensInformationActivity extends AppCompatActivity {

    private Boolean login;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    ImageView picture;
    TextView nameGarden, note, superficie, adress, descr;
    Button audioGuid;
    MediaPlayer mPlayer = new MediaPlayer();
    AudioAttributes audioAttributes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gardens_information);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        login = preferences.getBoolean("login", false);
        editor=preferences.edit();

        Bundle bundle = this.getIntent().getExtras();
        final Garden garden = (Garden)bundle.getSerializable("garden");

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
        superficie.setText(""+garden.getSurfaceArea());

        adress = (TextView) findViewById(R.id.adress);
        adress.setText(garden.getStreet()+", "+garden.getNumStreet());

        descr = (TextView) findViewById(R.id.descr);
        descr.setText(garden.getDescription());

        audioGuid = (Button) findViewById(R.id.audioGuidBtn);
        audioGuid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(audioGuid.getBackground().equals(getDrawable(R.drawable.ic_volume_up_black_24dp))){
                    audioGuid.setBackground(getDrawable(R.drawable.ic_volume_off_black_24dp));
                    mPlayer.stop();
                }
                else{
                    audioGuid.setBackground(getDrawable(R.drawable.ic_volume_up_black_24dp));

                    //String url = "http://res.cloudinary.com/vnckcloud/video/upload/v1513403728/Blankets_-_The_hanging_tree_xfuze6.mp3";
                    String url = garden.getUrlAudio();
                    //mPlayer.setAudioAttributes(audioAttributes);
                    try {
                        mPlayer.setDataSource(url);
                        mPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mPlayer.start();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.stop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPlayer.stop();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        mPlayer.stop();
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
                startActivity(new Intent(GardensInformationActivity.this, SettingsActivity.class));
                return true;
            case R.id.sign_in:
                startActivity(new Intent(GardensInformationActivity.this, LoginActivity.class));
                return true;
            case R.id.sign_out:
                editor.putBoolean("login", false);
                editor.commit();
                startActivity(new Intent(GardensInformationActivity.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
