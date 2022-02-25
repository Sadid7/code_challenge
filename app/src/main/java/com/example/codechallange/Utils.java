package com.example.codechallange;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Utils {
    public static AlertDialog getAlertDialog(Context context,
                                             String title,
                                             String message,
                                             DialogInterface.OnClickListener listener, String buttonName) {
        return new AlertDialog.Builder(context).
                setTitle(title).
                setMessage(message)
                .setNeutralButton(buttonName,listener).
                        create();

    }

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static boolean isOfflineDataAvailable(Context context, String fileName) {
        try {
            FileInputStream inputStream = context.openFileInput(fileName);
            if (inputStream != null) {
                return true;
            } else {
                return false;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
