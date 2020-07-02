package com.example.simbirplanner.mvp.views;

import com.example.simbirplanner.models.EventInformation;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {
    @StateStrategyType(OneExecutionStateStrategy.class)
    void openCreateEventScreen();
    @StateStrategyType(OneExecutionStateStrategy.class)
    void successDeleteEvent();
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showErrorDeleteEvent(Throwable throwable);
    void showSelectedEvents (List<EventInformation> events);
    void showAllEvents(List<EventInformation> events);
}
