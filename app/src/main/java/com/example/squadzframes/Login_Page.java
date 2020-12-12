package com.example.squadzframes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__page);
        Button login_button = findViewById(R.id.login_button);
        Button sign_up_button  = findViewById(R.id.sign_up_button);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(Login_Page.this, MainActivity.class);
                startActivity(send);
            }
        });

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(Login_Page.this, SignUpPage.class);
                startActivity(send);
            }
        });
    }
}