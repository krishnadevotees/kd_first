package com.example.visonofman.ui.chapters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChaptersViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ChaptersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Language fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}