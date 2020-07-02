package com.example.simbirplanner.mvp.views;

import com.example.simbirplanner.models.EventInformation;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEnd;

@AddToEnd
public interface DetailEventView extends MvpView {
    void showEventInformation(EventInformation eventInformation);
}
