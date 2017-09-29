package com.vkr.ksenija_i.IN_OUT;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;

public class Sharedpref extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private static Sharedpref INSTANCE = null;

    private Sharedpref(Context context) {
        this.sharedPreferences = context.getSharedPreferences("main", Context.MODE_PRIVATE);
    }

    public static Sharedpref getInstance(Context context) {

        if (INSTANCE == null) {
            synchronized (Sharedpref.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Sharedpref(context);
                }
            }
        }
        return INSTANCE;
    }

    public void saveToken(String token) {
        sharedPreferences.edit().putString("USER_TOKEN_KEY", token).apply();
    }

    public String getToken() {
        return sharedPreferences.getString("USER_TOKEN_KEY", "0");
    }

    public void prefClear () {
        sharedPreferences.edit().clear().apply();
    }

    public void saveName(String name, String value) {
        sharedPreferences.edit().putString(name,value).apply();
    }

    public String getName(String name, String value ) {
        return sharedPreferences.getString(name, value);
    }
}
