package com.vkr.ksenija_i.IN_OUT;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

import com.vkr.ksenija_i.IN_OUT.R;

public class EveningActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView11;
    final String LOG_TAG = "myLogs";
    String fio,fio2;
    public Db_conn dbConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evening);

        new MyTask().execute();

        imageView = (ImageView) findViewById(R.id.imageView);
        Animation anim =  AnimationUtils.loadAnimation(this, R.anim.myanim2);
        imageView.startAnimation(anim);

        //загрузка имен из SharedPreferences с лэйаута Представиться (для вывода и передачи в БД)
        SharedPreferences pref = getSharedPreferences("main", MODE_PRIVATE);
        fio = pref.getString("saved_name", "").toString();
        fio2 = pref.getString("saved_name2", "").toString();

        textView11 = (TextView)findViewById(R.id.textView11);
        textView11.setText(fio2);
    }

    private class MyTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            dbConn = new Db_conn();
            Connection dbConnection;
            try {
                dbConnection  = dbConn.getDBConnection();

                PreparedStatement insert = dbConnection.prepareStatement("INSERT INTO users " + "VALUES (null, ?, curdate(), curtime(),'ушел(-а)')");
                insert.setString(1, fio);
                insert.executeUpdate();
                insert.close();
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
    }
}
