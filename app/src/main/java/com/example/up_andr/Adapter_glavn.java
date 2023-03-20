package com.example.up_andr;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

public class Adapter_glavn extends BaseAdapter {
    private Context mContext;
    List<Mask_glavn> maskListGlavn;

    public Adapter_glavn(Context mContext, List<Mask_glavn> maskListGlavn)
    {
        this.mContext = mContext;
        this.maskListGlavn = maskListGlavn;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
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

    @Override
    public int getCount() {
        return maskListGlavn.size();
    }

    @Override
    public Object getItem(int position) {
        return maskListGlavn.get(position);
    }

    @Override
    public long getItemId(int position) {
        return maskListGlavn.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=View.inflate(mContext,R.layout.activity_mask_glavn, null);
        ImageView Image=v.findViewById(R.id.ImageM);
        TextView Title=v.findViewById(R.id.TitleM);
        TextView Description=v.findViewById(R.id.OpisanieM);

        Mask_glavn mask=maskListGlavn.get(position);
        Title.setText(mask.getTitle());

        Image.setImageURI(Uri.parse(mask.getImage()));

        if(mask.getImage().toString().equals("null"))
        {
            Image.setImageResource(R.drawable.logo);
        }
        else
        {
            new DownloadImageTask((ImageView) Image).execute(mask.getImage());

        }
        Description.setText(mask.getOpisanie());

        return v;
    }
}
