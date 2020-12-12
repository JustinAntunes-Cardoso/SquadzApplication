package com.example.squadzframes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.squadzframes.ui.friends.FriendFragment;

public class FriendPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_page);

        ImageButton btn = (ImageButton) findViewById(R.id.friendBackButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)   { finish();
            }
        });
    }

}