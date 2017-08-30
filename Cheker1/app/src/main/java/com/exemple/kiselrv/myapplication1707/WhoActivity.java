package com.exemple.kiselrv.myapplication1707;

import android.content.SharedPreferences;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WhoActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edit_who,edit_who2;
    Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who);
        edit_who = (EditText)findViewById(R.id.editText);
        edit_who2 = (EditText)findViewById(R.id.editText2);
        btn_save = (Button)findViewById(R.id.button_save);
        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        save_name();
    }
    private void save_name() {
        //сохранить введенное имя
        SharedPreferences pref = getSharedPreferences("main", MODE_PRIVATE);
        SharedPreferences.Editor ed = pref.edit();
        ed.putString("saved_name", edit_who.getText().toString());
        ed.putString("saved_name2", edit_who2.getText().toString());
        ed.apply();

        //
        Toast.makeText(WhoActivity.this, "Данные сохранены", Toast.LENGTH_SHORT).show();
    }
}

