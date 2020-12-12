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
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.squadzframes.R;
import com.example.squadzframes.profile;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ImageView profile;
//    VideoView videoView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        videoView = (VideoView) root.findViewById(R.id.videoView);

        final ImageView profile = root.findViewById(R.id.imageView5);
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
        //  videoView.start();
        MediaController mediaController = new MediaController(this.getContext());
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);


        return root;
    }

    public void openProfile()
    {
        Intent intent = new Intent(getActivity(), com.example.squadzframes.profile.class);
        startActivity(intent);
    }



}