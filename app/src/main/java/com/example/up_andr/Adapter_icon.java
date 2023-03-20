package com.example.up_andr;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.List;

public class Adapter_icon extends RecyclerView.Adapter<Adapter_icon.ViewHolder> {
    private Context mContext;
    List<Icon_mask> icon_masks;

    public Adapter_icon(Context mContext, List<Icon_mask> icon_masks)
    {
        this.mContext = mContext;
        this.icon_masks = icon_masks;
    }

    @NonNull
    @Override
    public Adapter_icon.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Adapter_icon.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.activity_icon_mask, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_icon.ViewHolder holder, int position) {
        final Icon_mask modal = icon_masks.get(position);
        holder.title.setText(modal.getTitle());

        if(modal.getImage().equals("null"))
        {
            holder.image.setImageResource(R.drawable.logo);
        }
        else
        {
            new Adapter_icon.DownloadImageTask((ImageView) holder.image)
                    .execute(modal.getImage());
        }
    }

    @Override
    public int getItemCount() {
        return icon_masks.size();
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.Icon_Text);
            image = itemView.findViewById(R.id.Icon_Image);
        }
    }
}
