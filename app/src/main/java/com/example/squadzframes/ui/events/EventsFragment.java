package com.example.squadzframes.ui.events;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.squadzframes.R;

public class EventsFragment extends Fragment {

    private EventsViewModel eventsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        eventsViewModel =
                new ViewModelProvider(this).get(EventsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_events, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        eventsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {

            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        ImageView btn = (ImageView) root.findViewById(R.id.imageButton2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_navigation_events_to_locateEventMap);
            }
        });

        ImageView bttn = (ImageView) root.findViewById(R.id.imageButton3);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_navigation_events_to_openEvent);
            }
        });



        return root;
    }


}