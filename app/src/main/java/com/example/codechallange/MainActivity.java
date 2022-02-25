package com.example.codechallange;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements DataRequestListener, TextWatcher {

    private ProgressDialog progressDialog;
    private DataRequestTask dataRequestTask;
    private TextView tv_user;
    private ListView lv_userList;
    private EditText et_searchList;
    private UserListViewAdapter userLIstVIewAdapter;
    private UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeVIews();
        this.dataRequestTask = new DataRequestTask(getString(R.string.uri),this);
        startDataRequest();
    }

    private void initializeVIews() {
        tv_user = (TextView) findViewById(R.id.userTextView);
        lv_userList = (ListView) findViewById(R.id.user_list_view);
        et_searchList = (EditText) findViewById(R.id.searchUserList);
        et_searchList.addTextChangedListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.dataFetchMessage));
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
        this.userData = new UserData(response);
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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        userLIstVIewAdapter.setUserInfoList( userData.getSearchedUsers(charSequence.toString()));
        userLIstVIewAdapter.notifyDataSetChanged();
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
}