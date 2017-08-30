package com.exemple.kiselrv.myapplication1707;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

public class EveningActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView11;
    final String LOG_TAG = "myLogs";
    String fio,fio2;

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
//            try {
//                Class.forName("com.mysql.jdbc.Driver");
//                Log.d(LOG_TAG, "--- Driver is conected!!! ---");
//            } catch (ClassNotFoundException e) {
//                Log.d(LOG_TAG, "--- Driver is NOT conected!!! ---");
//                Log.d(LOG_TAG, e.toString());
//                e.printStackTrace();
//                return null;
//            }
            Connection con;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Properties properties=new Properties();
                properties.setProperty("user","***"); //user
                properties.setProperty("password","***"); //user
                properties.setProperty("useUnicode","true");
                properties.setProperty("characterEncoding","UTF-8");
                con = DriverManager.getConnection("jdbc:mysql://***", properties);
//185.26.122.51
                //con = DriverManager.getConnection("jdbc:mysql://192.168.0.2:3306/cheker", properties); домашняя БД

                PreparedStatement insert = con.prepareStatement("INSERT INTO users " + "VALUES (null, ?, curdate(), curtime(),'ушел(-а)')");
                insert.setString(1, fio);
                insert.executeUpdate();
                insert.close();
                con.close();

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
