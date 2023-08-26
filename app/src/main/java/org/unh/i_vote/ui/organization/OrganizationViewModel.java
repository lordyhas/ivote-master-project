package org.unh.i_vote.ui.organization;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OrganizationViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public OrganizationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}