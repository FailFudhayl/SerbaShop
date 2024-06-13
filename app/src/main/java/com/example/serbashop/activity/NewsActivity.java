package com.example.serbashop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.serbashop.R;
import com.example.serbashop.model.News;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        TextView TV_newsTitle = findViewById(R.id.TV_newsTitle);
        ImageView IV_imgNews = findViewById(R.id.IV_newsDetail);
        TextView TV_newsDesc = findViewById(R.id.TV_newsDescDetail);

        Intent intent = getIntent();
        News newss = intent.getParcelableExtra("newss");

        TV_newsTitle.setText(newss.getTitle());
        IV_imgNews.setImageResource(newss.getNews_img());
        TV_newsDesc.setText(newss.getDescription());
    }
}