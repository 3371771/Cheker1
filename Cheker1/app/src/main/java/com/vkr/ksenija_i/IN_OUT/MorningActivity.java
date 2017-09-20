package com.vkr.ksenija_i.IN_OUT;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MorningActivity extends Entrance {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morning);

        toDo = "пришел(-а)";
        imageView = (ImageView) findViewById(R.id.imageView5);
        anim = AnimationUtils.loadAnimation(this, R.anim.myanim);
        textView9 = (TextView) findViewById(R.id.textView9);
        start();
    }
}

