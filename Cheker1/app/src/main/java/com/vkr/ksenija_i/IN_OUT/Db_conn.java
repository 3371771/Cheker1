package com.vkr.ksenija_i.IN_OUT;


import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

import java.util.Properties;

public class Db_conn {

    public static Connection getDBConnection() {

        Connection dbConnection = null;
        final String LOG_TAG = "myLogs";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Properties properties = new Properties();
            properties.setProperty("user", "**"); //user
            properties.setProperty("password", "**"); //user
            properties.setProperty("useUnicode", "true");
            properties.setProperty("characterEncoding", "UTF-8");
            dbConnection = DriverManager.getConnection("**", properties);
            return dbConnection;

        } catch (java.sql.SQLException e) {
            Log.d(LOG_TAG, "ошибка подключения к БД");
            Log.d(LOG_TAG, e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbConnection;
    }
}

