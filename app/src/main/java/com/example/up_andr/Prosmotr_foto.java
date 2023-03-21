package com.example.up_andr;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Base64;

public class Prosmotr_foto extends AppCompatActivity {
    Connection connection;
    ImageView imageView;
    Mask_Profile mask;
    Adapter_prof adapter;

    View v;

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_prosmotr_foto);
        mask = getIntent().getParcelableExtra("Pictures");

        imageView = findViewById(R.id.Image_Prosmotr);
        imageView.setImageBitmap(getImgBitmap(mask.getImage()));



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }
        });

    }

    private Bitmap getImgBitmap(String encodedImg) {
        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return BitmapFactory.decodeResource(Prosmotr_foto.this.getResources(),
                R.drawable.icon);
    }

    public void onClickProfile(View v) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    public void onClickDelete(View v) {
        AlertDialog.Builder builder=new AlertDialog.Builder(Prosmotr_foto.this);
        builder.setTitle("Удалить")
                .setMessage("Вы хотите удалить фотографию?")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            ConnectionHelper connectionHelper = new ConnectionHelper();
                            connection = connectionHelper.connectionClass();
                            if (connection != null) {
                                String query = "DELETE FROM Image  WHERE id= "+mask.getID()+"";
                                Statement statement = connection.createStatement();
                                statement.executeQuery(query);
                            }
                        }

                        catch (Exception ex)
                        {

                        }
                        Prof(v);
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    public void Prof(View v) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
    public void onClickClose(View v) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    }
