package com.example.squadzframes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.squadzframes.ui.events.EventsFragment;
import com.example.squadzframes.ui.friends.FriendFragment;
import com.example.squadzframes.ui.home.HomeFragment;

public class LocateEvent extends AppCompatActivity {

    private ImageButton bttn;
    private ImageButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_event);

        ImageButton btn = (ImageButton) findViewById(R.id.imageButton6);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageButton bttn = (ImageButton) findViewById(R.id.imageButton7);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goOpenEvents();
            }
        });
    }

    public void goOpenEvents(){
        Intent intent = new Intent (LocateEvent.this, OpenEvent.class);
        startActivity(intent);
    }
}