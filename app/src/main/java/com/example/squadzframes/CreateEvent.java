package com.example.squadzframes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.squadzframes.ui.events.EventsFragment;
import com.example.squadzframes.ui.friends.FriendFragment;
import com.example.squadzframes.ui.home.HomeFragment;

public class CreateEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        ImageButton btn = (ImageButton) findViewById(R.id.imageButton5);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageButton bttn = (ImageButton) findViewById(R.id.imageButton4);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });
    }

    public void goHome(){
        Intent intent = new Intent (CreateEvent.this, MainActivity.class);
        startActivity(intent);
    }
}