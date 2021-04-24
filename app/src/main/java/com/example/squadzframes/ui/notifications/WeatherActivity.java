package com.example.squadzframes.ui.notifications;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.squadzframes.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherActivity extends AppCompatActivity {

    private DatabaseReference mWeatherDatabase;
    private TextView location, temperature, humidity, timeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        final String machineName = getIntent().getStringExtra("machine_name");

        location = (TextView) findViewById(R.id.locationPlaceHolder);
        temperature = (TextView) findViewById(R.id.temperaturePlaceHolder);
        humidity = (TextView) findViewById(R.id.humidityPlaceHolder);
        timeStamp = (TextView) findViewById(R.id.timePlaceHolder);
        ImageView locationView = findViewById(R.id.courtImage);



        mWeatherDatabase = FirebaseDatabase.getInstance().getReference().child("Weather");
        mWeatherDatabase.child(machineName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String hum = snapshot.child("Humidity").getValue().toString();
                String loc = snapshot.child("location").getValue().toString();
                String temp = snapshot.child("Temperature").getValue().toString();
               //String time = snapshot.child("Time").getValue().toString();
                String image = snapshot.child("background").getValue().toString();

                //Set time stamp when the data changes.
                SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = s.format(new Date());

                location.setText(loc);
                humidity.setText(hum);
                temperature.setText(temp);
                timeStamp.setText(time);
                Picasso.get().load(image).placeholder(R.drawable.default_avatar).into(locationView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }




}