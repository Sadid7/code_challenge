package com.example.codechallange.datacontroller.local;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LocalDataHandler {
    Context context;
    String fileName;

    public LocalDataHandler(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;
    }

    public String getUserData() {

        String offlineDataString = "";
        try {
            FileInputStream inputStream = context.openFileInput(fileName);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                offlineDataString = stringBuilder.toString();
            }
        } catch (IOException ioException) {
            Log.e("offline data", "Can not read file: " + ioException.toString());
        }
        return offlineDataString;
    }

    public void saveUserData(String userDataResponse) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.
                    openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(userDataResponse);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("offline data", "File write failed: ");
        }
    }
}
