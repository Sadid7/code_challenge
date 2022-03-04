package com.example.codechallange.datacontroller;

import com.example.codechallange.models.SingleUserData;

import java.util.ArrayList;

public interface DataRetrieveListener {
    void onRetrieveStart();
    void onRetrieveSuccess();
    void onRetrieveError(String error);

}
