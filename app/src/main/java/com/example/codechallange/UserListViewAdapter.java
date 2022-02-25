package com.example.codechallange;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.codechallange.models.SingleUserData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserListViewAdapter extends BaseAdapter {
    ArrayList<SingleUserData> userInfoList;
    LayoutInflater layoutInflater;

    UserListViewAdapter(Context context,
                        ArrayList<SingleUserData> userInfoList) {
        this.userInfoList = userInfoList;
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
        TextView tvUserName = (TextView) view.findViewById(R.id.userNameTextView);
        TextView tvUserEmail = (TextView) view.findViewById(R.id.userEmailTextVIew);
        ImageView ivUserImage = (ImageView) view.findViewById(R.id.userImageVIew);
        tvUserName.setText(userInfoList.get(i).getFullName());
        tvUserEmail.setText(userInfoList.get(i).getEmail());
        Picasso.get()
                .load(userInfoList.get(i).getImageUri())
                .resize(350,350)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(ivUserImage);
        return view;
    }

    public void setUserInfoList(ArrayList<SingleUserData> userInfoList) {
        this.userInfoList = userInfoList;
    }
}
