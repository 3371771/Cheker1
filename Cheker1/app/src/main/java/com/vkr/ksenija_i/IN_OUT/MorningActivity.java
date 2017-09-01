package com.vkr.ksenija_i.IN_OUT;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

public class MorningActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView9;
    final String LOG_TAG = "myLogs";
    String fio, fio2;
    public Db_conn dbConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morning);
        new MyTask().execute();
        imageView = (ImageView) findViewById(R.id.imageView5);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.myanim);
        imageView.startAnimation(anim);

        SharedPreferences pref = getSharedPreferences("main", MODE_PRIVATE);
        fio = pref.getString("saved_name", "").toString();
        fio2 = pref.getString("saved_name2", "").toString();

        textView9 = (TextView) findViewById(R.id.textView9);
        textView9.setText(fio2);
    }

    private class MyTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            dbConn = new Db_conn();
            Connection dbConnection;
            try {
                dbConnection = dbConn.getDBConnection();
                PreparedStatement insert = dbConnection.prepareStatement("INSERT INTO users " + "VALUES (null, ?, curdate(), curtime(),'пришел(-а)')");
                insert.setString(1, fio);
                insert.executeUpdate();

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