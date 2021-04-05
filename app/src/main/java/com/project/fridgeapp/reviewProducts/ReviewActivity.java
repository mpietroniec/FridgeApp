package com.project.fridgeapp.reviewProducts;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
        btnAddProduct.setOnClickListener(view -> {
            Intent intent = new Intent(ReviewActivity.this, AddingActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onItemClickListener(int position) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_delete_all_fridge_products, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all) {
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete all?");
        builder.setMessage("Are you sure you want to delete everything?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            database = DatabaseHelper.getInstance(this);
            database.fridgeProductDao().deleteAll(dataList);
            Intent intent = new Intent(ReviewActivity.this, ReviewActivity.class);
            startActivity(intent);
        });
        builder.setNegativeButton("Nie", (dialog, which) -> {

        });
        builder.show();
    }
}