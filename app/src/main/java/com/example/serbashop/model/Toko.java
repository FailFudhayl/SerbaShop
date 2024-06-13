package com.example.serbashop.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Toko implements Parcelable {
    private String nama_toko;
    private int toko_img;
    private String time;
    private String url_toko;

    public Toko(String nama_toko, int toko_img, String time, String url_toko) {
        this.nama_toko = nama_toko;
        this.toko_img = toko_img;
        this.time = time;
        this.url_toko = url_toko;
    }

    protected Toko(Parcel in) {
        nama_toko = in.readString();
        toko_img = in.readInt();
        time = in.readString();
        url_toko = in.readString();
    }

    public static final Creator<Toko> CREATOR = new Creator<Toko>() {
        @Override
        public Toko createFromParcel(Parcel in) {
            return new Toko(in);
        }

        @Override
        public Toko[] newArray(int size) {
            return new Toko[size];
        }
    };

    public String getNama_toko() {
        return nama_toko;
    }

    public void setNama_toko(String nama_toko) {
        this.nama_toko = nama_toko;
    }

    public int getToko_img() {
        return toko_img;
    }

    public void setToko_img(int toko_img) {
        this.toko_img = toko_img;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl_toko() {
        return url_toko;
    }

    public void setUrl_toko(String url_toko) {
        this.url_toko = url_toko;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(nama_toko);
        parcel.writeInt(toko_img);
        parcel.writeString(time);
        parcel.writeString(url_toko);
    }
}

