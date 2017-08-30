package com.exemple.kiselrv.myapplication1707;

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
    String fio,fio2;

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

        textView9 = (TextView)findViewById(R.id.textView9);
        textView9.setText(fio2);
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
                properties.setProperty("user","host1213291_test"); //user
                properties.setProperty("password","NdxW0YY2"); //user
                properties.setProperty("useUnicode","true");
                properties.setProperty("characterEncoding","UTF-8");
                con = DriverManager.getConnection("jdbc:mysql://mysql51.hostland.ru/host1213291_test", properties);
//185.26.122.51
                //

                PreparedStatement insert = con.prepareStatement("INSERT INTO users " + "VALUES (null, ?, curdate(), curtime(),'пришел(-а)')");
                insert.setString(1, fio);
                insert.executeUpdate();

//                Statement st = con.createStatement();
//                final ResultSet rs = st.executeUpdate( "INSERT FROM users + fio");

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