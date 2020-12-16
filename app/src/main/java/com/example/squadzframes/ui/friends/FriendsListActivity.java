package com.example.squadzframes.ui.friends;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.squadzframes.model.Users;
import com.example.squadzframes.ui.home.MainActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsListActivity extends AppCompatActivity {

    private RecyclerView allUserList;
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    FirebaseAuth mAuth;
    FirebaseUser mCurrentUser;
    private DatabaseReference mUsersDatabase;
    Query query;
    FirebaseRecyclerOptions<Users> options;
    private RecyclerView FindFriendsRecyclerList;
    private DatabaseReference mFriendRequestDatabase;
    private DatabaseReference mFriendDatabase;
    String currentUserID;
    String imageString;
    String mCurrentState;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        //final String user_id = getIntent().getStringExtra("user_id");

        mAuth = FirebaseAuth.getInstance();

        StorageReference filepathRef = mStorageRef.child("profile_images").child("profile:" + currentUserID + ".jpg");

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mFriendRequestDatabase = FirebaseDatabase.getInstance().getReference().child("Friend_Request");
        mFriendDatabase = FirebaseDatabase.getInstance().getReference().child("Friends");//.child(mCurrentUser.getUid());


        mCurrentState = "not_friends";

        query=mFriendDatabase;
        options =
                new FirebaseRecyclerOptions.Builder<Users>()
                        .setQuery(query, Users.class)
                        .build();

        allUserList = findViewById(R.id.all_users_recycle);
        allUserList.setLayoutManager(new LinearLayoutManager(this));

        ImageView bttn = (ImageView) findViewById(R.id.back_button);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(FriendsListActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Users, FriendsListActivity.UsersViewHolder>
                firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, FriendsListActivity.UsersViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull FriendsListActivity.UsersViewHolder usersViewHolder, int position, @NonNull Users users) {
                usersViewHolder.setName(users.getName());
                //usersViewHolder.setStatus(users.getMessage());
                //currentUserID= mAuth.getCurrentUser().getUid();
                //imageString = "profile:" + currentUserID + ".jpg";
                usersViewHolder.setUserImage(users.getImageProfile(), getApplicationContext());

                //Sends user unique key
                final String current_user_id = getRef(position).getKey();

                usersViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent profileIntent = new Intent(FriendsListActivity.this,FriendPageActivity.class);
                        profileIntent.putExtra("current_user_id", current_user_id);
//                        intent.putExtra("host",host);
//                        intent.putExtra("time",time);
//                        intent.putExtra("party",party);
                        startActivity(profileIntent);
                    }
                });

                usersViewHolder.add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    }
                });

            }

            @NonNull
            @Override
            public FriendsListActivity.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_users, parent, false);
                FriendsListActivity.UsersViewHolder viewHolder = new FriendsListActivity.UsersViewHolder(view);
                return viewHolder;
            }
        };

        allUserList.setAdapter(firebaseRecyclerAdapter);

        firebaseRecyclerAdapter.startListening();
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView userName, userStatus;
        CircleImageView userImageView;
        Button add;


        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            add = itemView.findViewById(R.id.view_button);
            mView = itemView;
        }
        public void setName(String name){
            TextView userNameView = mView.findViewById(R.id.user_name);
            userNameView.setText(name);
        }
        public void setStatus(String message){

        }
        public void setUserImage(String image, Context ctx){
            userImageView = mView.findViewById(R.id.user_image);
            Picasso.get().load(image).placeholder(R.drawable.default_avatar).into(userImageView);
        }
    }
}
