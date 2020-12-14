package com.example.squadzframes.ui.friends;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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