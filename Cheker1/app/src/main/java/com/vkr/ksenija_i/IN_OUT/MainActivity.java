package com.vkr.ksenija_i.IN_OUT;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    Button enter, exit, admin;
    DialogFragment dialog_info;
    MenuItem name;
    TextView textView;
    String trigger,get_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        enter = (Button) findViewById(R.id.button);
        enter.setClickable(false);
        enter.setOnClickListener(this);
        exit = (Button) findViewById(R.id.button2);
        exit.setClickable(false);
        exit.setOnClickListener(this);
        admin = (Button) findViewById(R.id.admin);
        admin.setOnClickListener(this);
        dialog_info = new Dialod_info();

        triggers();

        SharedPreferences pref = getSharedPreferences("main", MODE_PRIVATE);
        get_admin = (pref.getString("saved_name", "").toString());
        if (get_admin.equals("Admin")) {
            admin.setVisibility(View.VISIBLE);
            admin.setClickable(true);
        } else {admin.setVisibility(View.INVISIBLE);
            admin.setClickable(false);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //в зависимости от входа
                SharedPreferences pref = getSharedPreferences("main", MODE_PRIVATE);
                trigger = pref.getString("trigger","").toString();

                if (trigger.equals("1")) {
                    Snackbar.make(view, "Выйти?", Snackbar.LENGTH_LONG)
                            .setAction("Да", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    clear();
                                    textView.setText("Пожалуйста, выполните вход");
                                    admin.setVisibility(View.INVISIBLE);
                                    admin.setClickable(false);
                                }
                            }).show();
                }
                else {
                    Snackbar.make(view, "Войти?", Snackbar.LENGTH_LONG)
                            .setAction("Да", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent3;
                                    intent3 = new Intent(com.vkr.ksenija_i.IN_OUT.MainActivity.this, WhoActivity.class);
                                    startActivity(intent3);
                                }
                            }).show();
                }
        }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu){
        SharedPreferences pref = getSharedPreferences("main", MODE_PRIVATE);
        name = menu.findItem(R.id.fio);
        String name1 = (pref.getString("saved_name", "").toString());
        name.setTitle(name1);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.fio) {
            //получение имени из сохраненного в памяти
            SharedPreferences pref = getSharedPreferences("main", MODE_PRIVATE);
            String fio = pref.getString("saved_name", "").toString();
            //
            Toast toast = Toast.makeText(this, "Вы вошли как " + fio, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 70);
            toast.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.button:
                Intent intent2;
                intent2 = new Intent(this, MorningActivity.class);
                startActivity(intent2);
                break;
            case R.id.button2:
                Intent intent3;
                intent3 = new Intent(this, EveningActivity.class);
                startActivity(intent3);
                break;
            case R.id.admin:
                Intent intent4;
                intent4 = new Intent(this, AdminActivity.class);
                startActivity(intent4);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        String address = "3371771@list.ru";
        String subject = "Вопрос по приложению IN/OUT";
        if (id == R.id.nav_info) {
            dialog_info.show(getFragmentManager(), "dialog_info");
        } else if (id == R.id.nav_help) {
            Intent intent3;
            intent3 = new Intent(Intent.ACTION_SEND);
            intent3.setType("plain/text");
            intent3.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{address});
            intent3.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
            startActivity(intent3);
        } else if (id == R.id.nav_who) {
            Intent intent4;
            intent4 = new Intent(this, WhoActivity.class);
            startActivity(intent4);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//функция очищения SharedPreferences + обновление имени вверху
    public void clear() {
        SharedPreferences pref = getSharedPreferences("main", MODE_PRIVATE);
        SharedPreferences.Editor ed = pref.edit();
        ed.clear().apply();

        String name1 = (pref.getString("saved_name", "").toString());
        name.setTitle(name1);

        enter.setVisibility(View.INVISIBLE);
        exit.setVisibility(View.INVISIBLE);
        enter.setClickable(false);
        exit.setClickable(false);
    }

    public void triggers () { // показывает надпись в зависимости от того выполнен вход или нет
        SharedPreferences pref = getSharedPreferences("main", MODE_PRIVATE);
        trigger = pref.getString("trigger","").toString();
        textView = (TextView) findViewById(R.id.textView);

        if (trigger.equals("1")) {
            textView.setText("Приложите телефон к метке");
            exit.setVisibility(View.VISIBLE);
            enter.setVisibility(View.VISIBLE);
            exit.setClickable(true);
            enter.setClickable(true);
        } else
            {textView.setText("Пожалуйста, выполните вход");
                enter.setVisibility(View.INVISIBLE);
                exit.setVisibility(View.INVISIBLE);
                enter.setClickable(false);
                exit.setClickable(false);
            }
    }
}
