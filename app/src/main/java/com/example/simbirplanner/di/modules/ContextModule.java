package com.example.simbirplanner.di.modules;

import android.content.Context;

import com.example.simbirplanner.annotations.ApplicationContext;
import com.example.simbirplanner.annotations.SimBirPlannerApplication;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @ApplicationContext
    @SimBirPlannerApplication
    @Provides
    public Context context(){
        return context.getApplicationContext();
    }
}
