package com.example.codechallange;

import android.media.Image;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserData {
    private ArrayList<SingleUserInfo> allUserInfo;
    private ArrayList<Image> allUserImages;

    public UserData(String dataResponse) {
        this.allUserInfo = new ArrayList<>();
        try {
            JSONObject jsonUserDataResponse = new JSONObject(dataResponse);
            JSONArray jsonUserDataArray = jsonUserDataResponse.getJSONArray("data");
            for (int i = 0; i < jsonUserDataArray.length(); i++) {
                SingleUserInfo singleUserInfo = new SingleUserInfo(jsonUserDataArray.getJSONObject(i));
                allUserInfo.add(singleUserInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<SingleUserInfo> getAllUserInfo() {
        return allUserInfo;
    }
}
