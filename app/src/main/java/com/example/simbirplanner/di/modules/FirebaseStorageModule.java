package com.example.simbirplanner.di.modules;

import com.example.simbirplanner.annotations.SimBirPlannerApplication;
import com.google.firebase.storage.FirebaseStorage;

import dagger.Module;
import dagger.Provides;
import moxy.presenter.ProvidePresenter;

@Module
public class FirebaseStorageModule {

    @SimBirPlannerApplication
    @Provides
    FirebaseStorage firebaseStorage(){
        return FirebaseStorage.getInstance();
    }
}
