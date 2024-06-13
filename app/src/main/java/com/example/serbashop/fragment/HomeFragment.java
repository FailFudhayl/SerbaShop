package com.example.serbashop.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import com.example.serbashop.R;
import com.example.serbashop.adapter.CategoryAdapter;
import com.example.serbashop.adapter.NewsAdapter;
import com.example.serbashop.adapter.ProductAdapter;
import com.example.serbashop.adapter.TokoAdapter;
import com.example.serbashop.api.ApiService;
import com.example.serbashop.api.ProductResponse;
import com.example.serbashop.api.RetrofitClient;
import com.example.serbashop.datasource.DataCategory;
import com.example.serbashop.datasource.DataNews;
import com.example.serbashop.datasource.DataToko;
import com.example.serbashop.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private List<Product> productList = new ArrayList<>();
    RecyclerView rvBarang;
    ProductAdapter productAdapter;
    Handler handler;
    LinearLayout layout_error, loading_layout;
    Button retry_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        retry_btn = view.findViewById(R.id.btn_retryHome);
        layout_error = view.findViewById(R.id.erorLayout_Home);
        loading_layout = view.findViewById(R.id.loading_barang);

        RecyclerView rvNews = view.findViewById(R.id.recyclerView_current_news);
        rvNews.setHasFixedSize(true);
        NewsAdapter newsAdapter = new NewsAdapter(DataNews.newss);
        rvNews.setAdapter(newsAdapter);

        RecyclerView rvCat = view.findViewById(R.id.rv_category);
        rvCat.setHasFixedSize(true);
        CategoryAdapter categoryAdapter = new CategoryAdapter(DataCategory.categories, getContext());
        rvCat.setAdapter(categoryAdapter);

        rvBarang = view.findViewById(R.id.rv_barang);
        productAdapter = new ProductAdapter(productList, getContext());
        rvBarang.setAdapter(productAdapter);

        handler = new Handler(Looper.getMainLooper());
        loading_layout.setVisibility(View.VISIBLE);
        handler.postDelayed(() -> {
            loading_layout.setVisibility(View.GONE);
        }, 1000);

        fetchProducts();
    }

    private void fetchProducts() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<ProductResponse> call = apiService.getProducts();

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productList.addAll(response.body().getProducts());
                    productAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                rvBarang.setVisibility(View.GONE);
                layout_error.setVisibility(View.VISIBLE);
                retry_btn.setVisibility(View.VISIBLE);

                retry_btn.setOnClickListener( v -> {
                    layout_error.setVisibility(View.GONE);
                    loading_layout.setVisibility(View.VISIBLE);
                    handler.postDelayed(() -> {
                        loading_layout.setVisibility(View.GONE);
                        rvBarang.setVisibility(View.VISIBLE);
                    }, 100);
                    fetchProducts();
                });
            }
        });
    }
}