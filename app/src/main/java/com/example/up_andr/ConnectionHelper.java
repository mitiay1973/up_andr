package com.example.up_andr;
import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnectionHelper {
    Connection connection;
    String uName, password, ip, port, database;
    @SuppressLint("NewApi")

    public Connection connectionClass() {
        ip = "ngknn.ru";
        database = "ImageMobile";
        uName = "33П";
        password = "12357";
        port = "1433";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL= "jdbc:jtds:sqlserver://"+ ip + ":"+ port+";"+
                    "databasename="+ database+";user="+uName+";password="+password+";";
            connection = DriverManager.getConnection(ConnectionURL);

        } catch (Exception ex) {
            Log.e("Error",ex.getMessage());
        }
        return connection;
    }
}
