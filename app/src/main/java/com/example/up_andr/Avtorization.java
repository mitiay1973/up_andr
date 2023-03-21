package com.example.up_andr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Avtorization extends AppCompatActivity {

    User user = new User( "","");
    final static String userVariableKey = "USER_VARIABLE";

    public static User_Mask mask;
    EditText email, password;
    SharedPreferences preferences;
    final static String Email = "Email";
    final static String Password = "Password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avtorization);
        getSupportActionBar().hide();

        email = findViewById(R.id.EditText_Email);
        password = findViewById(R.id.EditText_Password);
        getInfo();
    }
    public void onClickLoginAkk(View v) {
        if(email.getText().toString().equals("") || password.getText().toString().equals(""))
        {
            Toast.makeText(Avtorization.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Pattern pattern = Pattern.compile("@", Pattern.CASE_INSENSITIVE);
            Matcher m = pattern.matcher(email.getText().toString());
            boolean b = m.find();
            if(b)
            {
                Login();
            }
            else
            {
                Toast.makeText(Avtorization.this, "Поле Email обязательно должен содержать '@'", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void onClickLoginProf(View v) {
        if(email.getText().toString().equals("") || password.getText().toString().equals(""))
        {
            Toast.makeText(Avtorization.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Pattern pattern = Pattern.compile("@", Pattern.CASE_INSENSITIVE);
            Matcher m = pattern.matcher(email.getText().toString());
            boolean b = m.find();
            if(b)
            {
                Login1();
            }
            else
            {
                Toast.makeText(Avtorization.this, "Поле Email обязательно должен содержать '@'", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void saveInfo(){
        preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Email, email.getText().toString());
        editor.putString(Password,password.getText().toString());
        editor.commit();
    }

    public void getInfo(){
        preferences = getPreferences(MODE_PRIVATE);
        String emailu = preferences.getString(Email,"");
        String passwordu = preferences.getString(Password,"");
        email.setText(emailu);
        password.setText(passwordu);
    }
    public void Login()
    {
        String emails = String.valueOf(email.getText());
        String passwords = String.valueOf(password.getText());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://mskko2021.mad.hakta.pro/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Retrofits retrofits = retrofit.create(Retrofits.class);

        UserM modelSendUser = new UserM(emails, passwords);
        Call<User_Mask> call = retrofits.cUser(modelSendUser);
        call.enqueue(new Callback<User_Mask>() {
            @Override
            public void onResponse(Call<User_Mask> call, Response<User_Mask> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Avtorization.this, "Пользователь не найден", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.body() != null)
                {
                    if(response.body().getToken() != null)
                    {
                        saveInfo();
                        mask = response.body();
                        Intent intent = new Intent(Avtorization.this, Glavn.class);
                        Bundle b = new Bundle();
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onFailure(Call<User_Mask> call, Throwable t) {
                Toast.makeText(Avtorization.this, "При авторизации возникла ошибка: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
    public void Login1()
    {
        String emails = String.valueOf(email.getText());
        String passwords = String.valueOf(password.getText());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://mskko2021.mad.hakta.pro/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Retrofits retrofits = retrofit.create(Retrofits.class);

        UserM modelSendUser = new UserM(emails, passwords);
        Call<User_Mask> call = retrofits.cUser(modelSendUser);
        call.enqueue(new Callback<User_Mask>() {
            @Override
            public void onResponse(Call<User_Mask> call, Response<User_Mask> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Avtorization.this, "Пользователь не найден", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.body() != null)
                {
                    if(response.body().getToken() != null)
                    {
                        saveInfo();
                        mask = response.body();
                        Intent intent = new Intent(Avtorization.this, Profile.class);
                        Bundle b = new Bundle();
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onFailure(Call<User_Mask> call, Throwable t) {
                Toast.makeText(Avtorization.this, "При авторизации возникла ошибка: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(userVariableKey, user);
        saveInfo();
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        user=(User)savedInstanceState.getSerializable(userVariableKey);
        EditText etLogin=findViewById(R.id.EditText_Email);
        EditText etPassword=findViewById(R.id.EditText_Password);
        etLogin.setText(user.getLogin());
        etPassword.setText(user.getPassword());
    }


    public void onClickRegister(View v) {
        Intent intent = new Intent(this, Reg.class);
        startActivity(intent);
    }
}