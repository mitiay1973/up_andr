package com.example.up_andr;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Glavn extends AppCompatActivity {
    private TextView text;
    private ImageView img;

    private Adapter_glavn adapter_glavn;
    private List<Mask_glavn> mask_glavn = new ArrayList<>();

    private Adapter_icon adapter_icon;
    private List<Icon_mask> icon_mask = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_glavn);
        text = findViewById(R.id.Glavnaya_Name);
        text.setText("С возвращением " + Avtorization.mask.getNickName() + "!");
        img = findViewById(R.id.Glavn_Image);
        new DownloadImageTask((ImageView) img).execute(Avtorization.mask.getAvatar());

        ListView listView = findViewById(R.id.ListFoto);
        adapter_glavn = new Adapter_glavn(Glavn.this, mask_glavn);
        listView.setAdapter(adapter_glavn);

        RecyclerView recyclerView = findViewById(R.id.List_icon);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        adapter_icon = new Adapter_icon(Glavn.this, icon_mask);
        recyclerView.setAdapter(adapter_icon);

        new Get_quotes().execute();
        new Get_feelings().execute();
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Ошибка", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class Get_quotes extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://mskko2021.mad.hakta.pro/api/quotes");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();

            } catch (Exception exception) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object=new JSONObject(s);
                JSONArray tempArray= object.getJSONArray("data") ;
                for (int i = 0;i<tempArray.length();i++)
                {
                    JSONObject Json = tempArray.getJSONObject(i);
                    Mask_glavn temp = new Mask_glavn(
                            Json.getInt("id"),
                            Json.getString("title"),
                            Json.getString("image"),
                            Json.getString("description")
                    );

                    mask_glavn.add(temp);
                    adapter_glavn.notifyDataSetInvalidated();
                }
            } catch (Exception ignored) {
                Toast.makeText(Glavn.this, "При выводе данных возникла ошибка", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class Get_feelings extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://mskko2021.mad.hakta.pro/api/feelings");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();

            } catch (Exception exception) {
                return null;
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                icon_mask.clear();
                adapter_icon.notifyDataSetChanged();

                JSONObject object = new JSONObject(s);
                JSONArray tempArray  = object.getJSONArray("data");

                for (int i = 0;i<tempArray.length();i++)
                {
                    JSONObject productJson = tempArray.getJSONObject(i);
                    Icon_mask tempProduct = new Icon_mask(
                            productJson.getInt("id"),
                            productJson.getString("title"),
                            productJson.getInt("position"),
                            productJson.getString("image")
                    );
                    icon_mask.add(tempProduct);
                    adapter_icon.notifyDataSetChanged();
                }
                icon_mask.sort(Comparator.comparing(Icon_mask::getPosition));
                adapter_icon.notifyDataSetChanged();
            }
            catch (Exception exception)
            {
                Toast.makeText(Glavn.this, "При выводе данных возникла ошибка", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onClickListnet(View v) {
        Intent intent = new Intent(this, Pleer.class);
        startActivity(intent);
    }

    public void onClickProfile(View v) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    public void onClickMenu(View v) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}