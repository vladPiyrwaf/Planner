package com.example.simbirplanner.di.components;

import com.example.simbirplanner.di.modules.FirebaseStorageServiceModule;
import com.example.simbirplanner.repository.FireStoreService;
import com.example.simbirplanner.repository.RealmService;
import com.example.simbirplanner.annotations.SimBirPlannerApplication;
import com.example.simbirplanner.di.modules.RealmServiceModule;
import com.example.simbirplanner.mvp.presenters.CreateEventPresenter;
import com.example.simbirplanner.mvp.presenters.DetailEventPresenter;
import com.example.simbirplanner.mvp.presenters.MainPresenter;

import dagger.Component;

@SimBirPlannerApplication
@Component(modules = {RealmServiceModule.class, FirebaseStorageServiceModule.class})
public interface ApplicationComponent {
    RealmService getRealmService();
    FireStoreService getFirebaseStorage();

    void inject(DetailEventPresenter detailEventPresenter);
    void inject(CreateEventPresenter createEventPresenter);
    void inject(MainPresenter mainPresenter);
}
