package com.example.squadzframes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.squadzframes.ui.friends.FriendFragment;
import com.example.squadzframes.ui.home.HomeFragment;

public class DetailsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);

        ImageButton btn = (ImageButton) findViewById(R.id.imageButton8);
        btn.setOnClickListener(new View.OnClickListener() {
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

        ImageButton btttn = (ImageButton) findViewById(R.id.imageButton10);
        btttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goJoin();
            }
        });


    }

    public void goBack(){
        Intent intent = new Intent (DetailsPage.this, OpenEvent.class);
        startActivity(intent);
    }

    public void goView(){
        Intent intent = new Intent (DetailsPage.this, FriendPage.class);
        startActivity(intent);
    }

    public void goJoin(){
        Intent intent = new Intent (DetailsPage.this, HomeFragment.class);
        startActivity(intent);
    }

}