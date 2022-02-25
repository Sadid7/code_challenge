package com.example.codechallange;

public interface DataRequestListener {

     void onRequestStart();
     void onRequestSuccess(String response);
     void onRequestError(String error);

}
