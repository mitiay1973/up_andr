package com.example.up_andr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Icon_mask implements Parcelable {
    protected Icon_mask(Parcel in) {
        Id = in.readInt();
        Title = in.readString();
        Position = in.readInt();
        Image = in.readString();
    }

    public static final Creator<Icon_mask> CREATOR = new Creator<Icon_mask>() {
        @Override
        public Icon_mask createFromParcel(Parcel in) {
            return new Icon_mask(in);
        }

        @Override
        public Icon_mask[] newArray(int size) {
            return new Icon_mask[size];
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

    public int getPosition() {
        return Position;
    }

    public String getImage() {
        return Image;
    }


    private int Id;
    private String Title;
    private int Position;
    private String Image;


    public Icon_mask(int id, String title, int position, String image) {
        Id = id;
        Title = title;
        Position = position;
        Image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(Title);
        dest.writeInt(Position);
        dest.writeString(Image);
    }
}