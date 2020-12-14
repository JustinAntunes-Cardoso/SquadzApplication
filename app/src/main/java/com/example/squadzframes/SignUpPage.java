package com.example.squadzframes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.squadzframes.model.UserInformation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//import com.example.firebaseapp.model.UserInformation;
//import com.example.squadzframes.firebase.realTimeDatabase.RealtimeDatabaseManager;


public class SignUpPage extends AppCompatActivity {
    EditText username, fullName, email, password, date;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        databaseReference = database.getReference("User Information");

        username = findViewById(R.id.editTextUsername);
        fullName = findViewById(R.id.editTextPersonName);
        email = findViewById(R.id.editTextEmailAddress);
        password = findViewById(R.id.editTextPassword);
        date = findViewById(R.id.editTextDate);


        Button btn = (Button) findViewById(R.id.sign_Up_Button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
                goHome();
            }
        });
    }

    private void sendData() {
        String sUsername, sFullname, sEmail, sPassword, sDate;

        sUsername = username.getText().toString();
        sFullname = fullName.getText().toString();
        sEmail = email.getText().toString();
        sPassword = password.getText().toString();
        sDate = date.getText().toString();

        String id = databaseReference.push().getKey();

        UserInformation personInfo = new UserInformation(sUsername, sFullname, sEmail, sPassword, sDate);

        databaseReference.child(id).setValue(personInfo);

    }

    public void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}