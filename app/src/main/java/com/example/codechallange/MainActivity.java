package com.example.codechallange;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DataRequestListener{

    private ProgressDialog progressDialog;
    private DataRequestTask dataRequestTask;
    private TextView user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (TextView) findViewById(R.id.userTextView);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.dataFetchMessage));
        this.dataRequestTask = new DataRequestTask(getString(R.string.uri),this);
        startDataRequest();


    }

    private void startDataRequest() {
        this.dataRequestTask.execute();
    }

    @Override
    public void onClick(View view) {
        startDataRequest();
    }

    @Override
    public void onRequestStart() {
        this.progressDialog.show();

    }

    @Override
    public void onRequestSuccess(String response) {
        this.progressDialog.dismiss();
        user.setText(response);
        UserData userData = new UserData(response);

    }

    @Override
    public void onRequestError(String errorMessage) {
        this.progressDialog.dismiss();

    }
}