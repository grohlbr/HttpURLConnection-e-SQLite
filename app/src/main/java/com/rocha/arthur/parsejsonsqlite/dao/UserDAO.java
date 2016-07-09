package com.rocha.arthur.parsejsonsqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rocha.arthur.parsejsonsqlite.database.DBHelper;
import com.rocha.arthur.parsejsonsqlite.models.User;

import java.util.ArrayList;

/**
 * Created by arthur on 06/07/16.
 */

public class UserDAO {
    private Context context;
    private DBHelper dbHelper;

    public static final String sqlCreateTable =
            "CREATE TABLE USERS (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "NAME TEXT," +
            "USERNAME TEXT,"+
            "EMAIL TEXT," +
            "ADDRESS TEXT)";

    public UserDAO(Context context){
        this.context = context;
        this.dbHelper = new DBHelper(this.context);
    }

    public Boolean save(ArrayList<User> users){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            for (int i = 0; i < users.size(); i++){
                ContentValues values = new ContentValues();
                values.put("NAME", users.get(i).getName());
                values.put("USERNAME", users.get(i).getUsername());
                values.put("EMAIL", users.get(i).getEmail());
                values.put("ADDRESS", users.get(i).getAddress());

                database.insertOrThrow("USERS", null, values);
                Log.i("DATABASE", "INSERT: "+ users.get(i).getName());
            }
        }catch (Throwable e){
            throw e;
        }finally {
            database.close();
        }
        return true;
    }

    public ArrayList<User> getAll(){
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String[] colums = new String[]{"ID", "NAME", "USERNAME", "EMAIL", "ADDRESS"};
        String where = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null; // "MARCA ASC"...
        String limit = null;

        Cursor cursor = database.query("USERS",colums,where,whereArgs,groupBy,having,orderBy,limit);

        ArrayList<User> users = new ArrayList<>();

        if(cursor.moveToFirst()){
            do {
                users.add(getUserByCursor(cursor));
            }while (cursor.moveToNext());
        }

        return users;

    }

    public void clearTableUsers(){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete("USERS", null, null);
    }

    private User getUserByCursor(Cursor cursor){
        User user = new User();
        user.setId(cursor.getLong(cursor.getColumnIndex("ID")));
        user.setName(cursor.getString(cursor.getColumnIndex("NAME")));
        user.setUsername(cursor.getString(cursor.getColumnIndex("USERNAME")));
        user.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
        user.setAddress(cursor.getString(cursor.getColumnIndex("ADDRESS")));

        return user;
    }


}
