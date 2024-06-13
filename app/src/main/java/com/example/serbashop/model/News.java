package com.example.serbashop.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class News implements Parcelable {
    private String title;
    private int news_img;
    private String description;

    public News(String title, int news_img, String description) {
        this.title = title;
        this.news_img = news_img;
        this.description = description;
    }

    protected News(Parcel in) {
        title = in.readString();
        news_img = in.readInt();
        description = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNews_img() {
        return news_img;
    }

    public void setNews_img(int news_img) {
        this.news_img = news_img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeInt(news_img);
        parcel.writeString(description);
    }
}
