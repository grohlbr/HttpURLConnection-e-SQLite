package com.rocha.arthur.parsejsonsqlite.web;

import com.rocha.arthur.parsejsonsqlite.models.Address;
import com.rocha.arthur.parsejsonsqlite.models.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by arthur on 07/07/16.
 */
public class UserHttp {
    private static String URL_USERS = "http://jsonplaceholder.typicode.com/users";

    public ArrayList<User> getUser() throws Exception {
        HttpURLConnection httpURLConnection = ConnectionUtil.connect(URL_USERS, "GET", true, false, null);
        ArrayList<User> userList = new ArrayList<>();

        int responseServer = httpURLConnection.getResponseCode();
        if (responseServer == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = httpURLConnection.getInputStream();
            JSONArray jsonUsers = new JSONArray(ConnectionUtil.bytesForString(inputStream));

            for (int i = 0; i < jsonUsers.length(); i++){
                JSONObject jsonUser = jsonUsers.getJSONObject(i);
                JSONObject jsonAddress = jsonUser.getJSONObject("address");
                Address address = new Address(
                        jsonAddress.getString("street"),
                        jsonAddress.getString("suite"),
                        jsonAddress.getString("city"),
                        jsonAddress.getString("zipcode")
                );

                User user = new User(
                        jsonUser.getLong("id"),
                        jsonUser.getString("name"),
                        jsonUser.getString("username"),
                        jsonUser.getString("email"),
                        address.getAddress()
                );
                userList.add(user);
            }
            return userList;
        }
        return null;
    }

}
