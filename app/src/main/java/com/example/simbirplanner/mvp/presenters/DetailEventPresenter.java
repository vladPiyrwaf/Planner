package com.example.simbirplanner.mvp.presenters;

import com.example.simbirplanner.repository.RealmService;
import com.example.simbirplanner.SimbirPlannerApplication;
import com.example.simbirplanner.mvp.views.DetailEventView;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class DetailEventPresenter extends MvpPresenter<DetailEventView> {

    @Inject
    RealmService realmService;

    public DetailEventPresenter(String id) {
        SimbirPlannerApplication.getAppComponent().inject(this);
        loadEventInformation(id);
    }

    private void loadEventInformation(String id) {
        getViewState().showEventInformation(realmService.searchById(id));
    }

}
