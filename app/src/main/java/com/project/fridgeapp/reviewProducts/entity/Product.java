package com.project.fridgeapp.reviewProducts.entity;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("product_name")
    private String name;

    public String getName() {
        return name;
    }
}
