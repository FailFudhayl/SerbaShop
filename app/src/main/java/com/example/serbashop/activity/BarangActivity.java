package com.example.serbashop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.serbashop.R;
import com.example.serbashop.api.ApiService;
import com.example.serbashop.api.RetrofitClient;
import com.example.serbashop.localdatabase.DatabaseHelper;
import com.example.serbashop.model.CartItem;
import com.example.serbashop.model.Product;
import com.squareup.picasso.Picasso;

import android.os.Handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarangActivity extends AppCompatActivity {
    ImageView detailImage;
    TextView detailName, detailPrice, detailRating, detailDescription;

    LinearLayout layout_loading, layout_eror, layout_item;
    Button retry_detail, btn_checkout, btn_keranjang;

    Handler handlerDet;
    String productName, productPrice, productImage;
    int productId;
    Intent intent1, intent2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang);

        handlerDet = new Handler(Looper.getMainLooper());

        layout_loading= findViewById(R.id.loading_detail);
        layout_eror = findViewById(R.id.erorLayout_Detail);
        layout_item = findViewById(R.id.layout_details);
        retry_detail = findViewById(R.id.btn_retryDetail);
        btn_checkout = findViewById(R.id.btn_BeliBarang);
        btn_keranjang = findViewById(R.id.btn_MasukKeranjang);
        detailImage = findViewById(R.id.IV_DetailBarang);
        detailName = findViewById(R.id.TV_DetailBarang);
        detailPrice = findViewById(R.id.TV_HargadetailBarang);
        detailRating = findViewById(R.id.TV_DetailProductRating);
        detailDescription = findViewById(R.id.TV_DescDetailBarang);


        productId = getIntent().getIntExtra("product", -1);

        retry_detail.setOnClickListener( v -> {
            layout_eror.setVisibility(View.GONE);
            layout_loading.setVisibility(View.VISIBLE);
            handlerDet.postDelayed(() -> {
                layout_loading.setVisibility(View.GONE);
                layout_item.setVisibility(View.VISIBLE);
            }, 100);
            fetchProductDetails(productId);
        });

        layout_loading.setVisibility(View.VISIBLE);
        handlerDet.postDelayed(() -> {
            layout_loading.setVisibility(View.GONE);
        }, 1000);
        fetchProductDetails(productId);

        btn_keranjang.setOnClickListener(v -> {
            addToCart();
        });

        btn_checkout.setOnClickListener(view -> {
            DatabaseHelper databaseHelper1 = new DatabaseHelper(BarangActivity.this);
            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            SQLiteDatabase db = databaseHelper1.getWritableDatabase();
            long result = databaseHelper1.addProductToRiwayat(db, productId, productName, productPrice, productImage, currentDate);

            if (result != -1) {
                Intent intent1 = new Intent(BarangActivity.this, PembayaranActivity.class);
                startActivity(intent1);
                finish();
            }

            db.close();
        });
    }

    private void fetchProductDetails(int productID){
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<Product> call = apiService.getProductById(productID);

        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Product product = response.body();

                    productName = product.getTitle();
                    productPrice = String.valueOf(product.getPrice());
                    productImage = product.getThumbnail();

                    detailName.setText(product.getTitle());
                    detailPrice.setText("$ " + product.getPrice());
                    detailRating.setText(String.valueOf(product.getRating()));
                    detailDescription.setText(product.getDescription());
                    Picasso.get().load(product.getThumbnail()).into(detailImage);
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                layout_item.setVisibility(View.GONE);
                layout_eror.setVisibility(View.VISIBLE);
                retry_detail.setVisibility(View.VISIBLE);

                retry_detail.setOnClickListener( v -> {
                    layout_eror.setVisibility(View.GONE);
                    layout_loading.setVisibility(View.VISIBLE);
                    handlerDet.postDelayed(() -> {
                        layout_loading.setVisibility(View.GONE);
                        layout_item.setVisibility(View.VISIBLE);
                    }, 100);
                    fetchProductDetails(productId);
                });
            }
        });
    }

    private void addToCart() {
        DatabaseHelper dbHelper = new DatabaseHelper(BarangActivity.this);
        dbHelper.addProductToCart(productId, productName, productPrice, productImage);

        // Pindah ke KeranjangFragment
        intent1 = new Intent();
        intent1.putExtra("update_cart", true);
        setResult(BarangActivity.RESULT_OK, intent1);

        // Finish this activity and go to MainActivity
        intent2 = new Intent(BarangActivity.this, MainActivity.class);
        intent2.putExtra("fragmentToLoad", "keranjangFragment");
        startActivity(intent2);
        finish();
    }
}