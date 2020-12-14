package com.example.squadzframes.ui.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.squadzframes.R;

public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        Button get_started_button = findViewById(R.id.get_started_button);
        TextView haveAccount = findViewById(R.id.haveAnAccount);
        get_started_button.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {
                                                      Intent send = new Intent(StartScreen.this, intro_1_1.class);
                                                      startActivity(send);
                                                  }
                                              }

        );
        String path = "android.resource://"+getPackageName()+"/"+R.drawable.arvie;

        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Toast.makeText(getBaseContext(), path +" arvie" , Toast.LENGTH_SHORT ).show();
                Intent intent = new Intent(StartScreen.this, Login_Page.class);
                startActivity(intent);
            }
        });
    }
}