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
            properties.setProperty("user", "host1213291_test"); //user
            properties.setProperty("password", "NdxW0YY2"); //user
            properties.setProperty("useUnicode", "true");
            properties.setProperty("characterEncoding", "UTF-8");
            dbConnection = DriverManager.getConnection("jdbc:mysql://mysql51.hostland.ru/host1213291_test", properties);
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

