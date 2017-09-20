package com.vkr.ksenija_i.IN_OUT;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class WhoActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edit_who, edit_who2,editText_pass;
    Button btn_save;
    final String LOG_TAG = "myLogs";
    String id,fio,pass,trigger;
    ProgressBar my_bar;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who);

        edit_who = (EditText) findViewById(R.id.editText);
        edit_who2 = (EditText) findViewById(R.id.editText2);
        editText_pass = (EditText) findViewById(R.id.editText_pass);
        btn_save = (Button) findViewById(R.id.button_save);
        btn_save.setOnClickListener(this);
        my_bar = (ProgressBar) findViewById(R.id.my_bar);
    }

    @Override
    public void onClick(View view) {

        if (edit_who.getText().length()==0 || edit_who2.getText().length()==0 || editText_pass.getText().length()==0) {
            Toast.makeText(this, "Все поля необходимо заполнить!", Toast.LENGTH_SHORT).show();
        } else {
            Sharedpref pref = Sharedpref.getInstance(getBaseContext());
            pref.prefClear();

            fio = edit_who.getText().toString();
            pass = editText_pass.getText().toString();

            new MyTask().execute();
            save_name();
            my_bar.setVisibility(View.VISIBLE);
        }
    }

    private void save_name() {
        //сохранить введенное имя
        Sharedpref pref = Sharedpref.getInstance(getBaseContext());
        pref.saveName("saved_name", edit_who.getText().toString());
        pref.saveName("saved_name2", edit_who2.getText().toString());

    }

    private class MyTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

                try {
                    Connection dbConnection = Db_conn.getDBConnection();
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
            my_bar.setVisibility(View.INVISIBLE);
            if (id != null) {
               trigger = "1";
           } else {
               trigger = "0";
           }
            Sharedpref pref = Sharedpref.getInstance(getBaseContext());
            if (trigger.equals("1")) {
                pref.saveToken("1");
                toast = Toast.makeText(WhoActivity.this, "Вы вошли, как " + fio, Toast.LENGTH_SHORT);
                TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                if( v != null) v.setGravity(Gravity.CENTER);
                toast.show();
                Intent intent_back;
                intent_back = new Intent (com.vkr.ksenija_i.IN_OUT.WhoActivity.this,MainActivity.class);
                startActivity(intent_back);
            } else {
                pref.saveToken("0");
                toast = Toast.makeText(WhoActivity.this, "Неверный логин или пароль", Toast.LENGTH_SHORT);
                TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                if( v != null) v.setGravity(Gravity.CENTER);
                toast.show();
                pref.prefClear();
            }
            super.onPostExecute(s);
        }
    }
}
