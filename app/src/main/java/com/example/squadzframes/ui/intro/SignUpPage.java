package com.example.squadzframes.ui.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.squadzframes.ui.home.HomeFragment;

import com.example.squadzframes.MainActivity;
import com.example.squadzframes.R;

public class SignUpPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        Button btn = (Button) findViewById(R.id.sign_Up_Button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });
    }
    public void goHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}