package com.example.simbirplanner.repository;

import android.util.Log;

import com.example.simbirplanner.OnTransactionCallBack;
import com.example.simbirplanner.models.EventInformation;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmService implements Service<EventInformation>{

    @Inject
    Realm realm;

    private static final String ID = "id";
    private static final String POSITION = "position";

    public RealmService(Realm realm) {
        this.realm = realm;
    }

    //TODO когда закрывать реалм в presenter?
    public void closeRealm() {
        realm.close();
    }

//    public RealmResults<EventInformation> getAllEvents() {
//        return realm.where(EventInformation.class).findAll();
//    }
//
//    public void addEvent(String title, String description, long position, long dateStart,
//                         long dateFinish, String imageUrl,OnTransactionCallback callback) {
//        realm.executeTransactionAsync((realm) -> {
//
//            EventInformation eventInformation = realm.createObject(EventInformation.class, UUID.randomUUID().toString());
//            eventInformation.setName(title);
//            eventInformation.setPosition(position);
//            eventInformation.setDateStart(dateStart);
//            eventInformation.setDateFinish(dateFinish);
//            eventInformation.setDescription(description);
//            eventInformation.setImageUrl(imageUrl);
//        }, () -> {
//            if (callback != null)
//                callback.onRealmSuccess();
//
//        }, (error) -> {
//            if (callback != null) {
//                callback.onRealmError(error);
//            }
//        });
//    }
//
//    public RealmResults<EventInformation> searchEventsByTimePosition(long position) {
//        return realm.where(EventInformation.class)
//                .equalTo(POSITION, position)
//                .findAll();
//    }
//
//    public EventInformation getEventById(String id) {
//        return realm.where(EventInformation.class).equalTo(ID, id).findFirst();
//    }
//
//    public void deleteEventSync(long id, OnTransactionCallback callback) {
//        realm.executeTransactionAsync((realm) -> {
//            EventInformation event = realm.where(EventInformation.class)
//                    .equalTo(ID, id)
//                    .findFirst();
//            if (event != null) {
//                event.deleteFromRealm();
//            }
//        }, () -> {
//            if (callback != null)
//                callback.onRealmSuccess();
//        }, (error) -> {
//            if (callback != null)
//                callback.onRealmError(error);
//        });
//    }

    @Override
    public List<EventInformation> getAll() {
        return realm.where(EventInformation.class).findAll();
    }

    @Override
    public void add(String title, String description, long position, long dateStart, long dateFinish, String imageUrl, OnTransactionCallBack callback) {
        realm.executeTransactionAsync((realm) -> {

            EventInformation eventInformation = realm.createObject(EventInformation.class, UUID.randomUUID().toString());
            eventInformation.setName(title);
            eventInformation.setPosition(position);
            eventInformation.setDateStart(dateStart);
            eventInformation.setDateFinish(dateFinish);
            eventInformation.setDescription(description);
            eventInformation.setImageUrl(imageUrl);
        }, () -> {
            if (callback != null)
                callback.onSuccess();

        }, (error) -> {
            if (callback != null) {
                callback.onError(error);
            }
        });
    }

    @Override
    public List<EventInformation> searchByPosition(long position) {
        return realm.where(EventInformation.class)
                .equalTo(POSITION, position)
                .findAll();
    }

    @Override
    public EventInformation searchById(String id) {
        return realm.where(EventInformation.class).equalTo(ID, id).findFirst();
    }

    @Override
    public void deleteById(String id, OnTransactionCallBack  callback) {
        realm.executeTransactionAsync((realm) -> {
            EventInformation event = realm.where(EventInformation.class)
                    .equalTo(ID, id)
                    .findFirst();
            if (event != null) {
                event.deleteFromRealm();
            }
        }, () -> {
            if (callback != null)
                callback.onSuccess();
        }, (error) -> {
            if (callback != null)
                callback.onError(error);
        });
    }

    @Override
    public void editById(String id) {

    }
}
