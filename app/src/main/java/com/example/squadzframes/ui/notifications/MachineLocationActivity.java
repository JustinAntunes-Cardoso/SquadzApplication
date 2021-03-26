package com.example.squadzframes.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.squadzframes.R;
import com.example.squadzframes.model.MachineLocation;
import com.example.squadzframes.model.Users;
import com.example.squadzframes.ui.friends.AllUsersActivity;
import com.example.squadzframes.ui.friends.FriendPageActivity;
import com.example.squadzframes.ui.home.MainActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MachineLocationActivity extends AppCompatActivity {

    private DatabaseReference mWeatherDatabase;
    Query query;
    FirebaseRecyclerOptions<MachineLocation> options;
    private RecyclerView mMachineRecyclerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_location);

        mMachineRecyclerList = findViewById(R.id.recyclerViewMachineLocation);

        mWeatherDatabase = FirebaseDatabase.getInstance().getReference().child("Weather");

        query=mWeatherDatabase;
        options =
                new FirebaseRecyclerOptions.Builder<MachineLocation>()
                        .setQuery(query, MachineLocation.class)
                        .build();

        mMachineRecyclerList.setHasFixedSize(true);
        mMachineRecyclerList.setLayoutManager(new LinearLayoutManager(this));

//        ImageView bttn = (ImageView) findViewById(R.id.back_button);
//        bttn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent mainIntent = new Intent(MachineLocationActivity.this, MainActivity.class);
//                startActivity(mainIntent);
//            }
//        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<MachineLocation, MachineLocationActivity.MachineViewHolder>
                firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<MachineLocation, MachineLocationActivity.MachineViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull MachineLocationActivity.MachineViewHolder machineViewHolder, int position, @NonNull MachineLocation machine) {
                machineViewHolder.setLocation(machine.getLocation());
                //usersViewHolder.setStatus(users.getMessage());
                //currentUserID= mAuth.getCurrentUser().getUid();
                //imageString = "profile:" + currentUserID + ".jpg";
                machineViewHolder.setLocationImage(machine.getImageBackground(), getApplicationContext());

                //Sends machine name
                final String machine_name = machine.getName();

                machineViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent profileIntent = new Intent(MachineLocationActivity.this, WeatherActivity.class);
                        profileIntent.putExtra("machine_name", machine_name);
//                        intent.putExtra("host",host);
//                        intent.putExtra("time",time);
//                        intent.putExtra("party",party);
                        startActivity(profileIntent);
                    }
                });
            }

            @NonNull
            @Override
            public MachineLocationActivity.MachineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row_machines, parent, false);
                MachineLocationActivity.MachineViewHolder viewHolder = new MachineLocationActivity.MachineViewHolder(view);
                return viewHolder;
            }
        };

        mMachineRecyclerList.setAdapter(firebaseRecyclerAdapter);

        firebaseRecyclerAdapter.startListening();
    }

    public static class MachineViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView machineLocation;
        CircleImageView locationView;

        public MachineViewHolder(@NonNull View itemView) {
            super(itemView);
            machineLocation = itemView.findViewById(R.id.location_name);
            mView = itemView;
        }
        public void setLocation(String name){
            TextView userNameView = mView.findViewById(R.id.location_name);
            userNameView.setText(name);
        }

        public void setLocationImage(String image, Context ctx){
            locationView = mView.findViewById(R.id.location_image);

            Picasso.get().load(image).placeholder(R.drawable.default_avatar).into(locationView);
        }
    }

}