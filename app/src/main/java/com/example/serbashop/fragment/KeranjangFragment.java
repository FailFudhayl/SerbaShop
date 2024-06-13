package com.example.serbashop.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serbashop.R;
import com.example.serbashop.activity.BarangActivity;
import com.example.serbashop.adapter.CartAdapter;
import com.example.serbashop.localdatabase.DatabaseHelper;
import com.example.serbashop.model.CartItem;
import com.example.serbashop.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

public class KeranjangFragment extends Fragment {

    private RecyclerView rvCart;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItemList;
    private DatabaseHelper dbHelper;

    Button retry_button;
    LinearLayout eror_layout, layout_loading;
    Handler handler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keranjang, container, false);

        retry_button = view.findViewById(R.id.btn_retryKeranjang);
        eror_layout = view.findViewById(R.id.erorLayout_Keranjang);
        layout_loading = view.findViewById(R.id.loading_Keranjang);

        rvCart = view.findViewById(R.id.rv_keranjang);
        rvCart.setLayoutManager(new LinearLayoutManager(getContext()));
        cartItemList = new ArrayList<>();
        cartAdapter = new CartAdapter(cartItemList, getContext());
        rvCart.setAdapter(cartAdapter);

        dbHelper = new DatabaseHelper(getContext());

        handler = new Handler(Looper.getMainLooper());
        layout_loading.setVisibility(View.VISIBLE);
        handler.postDelayed(() -> {
            layout_loading.setVisibility(View.GONE);
        }, 1000);
        loadCartItems();
        checkNetworkAndLoadData();

        return view;
    }

    private void loadCartItems() {
        cartItemList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_CART, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                int productId = cursor.getInt(cursor.getColumnIndexOrThrow("product_id"));
                String productName = cursor.getString(cursor.getColumnIndexOrThrow("product_name"));
                String productPrice = cursor.getString(cursor.getColumnIndexOrThrow("product_price"));
                String productImage = cursor.getString(cursor.getColumnIndexOrThrow("product_image"));

                CartItem cartItem = new CartItem(id, productId, productName, productPrice, productImage);
                cartItemList.add(cartItem);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Notify the adapter to update the RecyclerView
        cartAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == BarangActivity.RESULT_OK && data != null) {
            // Refresh the cart items
            loadCartItems();
            Toast.makeText(getContext(), "Item added to cart", Toast.LENGTH_SHORT).show();
        }
    }


    private void checkNetworkAndLoadData() {
        if (NetworkUtil.isNetworkConnected(getActivity())) {
            rvCart.setVisibility(View.VISIBLE);
            eror_layout.setVisibility(View.GONE);
            loadCartItems();
        } else {
            eror_layout.setVisibility(View.VISIBLE);
            retry_button.setVisibility(View.VISIBLE);

            retry_button.setOnClickListener( v -> {
                eror_layout.setVisibility(View.GONE);
                layout_loading.setVisibility(View.VISIBLE);
                handler.postDelayed(() -> {
                    layout_loading.setVisibility(View.GONE);
                    rvCart.setVisibility(View.VISIBLE);
                }, 100);
                loadCartItems();
            });
        }
    }
}