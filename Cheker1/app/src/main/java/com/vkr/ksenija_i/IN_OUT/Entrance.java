package com.vkr.ksenija_i.IN_OUT;

<<<<<<< HEAD:Cheker1/app/src/main/java/com/vkr/ksenija_i/IN_OUT/Entrance.java
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Entrance extends AppCompatActivity {

    ImageView imageView;
    TextView textView9;
    final String LOG_TAG = "myLogs";
    String fio, fio2,toDo;
    Animation anim;
=======
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MorningActivity extends Entrance {
>>>>>>> 5d9f2bcdcd05b0b6b6df46b37074d0a4138b6632:Cheker1/app/src/main/java/com/vkr/ksenija_i/IN_OUT/MorningActivity.java

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
<<<<<<< HEAD:Cheker1/app/src/main/java/com/vkr/ksenija_i/IN_OUT/Entrance.java
    }

    protected void start() {
        imageView.startAnimation(anim);
        new MyTask().execute();

        Sharedpref pref = Sharedpref.getInstance(getBaseContext());
        fio = pref.getName("saved_name", "");
        fio2 = pref.getName("saved_name2", "");
        textView9.setText(fio2);
    }

    public class MyTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                Connection dbConnection =  Db_conn.getDBConnection();
                PreparedStatement insert = dbConnection.prepareStatement("INSERT INTO users " + "VALUES (null, ?, curdate(), curtime(),?)");
                insert.setString(1, fio);
                insert.setString(2, toDo);
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

    @Override
    public void onBackPressed() {
        Intent intent_back;
        intent_back = new Intent (this,MainActivity.class);
        startActivity(intent_back);
=======
        setContentView(R.layout.activity_morning);

        toDo = "пришел(-а)";
        imageView = (ImageView) findViewById(R.id.imageView5);
        anim = AnimationUtils.loadAnimation(this, R.anim.myanim);
        textView9 = (TextView) findViewById(R.id.textView9);
        start();
>>>>>>> 5d9f2bcdcd05b0b6b6df46b37074d0a4138b6632:Cheker1/app/src/main/java/com/vkr/ksenija_i/IN_OUT/MorningActivity.java
    }
}

