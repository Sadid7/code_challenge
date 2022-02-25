package com.example.codechallange;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class SingleUserInfo {

    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String imageUri;

    public SingleUserInfo(JSONObject jsonSingleUserINfo){
        try {
            this.id = jsonSingleUserINfo.getInt("id");
            this.email = jsonSingleUserINfo.getString("email");
            this.firstName = jsonSingleUserINfo.getString("first_name");
            this.lastName = jsonSingleUserINfo.getString("last_name");
            this.imageUri = jsonSingleUserINfo.getString("avatar");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public int getId() {

        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getImageUri() {
        return imageUri;
    }
}
