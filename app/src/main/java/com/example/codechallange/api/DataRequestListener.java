package com.example.codechallange.api;

public interface DataRequestListener {
     void onRequestStart();
     void onRequestSuccess(String response);
     void onRequestError(String error);
}
