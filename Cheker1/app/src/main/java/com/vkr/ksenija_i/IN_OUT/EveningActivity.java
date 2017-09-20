package com.vkr.ksenija_i.IN_OUT;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class EveningActivity extends Entrance {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evening);

        toDo = "ушел(-а)";
        imageView = (ImageView) findViewById(R.id.image_moon);
        anim = AnimationUtils.loadAnimation(this, R.anim.myanim2);
        textView9 = (TextView)findViewById(R.id.textView11);
        start();
    }
}

