package com.example.codechallange;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.codechallange.api.DataRequestListener;
import com.example.codechallange.api.DataRequestTask;
import com.example.codechallange.models.AllUserData;

public class MainActivity extends AppCompatActivity implements DataRequestListener,
        TextWatcher,
        DialogInterface.OnClickListener {

    private ProgressDialog progressDialog;
    private ListView lv_userList;
    private TextView tv_online_status;
    private UserListViewAdapter userLIstVIewAdapter;
    private AllUserData allUserData;
    private OfflineDataHandler offlineDataHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeVIews();
        setOnlineStatus();
        loadData();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        setOnlineStatus();
    }

    private void initializeVIews() {
        EditText et_searchList = (EditText) findViewById(R.id.searchUserLEditText);
        et_searchList.addTextChangedListener(this);
        tv_online_status = (TextView) findViewById(R.id.userStatusTextView);
        lv_userList = (ListView) findViewById(R.id.userListView);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.dataFetchMessage));
        progressDialog.setCancelable(false);
    }

    private void setOnlineStatus() {
        if (Utils.isInternetAvailable(this)) {
            tv_online_status.setText("ONLINE");
            tv_online_status.setTextColor(Color.GREEN);
        } else {
            tv_online_status.setText("OFFLINE");
            tv_online_status.setTextColor(Color.RED);
        }
    }

    private void loadData() {
        /**checks availability of offline data or internet access */

        if (Utils.isInternetAvailable(this)) {
            startDataRequest();
        } else if (Utils.isOfflineDataAvailable(this,getString(R.string.offlineUserData))) {
            this.offlineDataHandler = new OfflineDataHandler(this
                    , getString(R.string.offlineUserData));
            this.allUserData = new AllUserData(offlineDataHandler.getUserData());
            showUserList();
        } else {
            Utils.showErrorDialog(this,
                    "Data Not Found",
                    this)
                    .show();
        }
    }

    private void startDataRequest() {
        DataRequestTask dataRequestTask = new DataRequestTask(getString(R.string.uri),
                this);
        dataRequestTask.execute();
    }

    @Override
    public void onRequestStart() {
        this.progressDialog.show();
    }

    @Override
    public void onRequestSuccess(String response) {
        this.progressDialog.dismiss();
        this.allUserData = new AllUserData(response);
        showUserList();
        this.offlineDataHandler = new OfflineDataHandler(this
                , getString(R.string.offlineUserData));
        offlineDataHandler.saveUserData(response);
    }

    @Override
    public void onRequestError(String errorMessage) {
        this.progressDialog.dismiss();
        Utils.showErrorDialog(this,
                "Couldn't Fetch Data",
                this).show();
    }

    private void showUserList() {
        this.userLIstVIewAdapter = new UserListViewAdapter(this,
                allUserData.getAllUserInfo());
        lv_userList.setAdapter(userLIstVIewAdapter);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }
    /**shows searched users */
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        userLIstVIewAdapter.setUserInfoList( allUserData.getSearchedUsers(charSequence.toString()));
        userLIstVIewAdapter.notifyDataSetChanged();
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
    /**Onclicklistener for error alert dialog */
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        loadData();
    }
}