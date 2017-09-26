package com.vkr.ksenija_i.IN_OUT;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import android.support.v7.widget.CardView;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Item> items;
    private Context mContext;
    final String LOG_TAG = "myLogs";
    String fio1, tel;


    public MyAdapter(List<Item> items, Context mContext) { //конструктор
        this.items = items;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Item itemList = items.get(position);
        holder.fio.setText(itemList.getFio());
        holder.date.setText(itemList.getDate());
        holder.time.setText(itemList.getTime());
        holder.vhod.setText(itemList.getVhod());
        //holder.card_view.setClickable(true);

        // изменения цвета бэкграунда в зависисмости от того выход или вход
//        String condition = itemList.getVhod();
//        switch (condition){
//            case ("вошел"): holder.card_view.setCardBackgroundColor(0xFFba68c8);
//                break;
//            case ("вышел"): holder.card_view.setCardBackgroundColor(0xFFe57373);
//                break;
//        }

        String condition = itemList.getVhod(); // изменения цвета текста в зависисмости от того выход или вход
        switch (condition) {
            case ("ушел(-а)"):
                holder.vhod.setTextColor(0xFFd50000);
                break;
            case ("пришел(-а)"):
                holder.vhod.setTextColor(0xFF1b5e20);
                break;
        }

        holder.card_view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                fio1 = itemList.getFio();
                new MyTask().execute();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView card_view;

        public TextView fio;
        public TextView date;
        public TextView time;
        public TextView vhod;

        public ViewHolder(View itemView) {
            super(itemView);
            card_view = (CardView) itemView.findViewById(R.id.card);
            fio = (TextView) itemView.findViewById(R.id.fio);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);
            vhod = (TextView) itemView.findViewById(R.id.vhod);
        }
    }


    private class MyTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {

            try {
                Connection dbConnection = Db_conn.getDBConnection();
                PreparedStatement select = dbConnection.prepareStatement("SELECT телефон FROM user_data WHERE ФИО = ?");
                select.setString(1, fio1);

                ResultSet rs = select.executeQuery();

                while (rs.next()) {
                    tel = rs.getString("телефон");
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
        protected void onPostExecute(String s) {
            Intent call;
            Uri number = Uri.parse("tel:" + tel);
            call = new Intent(Intent.ACTION_DIAL, number);
            mContext.startActivity(call);
        }
    }
}


