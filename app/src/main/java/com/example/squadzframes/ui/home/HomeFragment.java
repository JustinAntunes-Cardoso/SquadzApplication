package com.example.squadzframes.ui.home;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.squadzframes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private CircleImageView profile;
    private DatabaseReference userDatabase;
    private FirebaseUser currentUser;
    String currentUid;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        profile = (CircleImageView) root.findViewById(R.id.profile_image);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            currentUid = currentUser.getUid();
            userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid);
            if (userDatabase != null) {
                userDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String image = snapshot.child("image").getValue().toString();
//                String back_image = snapshot.child("background_image").getValue().toString();
                        Picasso.get().load(image).placeholder(R.drawable.default_avatar).into(profile);
                    }
                    //Picasso.get().load(back_image).into(background);

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        }

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfile();
            }
        });

//        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });

        VideoView videoView = (VideoView) root.findViewById(R.id.videoView2);
        String path = "android.resource://" + "com.example.squadzframes" + "/" + R.raw.squadzvideo;
        Uri u =Uri.parse(path);

        videoView.setVideoURI(u);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

//        MediaController mediaController = new MediaController(this.getContext());
//        videoView.setMediaController(mediaController);
//        mediaController.setAnchorView(videoView);


        return root;
    }

    public void openProfile()
    {
        Intent intent = new Intent(getActivity(), com.example.squadzframes.ui.profile.profile.class);
        startActivity(intent);
    }



}