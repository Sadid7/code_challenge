package com.example.codechallange.view;

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

import com.example.codechallange.R;
import com.example.codechallange.Utils;
import com.example.codechallange.datacontroller.DataController;
import com.example.codechallange.datacontroller.DataRetrieveListener;

public class MainActivity extends AppCompatActivity implements DataRetrieveListener,
        TextWatcher,
        DialogInterface.OnClickListener {

    private ProgressDialog progressDialog;
    private ListView lv_userList;
    private TextView tv_online_status;
    private UserListViewAdapter userLIstVIewAdapter;
    private DataController dataController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeVIews();
        setOnlineStatus();
        initiateDataHnadler();
        this.progressDialog.show();
        dataController.loadData();
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
        progressDialog = Utils.getProgreesDialog(this, getString(R.string.dataFetchMessage));
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

    private void initiateDataHnadler() {
        this.dataController = new DataController(this,
                getString(R.string.uri),
                getString(R.string.offlineUserData), this);
    }

    @Override
    public void onRetrieveStart() {
        this.progressDialog.show();
    }

    @Override
    public void onRetrieveSuccess() {
        this.progressDialog.dismiss();
        showUserList();
    }

    @Override
    public void onRetrieveError(String error) {
        this.progressDialog.dismiss();
        Utils.showErrorDialog(this,
                error,
                this).show();
    }

    private void showUserList() {
        this.userLIstVIewAdapter = new UserListViewAdapter(this,
                dataController.getAllUserInfo());
        lv_userList.setAdapter(userLIstVIewAdapter);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }
    /**shows searched users */
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        userLIstVIewAdapter.setUserInfoList( dataController.getSearchedUsers(charSequence.toString()));
        userLIstVIewAdapter.notifyDataSetChanged();
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
    /**Onclicklistener for error alert dialog */
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        dataController.loadData();
    }


}