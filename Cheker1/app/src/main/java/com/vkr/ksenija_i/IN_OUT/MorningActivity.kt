package com.vkr.ksenija_i.IN_OUT

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class MorningActivity : Entrance() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_morning)

        toDo = "пришел(-а)"
        imageView = findViewById(R.id.imageView5) as ImageView
        anim = AnimationUtils.loadAnimation(this, R.anim.myanim)
        textView9 = findViewById(R.id.textView9) as TextView
        start()
    }
}

