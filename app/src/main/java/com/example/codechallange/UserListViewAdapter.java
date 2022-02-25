package com.example.codechallange;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class UserListViewAdapter extends BaseAdapter {
    ArrayList<SingleUserInfo> userInfoList;
    //ArrayList<Image> userImagelist;
    LayoutInflater layoutInflater;


    UserListViewAdapter(Context context,
                        ArrayList<SingleUserInfo> userInfoList) {

        this.userInfoList = userInfoList;
        //this.userImagelist = userImagelist;
        this.layoutInflater = (LayoutInflater.from(context));

    }

    @Override
    public int getCount() {
        return userInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return userInfoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return userInfoList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = this.layoutInflater.inflate(R.layout.listview_single_row, null);
        TextView tvUserName = (TextView) view.findViewById(R.id.userName);
        TextView tvUserEmail = (TextView) view.findViewById(R.id.email);
        tvUserName.setText(userInfoList.get(i).getFullName());
        tvUserEmail.setText(userInfoList.get(i).getEmail());
        return view;
    }
}
