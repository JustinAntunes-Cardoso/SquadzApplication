package com.example.squadzframes.ui.friends;

import android.widget.ListView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.squadzframes.R;

public class FriendViewModel extends ViewModel {
    private MutableLiveData<String> mText;
    android.widget.ListView ListView;

    public FriendViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is friend fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}