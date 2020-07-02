package com.example.simbirplanner.mvp.presenters;

import com.example.simbirplanner.OnTransactionCallBack;
import com.example.simbirplanner.repository.RealmService;
import com.example.simbirplanner.SimbirPlannerApplication;
import com.example.simbirplanner.models.EventInformation;
import com.example.simbirplanner.mvp.views.MainView;

import java.util.List;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> implements OnTransactionCallBack {

    @Inject
    RealmService service;

    private long selectedDay;

    public MainPresenter(long selectedDay) {
        SimbirPlannerApplication.getAppComponent().inject(this);
        this.selectedDay = selectedDay;
    }

    public void loadEventSelectedDay(long selectedDay){
        List<EventInformation> list = service.searchByPosition(selectedDay);

        getViewState().showSelectedEvents(list);
    }

    public void getAllEvents(){
        getViewState().showAllEvents(service.getAll());
    }

    public void openCreateEventScreen(){
        getViewState().openCreateEventScreen();
    }

    public void deleteEvent(String id){
        service.deleteById(id, this);
    }

    @Override
    public void onSuccess() {
        getViewState().successDeleteEvent();
    }

    @Override
    public void onError(Throwable error) {
        getViewState().showErrorDeleteEvent(error);
    }
}
