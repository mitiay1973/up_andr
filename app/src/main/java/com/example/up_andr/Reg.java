package com.example.up_andr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Reg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_reg);
    }
}