package com.project.fridgeapp.shoppingList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.fridgeapp.R;
import com.project.fridgeapp.database.DatabaseHelper;
import com.project.fridgeapp.entities.ShoppingListItem;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList extends AppCompatActivity {

    private FloatingActionButton btnAddToShoppingList;
    private RecyclerView recyclerView;

    List<ShoppingListItem> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    DatabaseHelper database;
    ShoppingListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        recyclerView = findViewById(R.id.rv_shopping_list);

        database = DatabaseHelper.getInstance(this);

        dataList = database.shoppingListItemDao().getAllShoppingListItems();

        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ShoppingListAdapter(dataList, ShoppingList.this);
        recyclerView.setAdapter(adapter);

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