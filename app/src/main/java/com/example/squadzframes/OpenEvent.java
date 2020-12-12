package com.example.squadzframes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.squadzframes.ui.friends.FriendFragment;
import com.example.squadzframes.ui.home.HomeFragment;

public class OpenEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_event);
        ImageButton btn = (ImageButton) findViewById(R.id.imageButton5);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        Button bttn = (Button) findViewById(R.id.button1);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDetails();
            }
        });
    }

    public void goBack(){
        Intent intent = new Intent (OpenEvent.this, FriendFragment.class);
        startActivity(intent);
    }

    public void goDetails(){
        Intent intent = new Intent (OpenEvent.this, DetailsPage.class);
        startActivity(intent);
    }
}