package com.example.squadzframes.ui.events;


import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.squadzframes.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LocateEventMap extends FragmentActivity implements OnMapReadyCallback {
    Activity context;
    GoogleMap map;
    SupportMapFragment mapFragment;
    SearchView searchView;
    //Strings from database
    String temp, hum, time, loc, lat,lon;
    Double latitudeNum, longitudeNum;

    //Waypoints from database
    private DatabaseReference mWeatherDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locateeventmap);
        context = LocateEventMap.this;
        Button  detailsBtn = (Button) findViewById(R.id.detailsBtn);
        detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchView searchView =findViewById(R.id.sv_location);
                Intent intent=new Intent(context, CreateEvent.class);
                intent.putExtra("location",searchView.getQuery().toString());
                startActivity(intent);
            }
        });

        searchView = findViewById(R.id.sv_location);
        mapFragment =(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if(location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(LocateEventMap.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        map.addMarker(new MarkerOptions().position(latLng).title(location));


                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        //Database reference
        mWeatherDatabase = FirebaseDatabase.getInstance().getReference().child("Weather");
        //Waypoints from database
        mWeatherDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    hum = postSnapshot.child("Humidity").getValue().toString();
                    loc = postSnapshot.child("location").getValue().toString();
                    temp = postSnapshot.child("Temperature").getValue().toString();
                    time = postSnapshot.child("Time").getValue().toString();
                    lat = postSnapshot.child("Latitude").getValue().toString();
                    lon = postSnapshot.child("Longitude").getValue().toString();

                    String weather = "Temp: " + temp + "\t Humidity: " + hum;

                    latitudeNum = Double.parseDouble(lat);
                    longitudeNum = Double.parseDouble(lon);

                    LatLng[] coordinates = new LatLng[] {new LatLng(latitudeNum,longitudeNum)};
                    map = googleMap;
                    map.addMarker(new MarkerOptions().position(coordinates[0]).title(loc).snippet(weather));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates[0], 10));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}