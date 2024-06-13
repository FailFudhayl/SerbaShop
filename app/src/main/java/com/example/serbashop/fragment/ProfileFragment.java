package com.example.serbashop.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import com.example.serbashop.R;
import com.example.serbashop.adapter.RiwayatAdapter;
import com.example.serbashop.localdatabase.DatabaseHelper;
import com.example.serbashop.model.Riwayat;
import com.example.serbashop.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {

    private RecyclerView rvRiwayat;
    private RiwayatAdapter riwayatAdapter;
    private List<Riwayat> riwayatItemList = new ArrayList<>();

    Button retry_button;
    LinearLayout eror_layout, layout_loading;
    Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        retry_button = view.findViewById(R.id.btn_retryHome);
        eror_layout = view.findViewById(R.id.erorLayout_Profile);
        layout_loading = view.findViewById(R.id.loading_profile);

        rvRiwayat = view.findViewById(R.id.rv_riwayat);
        riwayatAdapter = new RiwayatAdapter(riwayatItemList, getContext());
        rvRiwayat.setAdapter(riwayatAdapter);

        handler = new Handler(Looper.getMainLooper());
        layout_loading.setVisibility(View.VISIBLE);
        handler.postDelayed(() -> {
            layout_loading.setVisibility(View.GONE);
        }, 1000);
        loadRiwayatItems();
        checkNetworkAndLoadData();
        return view;
    }

    private void loadRiwayatItems() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_RIWAYAT, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                int productId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUCT_ID));
                String productName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUCT_NAME));
                String productPrice = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUCT_PRICE));
                String productImage = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUCT_IMAGE));
                String transactionDate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TRANSACTION_DATE));

                Riwayat riwayatItem = new Riwayat(id, productId, productName, productPrice, productImage, transactionDate);
                riwayatItemList.add(riwayatItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        riwayatAdapter.notifyDataSetChanged();
    }

    private void checkNetworkAndLoadData() {
        if (NetworkUtil.isNetworkConnected(getActivity())) {
            rvRiwayat.setVisibility(View.VISIBLE);
            eror_layout.setVisibility(View.GONE);
            loadRiwayatItems();
        } else {
            eror_layout.setVisibility(View.VISIBLE);
            retry_button.setVisibility(View.VISIBLE);

            retry_button.setOnClickListener( v -> {
                eror_layout.setVisibility(View.GONE);
                layout_loading.setVisibility(View.VISIBLE);
                handler.postDelayed(() -> {
                    layout_loading.setVisibility(View.GONE);
                    rvRiwayat.setVisibility(View.VISIBLE);
                }, 100);
                loadRiwayatItems();
            });
        }
    }
}