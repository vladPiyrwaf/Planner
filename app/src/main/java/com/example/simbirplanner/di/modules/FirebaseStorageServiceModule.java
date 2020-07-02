package com.example.simbirplanner.di.modules;

import com.example.simbirplanner.annotations.SimBirPlannerApplication;
import com.example.simbirplanner.repository.FireStoreService;
import com.google.firebase.storage.FirebaseStorage;

import dagger.Module;
import dagger.Provides;

@Module(includes = {FirebaseStorageModule.class})
public class FirebaseStorageServiceModule {

    @SimBirPlannerApplication
    @Provides
    FireStoreService fireStoreService(FirebaseStorage firebaseStorage){
        return new FireStoreService(firebaseStorage);
    }
}
