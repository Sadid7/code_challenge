package com.example.codechallange.datacontroller.remote;

import android.os.AsyncTask;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RemoteDataRetrieveTask extends AsyncTask<Void, Void, Response> {

    private RemoteDataRetrieveListener remoteDataRetrieveListener;
    private OkHttpClient client;
    private Request request;

    public RemoteDataRetrieveTask(String endPointUri, RemoteDataRetrieveListener requestListener) {
        this.remoteDataRetrieveListener = requestListener;
        this.client = new OkHttpClient();
        this.request = new Request.Builder().url(endPointUri).build();
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Response doInBackground(Void... params) {
        Response response;
        try  {
             response = client.newCall(request).execute();
        } catch (Exception exception) {
            return null;
        }
        return response;
    }

    @Override
    protected void onPostExecute(Response response) {
        if (response != null && response.isSuccessful()) {
            try {
                String responseData = response.body().string();
                this.remoteDataRetrieveListener.onRequestSuccess(responseData);
            } catch (IOException e) {
                this.remoteDataRetrieveListener.onRequestError("Could not fetch data");
            }
        } else {
            this.remoteDataRetrieveListener.onRequestError("Could not fetch data");
        }
    }
}
