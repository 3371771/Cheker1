package com.vkr.ksenija_i.IN_OUT;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
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

    Button admin;
    DialogFragment dialog_info;
    MenuItem name;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        admin = (Button) findViewById(R.id.admin);
        admin.setOnClickListener(this);
        textView = (TextView) findViewById(R.id.textView);
        dialog_info = new Dialod_info();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        triggers();
        adminButton();
        fubButton();
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        Sharedpref pref = Sharedpref.getInstance(getBaseContext());
        name = menu.findItem(R.id.fio);
        String name1 = pref.getName("saved_name", "");
        name.setTitle(name1);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.fio) {
            //получение имени из сохраненного в памяти
            Sharedpref pref = Sharedpref.getInstance(getBaseContext());
            String fio = pref.getName("saved_name", "");
            //
            Toast toast = Toast.makeText(this, "Вы вошли как " + fio, Toast.LENGTH_SHORT);
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            if( v != null) v.setGravity(Gravity.CENTER);
            toast.show();
            toast.setGravity(Gravity.TOP, 0, 70);
            toast.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Intent intent4;
        intent4 = new Intent(this, AdminActivity.class);
        startActivity(intent4);
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
        switch (id) {
            case (R.id.nav_info):
                dialog_info.show(getFragmentManager(), "dialog_info");
                break;
            case (R.id.nav_help):
                Intent intent3;
                intent3 = new Intent(Intent.ACTION_SEND);
                intent3.setType("plain/text");
                intent3.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{address});
                intent3.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
                startActivity(intent3);
                break;
            default:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void clear() {
        Sharedpref pref = Sharedpref.getInstance(getBaseContext());
        pref.prefClear();

        String name1 = pref.getName("saved_name", "");
        name.setTitle(name1);
    } //функция очищения SharedPreferences + обновление имени вверху

    public void triggers() {
        Sharedpref pref = Sharedpref.getInstance(getBaseContext());
        if (pref.getToken().equals("1")) {
            textView.setText("Приложите телефон к метке");
        } else {
            textView.setText("Пожалуйста, выполните вход");
        }
    } // показывает надпись в зависимости от того выполнен вход или нет

    public void adminButton() {
        Sharedpref pref = Sharedpref.getInstance(getBaseContext());
        if (pref.getName("saved_name", "").equals("Admin")) {
            admin.setVisibility(View.VISIBLE);
            admin.setClickable(true);
            textView.setText("");
        } else {
            admin.setVisibility(View.INVISIBLE);
            admin.setClickable(false);
        }
    } // показывать или нет кнопку админки (в зависимости от логина)

    public void fubButton() {
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Sharedpref pref = Sharedpref.getInstance(getBaseContext());
        if (pref.getToken().equals("1")) {
            fab.setImageResource(R.drawable.ic_exit_to_app_black_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_person_black_24dp);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sharedpref pref = Sharedpref.getInstance(getBaseContext());//в зависимости от входа
                if (pref.getToken().equals("1")) {
                    Snackbar.make(view, "Выйти?", Snackbar.LENGTH_LONG)
                            .setAction("Да", new View.OnClickListener() {
                                @Override
                                public void onClick( View view) {
                                    clear();
                                    textView.setText("Пожалуйста, выполните вход");
                                    admin.setVisibility(View.INVISIBLE);
                                    admin.setClickable(false);
                                    fab.setImageResource(R.drawable.ic_person_black_24dp);
                                }
                            }).show();
                } else {
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
    } // картиникa и функции Фаба в зависимости от входа
}
