package com.project.fridgeapp.reviewProducts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.fridgeapp.ItemClickListener;
import com.project.fridgeapp.R;
import com.project.fridgeapp.addition.AddingActivity;
import com.project.fridgeapp.database.DatabaseHelper;
import com.project.fridgeapp.entities.FridgeProduct;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity implements ItemClickListener {

    private FloatingActionButton btnAddProduct;
    private RecyclerView recyclerView;

    List<FridgeProduct> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    DatabaseHelper database;
    ReviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        recyclerView = findViewById(R.id.rv_review_products);

        database = DatabaseHelper.getInstance(this);

        dataList = database.fridgeProductDao().getAllFridgeProducts();

        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ReviewAdapter(dataList, ReviewActivity.this, this);
        recyclerView.setAdapter(adapter);



        btnAddProduct = findViewById(R.id.fbtn_add_product);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReviewActivity.this, AddingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClickListener(int position) {

    }
}