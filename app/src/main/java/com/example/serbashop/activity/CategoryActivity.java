package com.example.serbashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.serbashop.R;
import com.example.serbashop.adapter.ProductAdapter;
import com.example.serbashop.api.ApiService;
import com.example.serbashop.api.ProductResponse;
import com.example.serbashop.api.RetrofitClient;
import com.example.serbashop.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView rvBarangCategory;
    private ProductAdapter productAdapter;
    private List<Product> productList  = new ArrayList<>();
    TextView tv_toolbar, tv_title;
    LinearLayout eror_layout, loading_layout;
    Button btn_retry;

    Handler handler;
    String categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        categories = getIntent().getStringExtra("category");
        handler = new Handler(Looper.getMainLooper());

        rvBarangCategory = findViewById(R.id.rv_barangCategory);
        productAdapter = new ProductAdapter(productList, this);
        rvBarangCategory.setHasFixedSize(true);
        rvBarangCategory.setAdapter(productAdapter);

        tv_title = findViewById(R.id.Category_title);
        tv_toolbar = findViewById(R.id.Category_toolbar);
        eror_layout = findViewById(R.id.erorLayout_Category);
        loading_layout = findViewById(R.id.loading_category);
        btn_retry = findViewById(R.id.btn_retryCategory);

        tv_title.setText(categories);
        tv_toolbar.setText(categories);

        loading_layout.setVisibility(View.VISIBLE);
        handler.postDelayed(() -> {
            loading_layout.setVisibility(View.GONE);
        }, 1000);

        fetchProducts(categories);

    }

    private void fetchProducts(String category) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<ProductResponse> call = apiService.getProducts();

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    List<Product> allProducts = response.body().getProducts();
                    productList.clear();
                    for (Product product : allProducts) {
                        if (product.getCategory().equals(category)) {
                            productList.add(product);
                        }
                    }
                    productAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                rvBarangCategory.setVisibility(View.GONE);
                eror_layout.setVisibility(View.VISIBLE);
                btn_retry.setVisibility(View.VISIBLE);

                btn_retry.setOnClickListener( v -> {
                    eror_layout.setVisibility(View.GONE);
                    loading_layout.setVisibility(View.VISIBLE);
                    handler.postDelayed(() -> {
                        loading_layout.setVisibility(View.GONE);
                        rvBarangCategory.setVisibility(View.VISIBLE);
                    }, 100);
                    fetchProducts(categories);
                });
            }
        });
    }
}