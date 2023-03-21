package com.example.up_andr;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Profile extends AppCompatActivity {
    View v;
    List<Mask_Profile> maskaProfiles;
    Adapter_prof pAdapter;
    Connection connection;
    GridView listView;
    String Img="";
    private ImageView imageButton;
    private TextView text;
    String currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        currentTime = sdf.format(new Date());
        setContentView(R.layout.activity_profile);
        text = findViewById(R.id.Profile_Name);
        text.setText(Avtorization.mask.getNickName());
        imageButton=findViewById(R.id.AddImage);
        v = findViewById(com.google.android.material.R.id.ghost_view);
        GetTextFromSQL(v);
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
        Intent intent = new Intent(Profile.this, Avtorization.class);
        startActivity(intent);
    }

    public void onClickMenu(View v) {
        Intent intent = new Intent(Profile.this, Menu.class);
        startActivity(intent);
    }
    public void onClickProfile(View v) {

    }
    public void onClickPleer(View v) {
        Intent intent = new Intent(Profile.this, Pleer.class);
        startActivity(intent);
    }

    public void onClickGlavn(View v) {
        Intent intent = new Intent(Profile.this, Glavn.class);
        startActivity(intent);
    }
    public void  onClickAddImg(View v) throws InterruptedException {
        getImage();
    }
    private void getImage()
    {
        Intent intentChooser= new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && data!= null && data.getData()!= null)
        {
            if(resultCode==RESULT_OK)
            {
                Log.d("MyLog","Image URI : "+data.getData());
                imageButton.setImageURI(data.getData());
                Bitmap bitmap = ((BitmapDrawable)imageButton.getDrawable()).getBitmap();
                encodeImage(bitmap);
                SetTextFromSql(v);
                finish();
                startActivity(getIntent());
            }
        }
    }
    private String encodeImage(Bitmap bitmap) {
        int prevW = 150;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Img= Base64.getEncoder().encodeToString(bytes);
            return Img;
        }
        return "";
    }
    public void SetTextFromSql(View v) {
        try {
            ConnectionHelper conectionHellper = new ConnectionHelper();
            connection = conectionHellper.connectionClass();
            if (connection != null) {

                String query = "INSERT INTO Image (image, time) values ('"+ Img +"','"+currentTime+"')";
                Statement statement = connection.createStatement();
                statement.execute(query);
                Toast.makeText(this,"Успешно добавлено", Toast.LENGTH_LONG).show();
            } else {

            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}