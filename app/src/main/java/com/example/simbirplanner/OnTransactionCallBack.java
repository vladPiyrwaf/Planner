package com.example.simbirplanner;

public interface OnTransactionCallBack {
    void onSuccess();
    void onError(final Throwable error);
}
