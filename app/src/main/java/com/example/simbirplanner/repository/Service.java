package com.example.simbirplanner.repository;

import com.example.simbirplanner.OnTransactionCallBack;

import java.util.List;

public interface Service<T> {

    List<T> getAll();
    void add(String title, String description, long position, long dateStart, long dateFinish,
             String imageUrl, OnTransactionCallBack callback);
    List<T> searchByPosition(long position);
    T searchById(String id);
    void deleteById(String id, OnTransactionCallBack callback);
    void editById(String id);


}
