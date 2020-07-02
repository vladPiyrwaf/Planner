package com.example.simbirplanner.mvp.presenters;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.example.simbirplanner.OnTransactionCallBack;
import com.example.simbirplanner.R;
import com.example.simbirplanner.repository.FireStoreService;
import com.example.simbirplanner.repository.RealmService;
import com.example.simbirplanner.SimbirPlannerApplication;
import com.example.simbirplanner.mvp.views.CreateEventView;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class CreateEventPresenter extends MvpPresenter<CreateEventView> implements OnTransactionCallBack,
FireStoreService.OnCompletedCallback{

    @Inject
    RealmService realmService;
    @Inject
    FireStoreService fireStoreService;

    private String url;
    private String title;
    private String description;
    private long position, timeStart, timeFinish;

    public CreateEventPresenter() {
        SimbirPlannerApplication.getAppComponent().inject(this);
    }

    public void addEvent(String title, String description, long position, long timeStart,
                         long timeFinish, Uri imageUri) {
        Integer titleError = null;
        Integer descriptionError = null;
        if (TextUtils.isEmpty(title)) {
            titleError = R.string.error_required;
        }

        if (TextUtils.isEmpty(description)) {
            descriptionError = R.string.error_required;
        }

        if (titleError != null || descriptionError!= null){
            getViewState().showErrorInput(titleError, descriptionError);
            return;
        }
        if (imageUri!=null){
            this.title = title;
            this.description = description;
            this.position = position;
            this.timeStart = timeStart;
            this.timeFinish = timeFinish;
            uploadImageEvent(imageUri);
        }
        else {
            realmService.add(title, description, position, timeStart, timeFinish, url, this);
        }
    }

    private void uploadImageEvent(Uri uri){
            getViewState().showHideProgressBar(true);
            getViewState().clickableButton(false);
            fireStoreService.uploadImageEvent(uri, this);
    }

    public void showTimePickerDialogStart(){
        getViewState().showTimePickerDialogStart();
    }

    public void showTimePickerDialogFinish(){
        getViewState().showTimePickerDialogFinish();
    }
    public void onDismissTimePicker() {
        getViewState().hideTimePicker();
    }

    @Override
    public void onUploadImageSuccess(String url) {
        Log.d("presenter", "onUploadImageSuccess: " + url);
        getViewState().showHideProgressBar(false);
        this.url = url;
        realmService.add(title, description, position, timeStart, timeFinish, url, this);
    }

    @Override
    public void onUploadImageError() {

    }

    @Override
    public void onSuccess() {
        getViewState().successSaveEvent();
    }

    @Override
    public void onError(Throwable error) {
        getViewState().showErrorCreate(error.getMessage());
    }
}
