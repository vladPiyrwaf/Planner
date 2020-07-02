package com.example.simbirplanner;

import android.app.Application;

import com.example.simbirplanner.di.components.ApplicationComponent;
import com.example.simbirplanner.di.components.DaggerApplicationComponent;
import com.example.simbirplanner.di.modules.ContextModule;

import io.realm.Realm;

public class SimbirPlannerApplication extends Application {

    private static ApplicationComponent applicationComponent;


    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        applicationComponent = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

    }

    public static ApplicationComponent getAppComponent(){
        return applicationComponent;
    }
}
