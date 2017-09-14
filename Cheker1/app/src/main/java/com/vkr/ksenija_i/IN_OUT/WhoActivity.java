package com.vkr.ksenija_i.IN_OUT;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class WhoActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edit_who, edit_who2,editText_pass;
    Button btn_save;
    public Db_conn dbConn;
    final String LOG_TAG = "myLogs";
    String id,fio,pass,trigger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who);
        edit_who = (EditText) findViewById(R.id.editText);
        edit_who2 = (EditText) findViewById(R.id.editText2);
        editText_pass = (EditText) findViewById(R.id.editText_pass);
        btn_save = (Button) findViewById(R.id.button_save);
        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        SharedPreferences pref = getSharedPreferences("main", MODE_PRIVATE);
        SharedPreferences.Editor ed = pref.edit();
        ed.clear().apply();

        fio = edit_who.getText().toString();
        pass = editText_pass.getText().toString();

        new MyTask().execute();
        save_name();
    }

    private void save_name() {
        //сохранить введенное имя
        SharedPreferences pref = getSharedPreferences("main", MODE_PRIVATE);
        SharedPreferences.Editor ed = pref.edit();
        ed.putString("saved_name", edit_who.getText().toString());
        ed.putString("saved_name2", edit_who2.getText().toString());
        ed.apply();
    }

    private class MyTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
                dbConn = new Db_conn();
                Connection dbConnection;

                try {
                    dbConnection = dbConn.getDBConnection();

                    PreparedStatement select = dbConnection.prepareStatement("SELECT id FROM user_data WHERE ФИО = ? AND пароль = ?");
                    select.setString(1, fio);
                    select.setString(2, pass);

                    ResultSet rs = select.executeQuery();

                    while (rs.next()) {
                        id = rs.getString("id");
                    }
                    select.close();
                    dbConnection.close();

                    Log.d(LOG_TAG, "есть подключение к БД");
                } catch (java.sql.SQLException e) {
                    Log.d(LOG_TAG, "ошибка подключения к БД");
                    Log.d(LOG_TAG, e.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

        @Override
        protected  void onPostExecute(String s) { // свитч 1 есть в Бд 2 нет в БД 3 админ
            // (либо вводить еще одну колонку в БД с тригером (проверять не по ай ди а по нему), либо сделать админа с №1 ай ди)
            // дальше настроить Флоат бтн по тригеру
           if (id != null) {
               trigger = "1";
           } else {
               trigger = "3";
           }
            SharedPreferences pref = getSharedPreferences("main", MODE_PRIVATE);
            SharedPreferences.Editor ed = pref.edit();
            ed.putString("trigger", trigger.toString());
            ed.apply();

            if (trigger.equals("1")) {
                Toast.makeText(WhoActivity.this, "Вы вошли, как " + fio, Toast.LENGTH_SHORT).show();
                Intent intent_back;
                intent_back = new Intent (com.vkr.ksenija_i.IN_OUT.WhoActivity.this,MainActivity.class);
                startActivity(intent_back);
            } else {
                Toast.makeText(WhoActivity.this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                ed.clear().apply();
            }
            super.onPostExecute(s);
        }
    }
}
