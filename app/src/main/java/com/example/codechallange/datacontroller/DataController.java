package com.example.codechallange.datacontroller;

import android.content.Context;

import com.example.codechallange.Utils;
import com.example.codechallange.datacontroller.local.LocalDataHandler;
import com.example.codechallange.datacontroller.remote.RemoteDataRetrieveListener;
import com.example.codechallange.datacontroller.remote.RemoteDataRetrieveTask;
import com.example.codechallange.models.AllUserData;
import com.example.codechallange.models.SingleUserData;

import java.util.ArrayList;

public class DataController implements RemoteDataRetrieveListener {

    LocalDataHandler localDataHandler;
    AllUserData allUserData;
    DataRetrieveListener dataRetrieveListener;
    Context context;
    String uri;
    String userDataFile;
    public DataController(Context context, String uri, String userDataFile, DataRetrieveListener dataRetrieveListener) {
        this.context = context;
        this.uri = uri;
        this.userDataFile = userDataFile;
        this.dataRetrieveListener = dataRetrieveListener;
    }

    public void loadData() {
        /**checks availability of offline data or internet access */
        dataRetrieveListener.onRetrieveStart();
        if (Utils.isInternetAvailable(context)) {
            startDataRequest(uri);
        } else if (Utils.isOfflineDataAvailable(context,userDataFile)) {
            this.localDataHandler = new LocalDataHandler(context
                    , userDataFile);
            this.allUserData = new AllUserData(localDataHandler.getUserData());
            dataRetrieveListener.onRetrieveSuccess();
        } else {
            dataRetrieveListener.onRetrieveError("Data Not Found");
        }
    }

    private void startDataRequest(String uri) {
        RemoteDataRetrieveTask remoteDataRetrieveTask = new RemoteDataRetrieveTask(uri,
                this);
        remoteDataRetrieveTask.execute();
    }


    @Override
    public void onRequestStart() {
        dataRetrieveListener.onRetrieveStart();
    }

    @Override
    public void onRequestSuccess(String response) {
        this.allUserData = new AllUserData(response);
        saveData(response);
        dataRetrieveListener.onRetrieveSuccess();

    }

    @Override
    public void onRequestError(String errorMessage) {
        dataRetrieveListener.onRetrieveError(errorMessage);
    }

    private void saveData(String response) {
        this.localDataHandler = new LocalDataHandler(context
                , userDataFile);
        localDataHandler.saveUserData(response);

    }

    public ArrayList<SingleUserData> getSearchedUsers(String searchString) {

        searchString = searchString.trim();
        ArrayList<SingleUserData> searchedUserList= new ArrayList<>();
        for(SingleUserData singleUserData : allUserData.getAllUserInfo()){
            if(singleUserData.getFullName() != null
                    && singleUserData.getFullName().toLowerCase().contains(searchString.toLowerCase())) {
                searchedUserList.add(singleUserData);
            }
        }
        return searchedUserList;
    }

    public ArrayList<SingleUserData> getAllUserInfo() {
        return allUserData.getAllUserInfo();
    }
}
