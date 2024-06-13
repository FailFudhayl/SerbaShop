package com.example.serbashop.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serbashop.R;
import com.example.serbashop.activity.PembayaranActivity;
import com.example.serbashop.localdatabase.DatabaseHelper;
import com.example.serbashop.model.CartItem;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItemList;
    private Context context;

    public CartAdapter(List<CartItem> cartItemList, Context context) {
        this.cartItemList = cartItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_keranjang, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        holder.tvProductName.setText(cartItem.getProductName());
        holder.tvProductPrice.setText(cartItem.getProductPrice());
        Picasso.get().load(cartItem.getProductImage()).into(holder.ivProductImage);
        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        holder.btn_hapus.setOnClickListener(view -> {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long result = databaseHelper.deleteCartItem(db, cartItem.getId());
            if (result != -1) {
                cartItemList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartItemList.size());
            }
            db.close();
        });

        holder.btn_beli.setOnClickListener(view -> {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            long result = databaseHelper.addProductToRiwayat(db, cartItem.getProductId(), cartItem.getProductName(), cartItem.getProductPrice(), cartItem.getProductImage(), currentDate);

            if (result != -1) {
                long deleteResult = databaseHelper.deleteCartItem(db, cartItem.getId());
                if (deleteResult != -1) {
                    cartItemList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, cartItemList.size());
                }
            }
            db.close();

            // Start PembayaranActivity
            Intent intent = new Intent(context, PembayaranActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvProductPrice;
        ImageView ivProductImage;
        Button btn_hapus, btn_beli;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.TV_ProductNameBasket);
            tvProductPrice = itemView.findViewById(R.id.TV_ProductPriceBasket);
            ivProductImage = itemView.findViewById(R.id.IV_ProductImageBasket);
            btn_hapus = itemView.findViewById(R.id.btn_removeBasket);
            btn_beli = itemView.findViewById(R.id.btn_checkoutBasket);
        }
    }
}
