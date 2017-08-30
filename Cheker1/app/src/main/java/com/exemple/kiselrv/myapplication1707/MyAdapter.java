package com.exemple.kiselrv.myapplication1707;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Item> items;
    private Context mContext;

    public MyAdapter(List<Item> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Item itemList = items.get(position);
        holder.fio.setText(itemList.getFio());
        holder.date.setText(itemList.getDate());
        holder.time.setText(itemList.getTime());
        holder.vhod.setText(itemList.getVhod());
       // изменения цвета бэкграунда в зависисмости от того выход или вход
//        String condition = itemList.getVhod();
//        switch (condition){
//            case ("вошел"): holder.card_view.setCardBackgroundColor(0xFFba68c8);
//                break;
//            case ("вышел"): holder.card_view.setCardBackgroundColor(0xFFe57373);
//                break;
//        }
        // изменения цвета текста в зависисмости от того выход или вход
        String condition = itemList.getVhod();
        switch (condition){
           case ("ушел(-а)"): holder.vhod.setTextColor(0xFFd50000);
               break;
            case ("пришел(-а)"): holder.vhod.setTextColor(0xFF1b5e20);
                break;
       }
    }

   @Override
    public int getItemCount() {return items.size();}

    public class ViewHolder extends RecyclerView.ViewHolder{

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
}
