package com.example.up_andr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Mask_Profile implements Parcelable {

    public Mask_Profile(int ID, String image, String time) {
        this.ID = ID;
        Image = image;
        Time = time;

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    private int ID;
    private String Image;
    private String Time;

    protected Mask_Profile(Parcel in) {
        ID = in.readInt();
        Image = in.readString();
        Time = in.readString();
    }

    public static final Creator<Mask_Profile> CREATOR = new Creator<Mask_Profile>() {
        @Override
        public Mask_Profile createFromParcel(Parcel in) {
            return new Mask_Profile(in);
        }

        @Override
        public Mask_Profile[] newArray(int size) {
            return new Mask_Profile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(Image);
        dest.writeString(Time);
    }

}