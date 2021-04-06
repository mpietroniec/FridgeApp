package com.project.fridgeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.project.fridgeapp.addition.AddingActivity;
import com.project.fridgeapp.fridgeImage.FridgeImageActivity;
import com.project.fridgeapp.reviewProducts.ReviewActivity;
import com.project.fridgeapp.shoppingList.ShoppingList;

public class MainActivity extends AppCompatActivity {

    private Button addProductBtn, shoppingListBtn, viewProductsBtn, imageFridgeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addProductBtn = findViewById(R.id.btn_add);
        shoppingListBtn = findViewById(R.id.btn_list);
        viewProductsBtn = findViewById(R.id.btn_view);
        imageFridgeBtn = findViewById(R.id.btn_image);

        viewProductsBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ReviewActivity.class);
            startActivity(intent);
        });

        addProductBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddingActivity.class);
            startActivity(intent);
        });

        shoppingListBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ShoppingList.class);
            startActivity(intent);
        });

        imageFridgeBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FridgeImageActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_info) {
            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}