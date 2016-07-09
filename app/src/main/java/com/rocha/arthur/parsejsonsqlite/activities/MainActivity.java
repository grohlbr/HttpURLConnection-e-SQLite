package com.rocha.arthur.parsejsonsqlite.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.rocha.arthur.parsejsonsqlite.R;
import com.rocha.arthur.parsejsonsqlite.adapters.AdapterUser;
import com.rocha.arthur.parsejsonsqlite.dao.UserDAO;
import com.rocha.arthur.parsejsonsqlite.models.User;
import com.rocha.arthur.parsejsonsqlite.web.ConnectionUtil;
import com.rocha.arthur.parsejsonsqlite.web.UserHttp;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private Exception exception;
    private ListView lvUsers;
    private GetDataAsync getDataAsync;
    private ArrayList<User> listUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvUsers = (ListView) findViewById(R.id.lvUsers);

        getAllSQLite();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_refresh){
            getData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getData(){
        if(ConnectionUtil.hasConnection(this)){
            getDataAsync = new GetDataAsync();
            getDataAsync.execute();
        }else{
            Toast.makeText(this, "Sem internet", Toast.LENGTH_LONG).show();
        }

    }

    class GetDataAsync extends AsyncTask<Void, Void, ArrayList<User>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Buscando no Servidor...");
            progressDialog.show();
        }

        @Override
        protected ArrayList<User> doInBackground(Void... params) {
            UserHttp userHttp = new UserHttp();
            try {
                return userHttp.getUser();
            }catch (Exception e){
                exception = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<User> users) {
            super.onPostExecute(users);
            progressDialog.dismiss();
            if(exception != null){
                Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
            }else{
                if(users != null){
                    lvUsers.setAdapter(new AdapterUser(users, MainActivity.this));
                    onSaveDataSQLite(users);
                }else{
                    Toast.makeText(MainActivity.this, "Nenhum dado no Servidor", Toast.LENGTH_LONG).show();
                }

            }
        }
    }

    private void getAllSQLite(){
        UserDAO userDAO = new UserDAO(this);

        listUsers = userDAO.getAll();

        if(listUsers.size() > 0){
            lvUsers.setAdapter(new AdapterUser(listUsers, MainActivity.this));
            Toast.makeText(MainActivity.this, "Buscou pelo SQLite", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(MainActivity.this, "Nenhum dado no SQLite", Toast.LENGTH_LONG).show();
        }
    }

    private void onSaveDataSQLite(ArrayList<User> users){
        UserDAO userDAO = new UserDAO(this);
        userDAO.clearTableUsers();

        if(userDAO.save(users)){
            Toast.makeText(MainActivity.this, "Salvo no SQLite", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(MainActivity.this, "Erro ao salvar no SQLite", Toast.LENGTH_LONG).show();
        }
    }
}
