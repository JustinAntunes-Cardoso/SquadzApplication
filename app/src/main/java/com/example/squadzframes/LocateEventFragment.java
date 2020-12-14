package com.example.squadzframes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.squadzframes.ui.events.OpenEvent;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocateEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocateEventFragment extends Fragment {

    Activity context;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LocateEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocateEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocateEventFragment newInstance(String param1, String param2) {
        LocateEventFragment fragment = new LocateEventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_locate_event, container, false);
        context=getActivity();

        ImageButton btn = (ImageButton) view.findViewById(R.id.imageButton7);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, OpenEvent.class);
                startActivity(intent);
                //Navigation.findNavController(view).navigate(R.id.action_locateEventFragment_to_openEvent);
            }
        });

        ImageButton btn2 = (ImageButton) view.findViewById(R.id.BackButtonOnLocateEvent);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).navigate(R.id.action_locateEventFragment_to_navigation_events);
            }
        });
        return view;

    }
}