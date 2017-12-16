package com.henallux.smartcity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.henallux.smartcity.Model.Garden;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class GardensInformationActivity extends AppCompatActivity {

    ImageView picture;
    TextView nameGarden, note, superficie, adress, descr;
    Button audioGuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gardens_information);

        Bundle bundle = this.getIntent().getExtras();
        Garden garden = (Garden)bundle.getSerializable("garden");

        picture = (ImageView) findViewById(R.id.mainPic);
        Picasso
                .with(this)
                .load("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/Vue_statue_jardin_des_plantes_le_mans.JPG/1200px-Vue_statue_jardin_des_plantes_le_mans.JPG")
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
                if(audioGuid.getBackground()==getDrawable(R.drawable.ic_volume_up_black_24dp)){
                    audioGuid.setBackground(getDrawable(R.drawable.ic_volume_off_black_24dp));
                }
                else{
                    audioGuid.setBackground(getDrawable(R.drawable.ic_volume_up_black_24dp));
                }
            }
        });
    }
}
