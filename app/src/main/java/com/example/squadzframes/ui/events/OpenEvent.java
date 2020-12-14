package com.example.squadzframes.ui.events;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.squadzframes.R;
import com.example.squadzframes.model.Event;
import com.example.squadzframes.ui.friends.FriendFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OpenEvent extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    List<Event> eventData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_event);
        databaseReference = database.getReference("Event Details");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventData = new ArrayList<>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    Event data = ds.getValue(Event.class);
                    eventData.add(data);
                }
                EventRecyclerViewAdapter myAdapter = new EventRecyclerViewAdapter(OpenEvent.this,eventData);
                recyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        ImageButton btn = (ImageButton) findViewById(R.id.back_arrow_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        Button bttn = (Button) findViewById(R.id.button1);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDetails();
            }
        });
    }

    public void goBack(){
        Intent intent = new Intent (OpenEvent.this, FriendFragment.class);
        startActivity(intent);
    }

    public void goDetails(){
        Intent intent = new Intent (OpenEvent.this, DetailsPage.class);
        startActivity(intent);
    }
}