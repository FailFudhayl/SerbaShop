package com.example.serbashop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.serbashop.R;
import com.google.android.material.badge.BadgeUtils;

public class PembayaranActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        Button pembayaran = findViewById(R.id.btn_sdhbayar);
        pembayaran.setOnClickListener(view -> {
            Intent intent = new Intent(PembayaranActivity.this, MainActivity.class);
            intent.putExtra("fragmentToLoad", "PembayaranFragment");
            startActivity(intent);
            finish();
        });

    }
}