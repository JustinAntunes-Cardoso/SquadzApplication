package com.example.squadzframes.ui.friends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.squadzframes.R;
import com.example.squadzframes.model.Users;
import com.example.squadzframes.ui.events.DetailsPage;
import com.example.squadzframes.ui.events.OpenEvent;
import com.example.squadzframes.ui.home.MainActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllUsersActivity extends AppCompatActivity {

    private RecyclerView allUserList;
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    FirebaseAuth mAuth;
    FirebaseUser mCurrentUser;
    private DatabaseReference mUsersDatabase;
    Query query;
    FirebaseRecyclerOptions<Users> options;
    private RecyclerView FindFriendsRecyclerList;
    private DatabaseReference mFriendRequestDatabase;
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

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mFriendRequestDatabase = FirebaseDatabase.getInstance().getReference().child("Friend_Request");
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        mCurrentState = "not_friends";

        query=mUsersDatabase;
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
                Intent mainIntent = new Intent(AllUsersActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Users,UsersViewHolder>
                firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder usersViewHolder, int position, @NonNull Users users) {
                usersViewHolder.setName(users.getName());
                //usersViewHolder.setStatus(users.getMessage());
                currentUserID= mAuth.getCurrentUser().getUid();
                //imageString = "profile:" + currentUserID + ".jpg";
                usersViewHolder.setUserImage(users.getImageProfile(), getApplicationContext());

                //Sends user unique key
                final String current_user_id = getRef(position).getKey();

                usersViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent profileIntent = new Intent(AllUsersActivity.this,FriendPageActivity.class);
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

//                        Intent profileIntent = new Intent(AllUsersActivity.this,FriendPageActivity.class);
//                        profileIntent.putExtra("current_user_id", current_user_id);
//                        startActivity(profileIntent);

//                        usersViewHolder.add.setEnabled(false);
//
//                        //------------NOT FRIENDS STATE----------//
//                        if(mCurrentState.equals("not_friends")){
//                            mFriendRequestDatabase.child(mCurrentUser.getUid()).child(current_user_id).child("request_type").setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if(task.isSuccessful()){
//                                        mFriendRequestDatabase.child(current_user_id).child(mCurrentUser.getUid()).child("request_type")
//                                                .setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//
//                                                usersViewHolder.add.setEnabled(true);
//                                                mCurrentState = "req_sent";
//                                                usersViewHolder.add.setText("X");
//
//                                                //Toast.makeText(AllUsersActivity.this,"Request Sent Successfully",Toast.LENGTH_LONG).show();
//                                            }
//                                        });
//                                    }else{
//                                        Toast.makeText(AllUsersActivity.this,"Failed Sending Request",Toast.LENGTH_LONG).show();
//                                    }
//                                }
//                            });
//                        }
//
//                        //------------CANCEL REQUEST STATE----------//
//                        if(mCurrentState.equals("req_sent")){
//                            mFriendRequestDatabase.child(mCurrentUser.getUid()).child(current_user_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    mFriendRequestDatabase.child(current_user_id).child(mCurrentUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            usersViewHolder.add.setEnabled(true);
//                                            mCurrentState = "not_friends";
//                                            usersViewHolder.add.setText("Add");
//                                        }
//                                    });
//                                }
//                            });
//                        }

                    }
                });

            }

            @NonNull
            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_users, parent, false);
                UsersViewHolder viewHolder = new UsersViewHolder(view);
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