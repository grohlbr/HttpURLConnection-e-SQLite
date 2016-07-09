package com.rocha.arthur.parsejsonsqlite.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rocha.arthur.parsejsonsqlite.R;
import com.rocha.arthur.parsejsonsqlite.models.User;

import java.util.ArrayList;

/**
 * Created by arthur on 07/07/16.
 */

public class AdapterUser extends BaseAdapter {
    private ArrayList<User> userList;
    private Context context;

    public AdapterUser(ArrayList<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.list_item_user,null);

        if(convertView != null){
            TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
            TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
            TextView tvEmail = (TextView) convertView.findViewById(R.id.tvEmail);
            TextView tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);

            User user = userList.get(position);
            tvName.setText(user.getName());
            tvUserName.setText(user.getUsername());
            tvEmail.setText(user.getEmail());


            tvAddress.setText(user.getAddress());

        }

        return convertView;
    }
}
