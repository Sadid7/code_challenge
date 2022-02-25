package com.example.codechallange;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserData {
    private ArrayList<SingleUserInfo> allUserData;


    public UserData(String dataResponse) {
        this.allUserData = new ArrayList<>();
        try {
            JSONObject jsonUserDataResponse = new JSONObject(dataResponse);
            JSONArray jsonUserDataArray = jsonUserDataResponse.getJSONArray("data");
            for (int i = 0; i < jsonUserDataArray.length(); i++) {
                SingleUserInfo singleUserInfo = new SingleUserInfo(jsonUserDataArray.getJSONObject(i));
                allUserData.add(singleUserInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<SingleUserInfo> getAllUserData() {
        return allUserData;
    }
}
