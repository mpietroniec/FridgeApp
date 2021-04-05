package com.project.fridgeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddingActivity.class);
                startActivity(intent);
            }
        });

        shoppingListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShoppingList.class);
                startActivity(intent);
            }
        });

        viewProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ReviewActivity.class);
                startActivity(intent);
            }
        });

        imageFridgeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FridgeImageActivity.class);
                startActivity(intent);
            }
        });
    }
}