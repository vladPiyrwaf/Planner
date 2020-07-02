package com.example.simbirplanner.di.modules;

import android.content.Context;
import android.util.Log;

import com.example.simbirplanner.annotations.ApplicationContext;
import com.example.simbirplanner.annotations.SimBirPlannerApplication;
import com.example.simbirplanner.models.EventInformation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.annotation.Nonnull;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@Module(includes = ContextModule.class)
public class RealmModule {

    @SimBirPlannerApplication
    @Provides
    Realm realm(@ApplicationContext Context context, RealmConfiguration config){
        Realm.init(context.getApplicationContext());
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
        }catch (Exception e){
            realm = Realm.getInstance(config);
        }
        return realm;
    }

    @SimBirPlannerApplication
    @Provides
    RealmConfiguration realmConfiguration(@ApplicationContext Context context){
        return new RealmConfiguration.Builder()
                .initialData(realm -> {
                    List<EventInformation> events = loadEvents(context);
                        realm.insert(events);
                })
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    private List<EventInformation> loadEvents(Context context){
        InputStream stream;
        try{
            stream = context.getApplicationContext().getAssets().open("events.json");
        }catch (IOException e){
            e.getMessage();
            return null;
        }

        Gson gson = new GsonBuilder().create();

        JsonElement json = new JsonParser().parse(new InputStreamReader(stream));
        return gson.fromJson(json, new TypeToken<List<EventInformation>>(){}.getType());
    }
}
