package com.project.fridgeapp.shoppingList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.fridgeapp.R;

public class ShoppingList extends AppCompatActivity {

    private FloatingActionButton btnAddToShoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        btnAddToShoppingList = findViewById(R.id.fbtn_open_shopping_list);
        btnAddToShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingList.this, AddToShoppingList.class);
                startActivity(intent);
            }
        });
    }
}