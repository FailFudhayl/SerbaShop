package com.example.serbashop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serbashop.R;
import com.example.serbashop.model.Riwayat;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder> {

    private List<Riwayat> riwayatItemList;
    private Context context;

    public RiwayatAdapter(List<Riwayat> riwayatItemList, Context context) {
        this.riwayatItemList = riwayatItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public RiwayatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_riwayat, parent, false);
        return new RiwayatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatViewHolder holder, int position) {
        Riwayat riwayat = riwayatItemList.get(position);
        holder.tvProductName.setText(riwayat.getProductName());
        holder.tvTransactionDate.setText("Transaction Date: " + riwayat.getTransactionDate());
        holder.tvTotalPrice.setText("Total: " + riwayat.getProductPrice());
        Picasso.get().load(riwayat.getProductImage()).into(holder.ivProductImage);
    }

    @Override
    public int getItemCount() {
        return riwayatItemList.size();
    }

    public static class RiwayatViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvTransactionDate, tvTotalPrice;
        ImageView ivProductImage;

        public RiwayatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.TV_ProductNameRiwayat);
            tvTransactionDate = itemView.findViewById(R.id.TV_TransactionDate);
            tvTotalPrice = itemView.findViewById(R.id.TV_TotalPriceRiwayat);
            ivProductImage = itemView.findViewById(R.id.IV_ProductImageRiwayat);
        }
    }
}

