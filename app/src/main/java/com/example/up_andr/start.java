package com.example.up_andr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_start);
    }
    public void onClickAvtoriz(View v) {
        Intent intent = new Intent(this, Avtorization.class);
        startActivity(intent);
    }

    public void onClickReg(View v) {
        Intent intent = new Intent(this, Reg.class);
        startActivity(intent);
    }
}