package com.example.codechallange.datacontroller.remote;

public interface RemoteDataRetrieveListener {
     void onRequestStart();
     void onRequestSuccess(String response);
     void onRequestError(String error);
}
