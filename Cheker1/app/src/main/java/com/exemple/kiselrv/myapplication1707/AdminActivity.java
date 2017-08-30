package com.exemple.kiselrv.myapplication1707;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AdminActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public MyAdapter adapter;
    public List<Item> items; //поменяна с прайвета и те, что выше тоже
    final String LOG_TAG = "myLogs";
    public Integer sizeItems1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        items = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new MyTask().execute();
    }

    private class MyTask extends AsyncTask<Void, Void, List> {

        private List<Item> items1 = new ArrayList<>();

        @Override
        protected List doInBackground(Void... params) {
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
                //  получение массива до st.close();
                // Statement st = con.createStatement();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT fio, DATE_FORMAT(дата, '%d.%m.%Y'), время, вход FROM users ORDER BY id DESC LIMIT 0,5");
                // в лимите пишется сколько отобразить записей
                while (rs.next()) {
                    items1.add(new Item(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
                }
                // st.close();
                // con.close();

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
        protected void onPostExecute(List result) {
           sizeItems1 = items1.size(); //тут передаем все записи из массива вне потока в поток и потом в адаптер
           for (int i = 0; i < sizeItems1; i++) {
               items.add(new Item(items1.get(i).getFio(),items1.get(i).getDate(),items1.get(i).getTime(),items1.get(i).getVhod()));
            }
        //    Collections.copy(items, items1); //коприрование, но не работает
            adapter = new MyAdapter(items, com.exemple.kiselrv.myapplication1707.AdminActivity.this);
            recyclerView.setAdapter(adapter);
            super.onPostExecute(result);
        }
    }
}
