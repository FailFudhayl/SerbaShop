package com.example.serbashop.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.serbashop.R;
import com.example.serbashop.adapter.TokoAdapter;
import com.example.serbashop.datasource.DataToko;

public class TokoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_toko, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvToko = view.findViewById(R.id.rv_toko);
        rvToko.setHasFixedSize(true);
        TokoAdapter tokoAdapter = new TokoAdapter(DataToko.tokos);
        rvToko.setAdapter(tokoAdapter);
    }
}