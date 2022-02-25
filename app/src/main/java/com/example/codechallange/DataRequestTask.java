package com.example.codechallange;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DataRequestTask extends AsyncTask<Void, Void, Response> {

    private DataRequestListener dataRequestListener;
    private OkHttpClient client;
    private Request request;

    public DataRequestTask(String endPointUri, DataRequestListener requestListener) {
        this.dataRequestListener = requestListener;
        this.client = new OkHttpClient();
        this.request = new Request.Builder().url(endPointUri).build();

    }

    @Override
    protected void onPreExecute() {
        this.dataRequestListener.onRequestStart();
    }

    @Override
    protected Response doInBackground(Void... params) {
        Response response;
        try  {
             response = client.newCall(request).execute();

        } catch (Exception exception) {
            Log.d("Error", exception.getMessage());
            return null;
        }
        Log.e("sadid",response.message());
        return response;
    }

    @Override
    protected void onPostExecute(Response response) {

        if (response != null && response.isSuccessful()) {
            try {
                String responseData = response.body().string();
                this.dataRequestListener.onRequestSuccess(responseData);
            } catch (IOException e) {
                this.dataRequestListener.onRequestError(e.getMessage());
            }
        } else {

            this.dataRequestListener.onRequestError("sadsad");
        }
    }
}
