package com.example.up_andr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Mask_glavn implements Parcelable {
    private int Id;
    private String Title;
    private String Image;
    private String Opisanie;

    public Mask_glavn(Parcel in) {
        Id = in.readInt();
        Title = in.readString();
        Image = in.readString();
        Opisanie = in.readString();
    }

    public Mask_glavn(int id, String title, String image, String opisanie) {
        Id = id;
        Title = title;
        Image = image;
        Opisanie = opisanie;
    }

    public static final Creator<Mask_glavn> CREATOR = new Creator<Mask_glavn>() {
        @Override
        public Mask_glavn createFromParcel(Parcel in) {
            return new Mask_glavn(in);
        }

        @Override
        public Mask_glavn[] newArray(int size) {
            return new Mask_glavn[size];
        }
    };

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }


    public String getImage() {
        return Image;
    }


    public String getOpisanie() {
        return Opisanie;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(Id);
        parcel.writeString(Title);
        parcel.writeString(Image);
        parcel.writeString(Opisanie);
    }
}