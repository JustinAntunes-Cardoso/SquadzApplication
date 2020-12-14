package com.example.squadzframes.ui.events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.squadzframes.MainActivity;
import com.example.squadzframes.R;
import com.example.squadzframes.model.Event;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateEvent extends AppCompatActivity {

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    String choiceP;
    String choiceG;
    TextView setTime,setLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        databaseReference = database.getReference("Event Details");
        Spinner partySizeSpin = (Spinner) findViewById(R.id.spinner_party);
        Spinner gameplaySpin = (Spinner) findViewById(R.id.spinner_gameplay);

        partySizeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                choiceP = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(CreateEvent.this, choiceP + " selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        gameplaySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                choiceG = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(CreateEvent.this, choiceG + " selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        setTime = (TextView) findViewById(R.id.set_time);
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
                popupActivity pop = new popupActivity();
                pop.show(manager,null);
            }
        });

        setLocation = (TextView) findViewById(R.id.set_location);
        setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });

        ImageButton btn = (ImageButton) findViewById(R.id.back_arrow);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageButton createButton = (ImageButton) findViewById(R.id.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This is where the database goes,
                sendData();
                goHome();
            }
        });
    }

    private void sendData() {
        String setTimeText= setTime.getText().toString();
        String setHostText = "Justin";
        String setLocationText = setLocation.getText().toString();
        String id = databaseReference.push().getKey();

        if(!TextUtils.isEmpty(setTimeText)){
            Event data = new Event(id,setLocationText,setHostText,setTimeText,choiceP,choiceG);

            databaseReference.child(id).setValue(data);

            Toast.makeText(this,"Data has been saved..",Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this,"Please Enter the time.",Toast.LENGTH_LONG).show();
        }
    }

    void setDate(String date){
        setTime = (TextView) findViewById(R.id.set_time);
        setTime.setText(date);
    }

    public void goHome(){
        Intent intent = new Intent (CreateEvent.this, MainActivity.class);
        startActivity(intent);
    }
}