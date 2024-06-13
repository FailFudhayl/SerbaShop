package com.example.serbashop.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serbashop.R;
import com.example.serbashop.model.Toko;

import java.util.ArrayList;

public class TokoAdapter extends RecyclerView.Adapter<TokoAdapter.ViewHolder> {

    public TokoAdapter(ArrayList<Toko> tokos) {
        this.tokos = tokos;
    }

    private ArrayList<Toko> tokos;

    @NonNull
    @Override
    public TokoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_toko, parent, false);
        return new TokoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TokoAdapter.ViewHolder holder, int position) {
        Toko toko = tokos.get(position);
        holder.IV_tokoFoto.setImageResource(toko.getToko_img());
        holder.TV_titleToko.setText(toko.getNama_toko());
        holder.TV_waktuBuka.setText(toko.getTime());

        holder.btn_keLokasi.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(toko.getUrl_toko()));
            holder.context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return tokos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView IV_tokoFoto;
        TextView TV_titleToko;
        TextView TV_waktuBuka;
        Button btn_keLokasi;
        Context context;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            IV_tokoFoto = itemView.findViewById(R.id.IV_ItemToko);
            TV_titleToko = itemView.findViewById(R.id.TV_TokoName);
            TV_waktuBuka = itemView.findViewById(R.id.TV_TokoTime);
            btn_keLokasi = itemView.findViewById(R.id.btn_lokasiOutlet);
            context = itemView.getContext();
        }
    }

}

