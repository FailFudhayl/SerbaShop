package com.example.serbashop.datasource;

import com.example.serbashop.R;
import com.example.serbashop.model.Category;
import com.example.serbashop.model.News;

import java.util.ArrayList;

public class DataCategory {
    public static ArrayList<Category> categories = generateDummyCategory();

    private static ArrayList<Category> generateDummyCategory() {
        ArrayList<Category> categories1 = new ArrayList<>();
        categories1.add(new Category("beauty",
                R.drawable.beauty));
        categories1.add(new Category("fragrances", R.drawable.fregrance));
        categories1.add(new Category("furniture", R.drawable.furniture));
        categories1.add(new Category("groceries", R.drawable.grocery));
        return  categories1;
    }

}
