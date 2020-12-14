package com.example.squadzframes;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        VideoView videoView = (VideoView) findViewById(R.id.videoView);
//        String path = "android.resource://" + getPackageName() + "/" + R.raw.squadzvideo;
//         Uri u =Uri.parse(path);
//
//        videoView.setVideoURI(u);
//        //  videoView.start();
//        MediaController mediaController = new MediaController(this);
//        videoView.setMediaController(mediaController);
//        mediaController.setAnchorView(videoView);


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_events, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
       // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

//         videoView = (VideoView) findViewById(R.id.videoView);
//        String path = "android.resource://"+getPackageName()+R.raw.squadz;
//        Uri u =Uri.parse(path);
//        videoView.setVideoURI(u);
//        videoView.start();
//
//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                mp.setLooping(true);
//            }
//        });
    }
//    @Override
//    public void onResume() {
//        videoView.resume();
//        super.onResume();
//    }
//    @Override
//    public void onPause() {
//        videoView.suspend();
//        super.onPause();
//    }
//    @Override
//    public void onDestroy() {
//        videoView.stopPlayback();
//        super.onDestroy();
//    }
}