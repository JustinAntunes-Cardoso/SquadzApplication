package com.example.squadzframes.ui.events;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.squadzframes.R;
import com.example.squadzframes.ui.friends.FriendPageActivity;
import com.example.squadzframes.ui.home.HomeFragment;

public class DetailsPage extends AppCompatActivity {

    TextView location,host,time,party;

    String locData,hostData,timeData,partyData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);

        location = findViewById(R.id.location_info);
        host = findViewById(R.id.host_info);
        time = findViewById(R.id.time_info);
        party = findViewById(R.id.party_info);

        getData();
        setData();

        ImageButton backButton = (ImageButton) findViewById(R.id.imageButton8);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        Button bttn = (Button) findViewById(R.id.button2);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goView();
            }
        });

        Button btttn = (Button) findViewById(R.id.join_button);
        btttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goJoin();
            }
        });

    }

    private void getData(){
        if(getIntent().hasExtra("location") && getIntent().hasExtra("host") &&
                getIntent().hasExtra("time") && getIntent().hasExtra("party")){
            locData = getIntent().getStringExtra("location");
            hostData = getIntent().getStringExtra("host");
            timeData = getIntent().getStringExtra("time");
            partyData = getIntent().getStringExtra("party");
        }else{
            Toast.makeText(this, "no data.", Toast.LENGTH_LONG).show();
        }
    }

    private void setData(){
        location.setText(locData);
        host.setText(hostData);
        time.setText(timeData);
        party.setText(partyData);
    }

    public void goBack(){
        Intent intent = new Intent (DetailsPage.this, OpenEvent.class);
        startActivity(intent);
    }

    public void goView(){
        Intent intent = new Intent (DetailsPage.this, FriendPageActivity.class);
        startActivity(intent);
    }

    public void goJoin(){
        Intent intent = new Intent (DetailsPage.this, HomeFragment.class);
        startActivity(intent);
    }

}