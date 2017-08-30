package com.exemple.kiselrv.myapplication1707;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Dialod_info extends DialogFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        getDialog().setTitle("О приложении");
        View v = inflater.inflate(R.layout.dialog_info, null);
//        v.findViewById(R.id.button3).setOnClickListener(this);
        return v;
    }

//    @Override
//    public void onClick(View v) {
//        dismiss();
//    }
}
