package com.example.up_andr;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {
    View v;
    List<Mask_Profile> maskaProfiles;
    Adapter_prof pAdapter;
    Connection connection;
    GridView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);

        v = findViewById(com.google.android.material.R.id.ghost_view);



        GetTextFromSQL(v);
    }

    public void SelectList(String Choice)
    {
        maskaProfiles = new ArrayList<Mask_Profile>();
        listView = findViewById(R.id.Profile_listfoto);
        pAdapter = new Adapter_prof(Profile.this, maskaProfiles);
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();
            if (connection != null) {

                String query = Choice;
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Mask_Profile tempMask = new Mask_Profile
                            (resultSet.getInt("id"),
                                    resultSet.getString("image"),
                                    resultSet.getString("time")
                            );
                    maskaProfiles.add(tempMask);
                    pAdapter.notifyDataSetInvalidated();
                }
                connection.close();
            } else {
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        enterMobile();
    }

    public void enterMobile() {
        pAdapter.notifyDataSetInvalidated();
        listView.setAdapter(pAdapter);
    }

    public void GetTextFromSQL(View v) {
        maskaProfiles = new ArrayList<Mask_Profile>();
        listView = findViewById(R.id.Profile_listfoto);
        pAdapter = new Adapter_prof(Profile.this, maskaProfiles);
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();
            if (connection != null) {
                String query = "Select * From Image";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Mask_Profile tempMask = new Mask_Profile
                            (resultSet.getInt("id"),
                                    resultSet.getString("image"),
                                    resultSet.getString("time")
                            );
                    maskaProfiles.add(tempMask);
                    pAdapter.notifyDataSetInvalidated();
                }
                connection.close();

            } else {
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        enterMobile();
    }

    public void onClickExit(View v) {
        Intent intent = new Intent(this, Avtorization.class);
        startActivity(intent);
    }

    public void onClickMenu(View v) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}