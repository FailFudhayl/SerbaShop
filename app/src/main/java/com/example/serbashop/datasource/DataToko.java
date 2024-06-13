package com.example.serbashop.datasource;


import com.example.serbashop.R;
import com.example.serbashop.model.Toko;

import java.util.ArrayList;

public class DataToko {
    public static ArrayList<Toko> tokos = generateDummyToko();

    private static ArrayList<Toko> generateDummyToko() {
        ArrayList<Toko> tokos1 = new ArrayList<>();
        tokos1.add(new Toko("Cabang Panakukang",
                R.drawable.store_panakukang,
                "10.00 - 22.00",
                "https://maps.app.goo.gl/v5DAKF4A99i1Bnvd8"));
        tokos1.add(new Toko("Cabang M'Tos",
                R.drawable.store_mtos,
                "10.00 - 22.00",
                "https://maps.app.goo.gl/Dzc5PxREJ4ibaQFM8"));
        tokos1.add(new Toko("Cabang Perintis",
                R.drawable.store_perintis,
                "10.00 - 22.00",
                "https://maps.app.goo.gl/VoMZhwGSw2E8UdE28"));
        tokos1.add(new Toko("Cabang Veteran",
                R.drawable.store_veteran,
                "10.00 - 22.00",
                "https://maps.app.goo.gl/5UPNPfwK6KEVn1MS6"));
        return tokos1;
    }
}

