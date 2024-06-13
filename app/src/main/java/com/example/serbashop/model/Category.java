package com.example.serbashop.model;

public class Category {
    public Category(String name, int img_category) {
        this.name = name;
        this.img_category = img_category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg_category() {
        return img_category;
    }

    public void setImg_category(int img_category) {
        this.img_category = img_category;
    }

    private String name;
    private int img_category;
}
