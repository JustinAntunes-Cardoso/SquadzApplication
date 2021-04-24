package com.example.squadzframes.ui.friends;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.squadzframes.R;
import com.example.squadzframes.model.MachineLocation;
import com.example.squadzframes.model.Users;
import com.example.squadzframes.ui.home.MainActivity;
import com.example.squadzframes.ui.notifications.MachineLocationActivity;
import com.example.squadzframes.ui.notifications.WeatherActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsListActivity extends AppCompatActivity {


    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    FirebaseAuth mAuth;
    FirebaseUser mCurrentUser;
    private DatabaseReference mUsersDatabase;
    Query query;
    FirebaseRecyclerOptions<Users> options;
    String currentUserID;
    ArrayList<String> friendsUserID = new ArrayList<String>();
    private DatabaseReference mFriendsDatabase;
    private RecyclerView mFriendsRecyclerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);



        mAuth = FirebaseAuth.getInstance();
        StorageReference filepathRef = mStorageRef.child("profile_images").child("profile:" + currentUserID + ".jpg");
        mCurrentUser = mAuth.getCurrentUser();
        mFriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Friends").child(mCurrentUser.getUid());
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        query=mUsersDatabase;
        options =
                new FirebaseRecyclerOptions.Builder<Users>()
                        .setQuery(query, Users.class)
                        .build();

        mFriendsRecyclerList = findViewById(R.id.recyclerViewFriends);
        mFriendsRecyclerList.setLayoutManager(new LinearLayoutManager(this));

        mFriendsDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.getKey();
                //String[] arrOfStr = value.split("=");
                //friendsUserID.add(arrOfStr[0]);
                friendsUserID.add(value/*.substring(1,29)*/);

                Context context = getApplicationContext();
                Toast.makeText(context, value/*friendsUserID.toString()*/,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.getValue().toString();
                //String[] arrOfStr = value.split("=");
                //friendsUserID.add(arrOfStr[0]);
                friendsUserID.add(value.substring(1,29));

                //Context context = getApplicationContext();
                //Toast.makeText(context, friendsUserID.toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Users, FriendsListActivity.FriendsViewHolder>
                firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, FriendsListActivity.FriendsViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull FriendsViewHolder friendsViewHolder, int position, @NonNull Users user) {


                RecyclerView.LayoutParams param = (RecyclerView.LayoutParams)friendsViewHolder.itemView.getLayoutParams();

                if(friendsUserID.contains(getRef(position).getKey())) {
                    Context context = getApplicationContext();
                    Toast.makeText(context, getRef(position).getKey(),Toast.LENGTH_LONG).show();

                    friendsViewHolder.setName(user.getName());
                    //usersViewHolder.setStatus(users.getMessage());
                    //currentUserID= mAuth.getCurrentUser().getUid();
                    //imageString = "profile:" + currentUserID + ".jpg";
                    friendsViewHolder.setUserImage(user.getImageProfile(), getApplicationContext());
                }else{
                    friendsViewHolder.itemView.setVisibility(View.GONE);
                    param.height = 0;
                    param.width = 0;
                }
                //Sends user unique key
                final String current_user_id = getRef(position).getKey();

                friendsViewHolder.mView.setOnClickListener(new View.OnClickListener() {
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

            }

            @NonNull
            @Override
            public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_friends, parent, false);
                FriendsViewHolder viewHolder = new FriendsViewHolder(view);
                return viewHolder;
            }
        };

        mFriendsRecyclerList.setAdapter(firebaseRecyclerAdapter);

        firebaseRecyclerAdapter.startListening();
    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView friendName;
        CircleImageView friendView;

        public FriendsViewHolder(@NonNull View itemView) {
            super(itemView);
            friendName = itemView.findViewById(R.id.friend_name);
            mView = itemView;
        }
        public void setName(String name){
            TextView userNameView = mView.findViewById(R.id.friend_name);
            userNameView.setText(name);
        }

        public void setUserImage(String image, Context ctx){
            friendView = mView.findViewById(R.id.friend_image);

            Picasso.get().load(image).placeholder(R.drawable.default_avatar).into(friendView);
        }
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_all_users);
//
//        //final String user_id = getIntent().getStringExtra("user_id");
//
//        mAuth = FirebaseAuth.getInstance();
//
//        StorageReference filepathRef = mStorageRef.child("profile_images").child("profile:" + currentUserID + ".jpg");
//
//        mCurrentUser = mAuth.getCurrentUser();
//        //mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
//        //mFriendRequestDatabase = FirebaseDatabase.getInstance().getReference().child("Friend_Request");
//        mFriendDatabase = FirebaseDatabase.getInstance().getReference().child("Friends").child(mCurrentUser.getUid());
//
//
//        //mCurrentState = "not_friends";
//
//        query=mFriendDatabase;
//        options =
//                new FirebaseRecyclerOptions.Builder<Users>()
//                        .setQuery(query, Users.class)
//                        .build();
//
//        allUserList = findViewById(R.id.all_users_recycle);
//        allUserList.setLayoutManager(new LinearLayoutManager(this));
//
//        ImageView bttn = (ImageView) findViewById(R.id.back_button);
//        bttn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent mainIntent = new Intent(FriendsListActivity.this, MainActivity.class);
//                startActivity(mainIntent);
//            }
//        });
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseRecyclerAdapter<Users, FriendsListActivity.UsersViewHolder>
//                firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, FriendsListActivity.UsersViewHolder>(options) {
//
//            @Override
//            protected void onBindViewHolder(@NonNull FriendsListActivity.UsersViewHolder usersViewHolder, int position, @NonNull Users users) {
//                usersViewHolder.setName(users.getName());
//                //usersViewHolder.setStatus(users.getMessage());
//                //currentUserID= mAuth.getCurrentUser().getUid();
//                //imageString = "profile:" + currentUserID + ".jpg";
//                usersViewHolder.setUserImage(users.getImageProfile(), getApplicationContext());
//
//                //Sends user unique key
//                final String current_user_id = getRef(position).getKey();
//
//                usersViewHolder.mView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent profileIntent = new Intent(FriendsListActivity.this,FriendPageActivity.class);
//                        profileIntent.putExtra("current_user_id", current_user_id);
////                        intent.putExtra("host",host);
////                        intent.putExtra("time",time);
////                        intent.putExtra("party",party);
//                        startActivity(profileIntent);
//                    }
//                });
//
//                usersViewHolder.add.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//
//                    }
//                });
//
//            }
//
//            @NonNull
//            @Override
//            public FriendsListActivity.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_users, parent, false);
//                FriendsListActivity.UsersViewHolder viewHolder = new FriendsListActivity.UsersViewHolder(view);
//                return viewHolder;
//            }
//        };
//
//        allUserList.setAdapter(firebaseRecyclerAdapter);
//
//        firebaseRecyclerAdapter.startListening();
//    }
//
//    public static class UsersViewHolder extends RecyclerView.ViewHolder{
//        View mView;
//        TextView userName, userStatus;
//        CircleImageView userImageView;
//        Button add;
//
//
//        public UsersViewHolder(@NonNull View itemView) {
//            super(itemView);
//            userName = itemView.findViewById(R.id.user_name);
//            add = itemView.findViewById(R.id.view_button);
//            mView = itemView;
//        }
//        public void setName(String name){
//            TextView userNameView = mView.findViewById(R.id.user_name);
//            userNameView.setText(name);
//        }
//        public void setStatus(String message){
//
//        }
//        public void setUserImage(String image, Context ctx){
//            userImageView = mView.findViewById(R.id.user_image);
//            Picasso.get().load(image).placeholder(R.drawable.default_avatar).into(userImageView);
//        }
//    }
//
}
