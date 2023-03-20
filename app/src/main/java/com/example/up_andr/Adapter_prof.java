package com.example.up_andr;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

public class Adapter_prof extends BaseAdapter {

    private Context mContext;
    List<Mask_Profile> mask_Profiles;

    public Adapter_prof(Context mContext, List<Mask_Profile> mask_Profiles) {
        this.mContext = mContext;
        this.mask_Profiles = mask_Profiles;
    }

    @Override
    public int getCount() {
        return mask_Profiles.size();
    }

    @Override
    public Object getItem(int i) {
        return mask_Profiles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mask_Profiles.get(i).getID();
    }

    public static Bitmap loadContactPhoto(ContentResolver cr, long id, Context context) {
        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
        if (input == null) {
            Resources res = context.getResources();
            return BitmapFactory.decodeResource(res, R.drawable.logo);
        }
        return BitmapFactory.decodeStream(input);
    }

    private Bitmap getUserImage(String encodedImg) {

        if (encodedImg != null && !encodedImg.equals("null")) {
            byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } else
            return BitmapFactory.decodeResource(mContext.getResources(), R.drawable.logo);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext, R.layout.activity_mask_profile, null);
        View v1 = View.inflate(mContext, R.layout.maska_foro_plus, null);

        ImageView Image = v.findViewById(R.id.ppz);
        TextView Time = v.findViewById(R.id.time);

        Mask_Profile mask = mask_Profiles.get(position);
        Time.setText(mask.getTime());


        Image.setImageBitmap(getUserImage(mask.getImage()));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenDetalis = new Intent(mContext, Prosmotr_foto.class);
                intenDetalis.putExtra("Pictures", mask);
                mContext.startActivity(intenDetalis);

            }
        });
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenDetalis = new Intent(mContext, Prosmotr_foto.class);
                intenDetalis.putExtra("Pictures", mask);
                mContext.startActivity(intenDetalis);

            }
        });
        return v;
    }
}
