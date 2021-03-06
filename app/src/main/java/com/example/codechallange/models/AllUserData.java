package com.example.codechallange.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllUserData {
    private ArrayList<SingleUserData> allUserInfo;

    public AllUserData(String dataResponse) {
        this.allUserInfo = new ArrayList<>();
        try {
            JSONObject jsonUserDataResponse = new JSONObject(dataResponse);
            JSONArray jsonUserDataArray = jsonUserDataResponse.getJSONArray("data");
            for (int i = 0; i < jsonUserDataArray.length(); i++) {
                SingleUserData singleUserData = new SingleUserData(jsonUserDataArray.getJSONObject(i));
                allUserInfo.add(singleUserData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public ArrayList<SingleUserData> getAllUserInfo() {
        return allUserInfo;
    }
}
