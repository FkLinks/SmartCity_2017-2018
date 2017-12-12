package com.henallux.smartcity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.henallux.smartcity.Model.Garden;

public class GardensInformationActivity extends AppCompatActivity {

    ImageView picture;
    TextView nameGarden, note, superficie, adress, descr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gardens_information);

        Bundle bundle = this.getIntent().getExtras();
        Garden garden = (Garden)bundle.getSerializable("garden");

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
    }
}
