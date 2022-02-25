package com.example.codechallange;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements DataRequestListener{

    private ProgressDialog progressDialog;
    private DataRequestTask dataRequestTask;
    private TextView tv_user;
    private ListView lv_userList;
    private UserListViewAdapter userLIstVIewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_user = (TextView) findViewById(R.id.userTextView);
        lv_userList = (ListView) findViewById(R.id.user_list_view);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.dataFetchMessage));
        this.dataRequestTask = new DataRequestTask(getString(R.string.uri),this);
        startDataRequest();
    }

    private void startDataRequest() {
        this.dataRequestTask.execute();
    }



    @Override
    public void onRequestStart() {
        this.progressDialog.show();

    }

    @Override
    public void onRequestSuccess(String response) {
        this.progressDialog.dismiss();
        UserData userData = new UserData(response);
        showUserList(userData);
    }

    @Override
    public void onRequestError(String errorMessage) {
        this.progressDialog.dismiss();

    }

    private void showUserList(UserData userData) {
        this.userLIstVIewAdapter = new UserListViewAdapter(this,userData.getAllUserInfo());
        lv_userList.setAdapter(userLIstVIewAdapter);
    }
}