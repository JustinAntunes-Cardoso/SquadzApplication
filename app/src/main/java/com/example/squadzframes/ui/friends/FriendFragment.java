package com.example.squadzframes.ui.friends;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.squadzframes.R;

public class FriendFragment extends Fragment {



    private FriendViewModel mViewModel;

    public static FriendFragment newInstance() {
        return new FriendFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.friend_fragment, container, false);

        Button friendListButton = (Button) root.findViewById(R.id.friends_list_button);
        friendListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(root).navigate(R.id.action_navigation_friends_to_friendsListActivity);
            }
        });

        ImageView bttn = (ImageView) root.findViewById(R.id.add_friend_button);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_navigation_friends_to_allUsersActivity);
            }
        });

        return root;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FriendViewModel.class);
        // TODO: Use the ViewModel

    }

    public void openProfile(){
        Intent intent = new Intent(getActivity(), FriendPageActivity.class);
        startActivity(intent);
    }

}