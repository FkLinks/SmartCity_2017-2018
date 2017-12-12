package com.henallux.smartcity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.henallux.smartcity.Model.Plant;
import com.squareup.picasso.Picasso;

public class PlantInformationActivity extends AppCompatActivity {

    ImageView picture;
    TextView namePlant, descr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_information);

        Bundle bundle = this.getIntent().getExtras();
        Plant plant = (Plant)bundle.getSerializable("plant");

        picture = (ImageView) findViewById(R.id.picturePlant);
        Picasso
                .with(this)
                .load("https://upload.wikimedia.org/wikipedia/commons/thumb/c/c7/Blue_rose-artificially_coloured.jpg/1200px-Blue_rose-artificially_coloured.jpg")
                .into(picture);

        namePlant = (TextView) findViewById(R.id.namePlant);
        namePlant.setText(plant.getName());

        descr = (TextView) findViewById(R.id.descriptionPlant);
        descr.setText(plant.getDescription());
    }
}
