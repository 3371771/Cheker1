package com.vkr.ksenija_i.IN_OUT;

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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class WhoActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edit_who, edit_who2;
    TextView text_test;
    Button btn_save;
    public Db_conn dbConn;
    final String LOG_TAG = "myLogs";
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who);
        edit_who = (EditText) findViewById(R.id.editText);
        edit_who2 = (EditText) findViewById(R.id.editText2);
        text_test = (TextView) findViewById(R.id.text_test);

        btn_save = (Button) findViewById(R.id.button_save);
        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //save_name();
        new MyTask().execute();
    }

    private void save_name() {
        //сохранить введенное имя
        SharedPreferences pref = getSharedPreferences("main", MODE_PRIVATE);
        SharedPreferences.Editor ed = pref.edit();
        ed.putString("saved_name", edit_who.getText().toString());
        ed.putString("saved_name2", edit_who2.getText().toString());
        ed.apply();

        Toast.makeText(WhoActivity.this, "Данные сохранены", Toast.LENGTH_SHORT).show();
    }

    private class MyTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
                dbConn = new Db_conn();
                Connection dbConnection;
                try {
                    dbConnection = dbConn.getDBConnection();

                    Statement st = dbConnection.createStatement();
                    ResultSet rs = st.executeQuery("SELECT id FROM user_data WHERE ФИО = 'Иванов Иван Иванович' AND пароль = '123456'");

                    while (rs.next()) {
                        id = rs.getString("id");
                    }

                    st.close();
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
        protected void onPostExecute(String s) { // свитч 1 есть в Бд 2 нет в БД 3 админ
            // (либо вводить еще одну колонку в БД с тригером (проверять не по ай ди а по нему), либо сделать с 1 ай ди)
            // дальше настроить Флоат бтн по тригеру
            if (id != null) {
                text_test.setText("есть");
            } else text_test.setText("нет");
            super.onPostExecute(s);
        }
    }
}
