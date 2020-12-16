package com.example.squadzframes.ui.friends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.squadzframes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendPageActivity extends AppCompatActivity {

    private ImageView mBackgroundImage;
    private CircleImageView mProfileImage;
    private TextView mProfileName, mProfileStatus;
    private Button mProfileSendRequestBtn;

    private DatabaseReference mUsersDatabase;

    private int mNumber;
    private String mCurrentState;

    private Button mProfileDeclineRequestBtn;

    private ProgressDialog mProgressDialog;
    private DatabaseReference mFriendRequestDatabase;
    FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_page);

        String user_id = getIntent().getStringExtra("current_user_id");

        //mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mFriendRequestDatabase = FirebaseDatabase.getInstance().getReference().child("Friend_Request");
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);


        mBackgroundImage = (ImageView) findViewById(R.id.profile_background);
        mProfileImage = (CircleImageView) findViewById(R.id.friend_image);
        mProfileName = (TextView) findViewById(R.id.friend_name);
        mProfileStatus = (TextView) findViewById(R.id.status_friend);
        mProfileSendRequestBtn = (Button) findViewById(R.id.add_button);
        mProfileDeclineRequestBtn = (Button) findViewById(R.id.cancel_request_button);

        mCurrentState = "not_friends";

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading User Data");
        mProgressDialog.setMessage("Please wait while we get your data");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String displayName = snapshot.child("name").getValue().toString();
                String displayStatus = snapshot.child("status").getValue().toString();
                String displayImage = snapshot.child(("image")).getValue().toString();
                String displayBackground = snapshot.child("background").getValue().toString();

                mProfileName.setText(displayName);
                mProfileStatus.setText(displayStatus);
                Picasso.get().load(displayImage).placeholder(R.drawable.default_avatar).into(mProfileImage);
                Picasso.get().load(displayBackground).placeholder(R.drawable.richardbackground).into(mBackgroundImage);

                mProgressDialog.dismiss();

                //-----------------------Friends List/Request Feature-------------------------//
                mFriendRequestDatabase.child(mCurrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(user_id)){
                            String req_type = snapshot.child(user_id).child("request_type").getValue().toString();

                            if(req_type.equals("received")){

                                //usersViewHolder.add.setEnabled(true);
                                mCurrentState = "req_received";
                                //usersViewHolder.add.setText("Accept");

                            }else if(req_type.equals("sent")){
                                mCurrentState = "req_sent";
                                //usersViewHolder.add.setText("Cancel");

                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

/////////////////////////////////////////////


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mProfileSendRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mProfileSendRequestBtn.setEnabled(false);

                //------------NOT FRIENDS STATE----------//
                if (mCurrentState.equals("not_friends")) {
                    mFriendRequestDatabase.child(mCurrentUser.getUid()).child(user_id).child("request_type").setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                mFriendRequestDatabase.child(user_id).child(mCurrentUser.getUid()).child("request_type")
                                        .setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        mProfileSendRequestBtn.setEnabled(true);
                                        mCurrentState = "req_sent";
                                        mProfileSendRequestBtn.setText("Cancel Request");

                                        //Toast.makeText(AllUsersActivity.this,"Request Sent Successfully",Toast.LENGTH_LONG).show();
                                    }
                                });
                            } else {
                                Toast.makeText(FriendPageActivity.this, "Failed Sending Request", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

                //------------CANCEL REQUEST STATE----------//
                if (mCurrentState.equals("req_sent")) {
                    mFriendRequestDatabase.child(mCurrentUser.getUid()).child(user_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mFriendRequestDatabase.child(user_id).child(mCurrentUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mProfileSendRequestBtn.setEnabled(true);
                                    mCurrentState = "not_friends";
                                    mProfileSendRequestBtn.setText("Add");
                                }
                            });
                        }
                    });
                }
            }
        });


        ImageButton btn = (ImageButton) findViewById(R.id.friendBackButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}