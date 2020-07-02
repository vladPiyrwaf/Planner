package com.example.simbirplanner.mvp.views;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import moxy.viewstate.strategy.alias.AddToEnd;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface CreateEventView extends MvpView {
    void showTimePickerDialogStart();
    void showTimePickerDialogFinish();
    void successSaveEvent();
    @StateStrategyType(AddToEndStrategy.class)
    void hideTimePicker();
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showErrorInput(Integer titleError, Integer descriptionError);
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showErrorCreate(String message);
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showHideProgressBar(boolean visibility);
    @StateStrategyType(OneExecutionStateStrategy.class)
    void clickableButton(boolean clickable);
}
