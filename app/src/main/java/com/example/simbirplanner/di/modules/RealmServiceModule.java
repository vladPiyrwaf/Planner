package com.example.simbirplanner.di.modules;

import com.example.simbirplanner.repository.RealmService;
import com.example.simbirplanner.annotations.SimBirPlannerApplication;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module(includes = RealmModule.class)
public class RealmServiceModule {

    @SimBirPlannerApplication
    @Provides
    RealmService realmService(Realm realm) {
        return new RealmService(realm);
    }
}
