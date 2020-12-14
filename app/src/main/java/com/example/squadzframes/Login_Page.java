package com.example.squadzframes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class Login_Page extends AppCompatActivity {

    private EditText username, password;
    private Switch switch1;

    private String textUser, textPass;
    private boolean switchOnOff;
    public static final String SHARED_PREFs = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String TEXTPASS = "textPass";
    public static final String SWITCH1 = "switch1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__page);
        Button login_button = findViewById(R.id.login_button);
        Button sign_up_button = findViewById(R.id.sign_up_button);

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        if (checkbox.equals("true")) {
            Intent send = new Intent(Login_Page.this, MainActivity.class);
            startActivity(send);
        } else {
            Toast.makeText(Login_Page.this, "Please Sign in", Toast.LENGTH_SHORT).show();
        }

        //initialize the views that we want to keep
        username = (EditText) findViewById(R.id.editUserName);
        password = (EditText) findViewById(R.id.loginPassword);
        switch1 = (Switch) findViewById(R.id.switch1);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(Login_Page.this, MainActivity.class);

                // username.setText(username.getText().toString());
                //password.setText((password.getText().toString()));
                // loadData();
                //updateViews();
                startActivity(send);
            }
        });

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                    Toast.makeText(Login_Page.this, "Checked", Toast.LENGTH_SHORT).show();
                } else if (!buttonView.isChecked()) {
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                    Toast.makeText(Login_Page.this, "Unchecked", Toast.LENGTH_SHORT).show();

                }
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

//    public void saveData(){
//        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFs,MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(TEXT,username.getText().toString());
//        editor.putString(TEXTPASS,password.getText().toString());
//        editor.putBoolean(SWITCH1,switch1.isChecked());
//
//        editor.apply();
//        Toast.makeText(this,"data saved",Toast.LENGTH_SHORT).show();
//    }
//
//    public void loadData(){
//        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFs,MODE_PRIVATE);
//        textUser = sharedPreferences.getString(TEXT,"");
//        textPass = sharedPreferences.getString(TEXTPASS,"");
//        switchOnOff = sharedPreferences.getBoolean(SWITCH1,false);
//    }
//
//    public void updateViews(){
//     username.setText(textUser);
//     password.setText(textPass);
//    }
}