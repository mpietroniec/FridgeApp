package com.project.fridgeapp.shoppingList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.fridgeapp.ItemClickListener;
import com.project.fridgeapp.R;
import com.project.fridgeapp.database.DatabaseHelper;
import com.project.fridgeapp.entities.ShoppingListItem;
import com.project.fridgeapp.reviewProducts.ReviewActivity;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList extends AppCompatActivity implements ItemClickListener {

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
        adapter = new ShoppingListAdapter(dataList, ShoppingList.this, this);
        recyclerView.setAdapter(adapter);

        btnAddToShoppingList = findViewById(R.id.fbtn_open_shopping_list);
        btnAddToShoppingList.setOnClickListener(view -> {
            Intent intent = new Intent(ShoppingList.this, AddToShoppingList.class);
            startActivity(intent);
        });
    }

    @Override
    public void onItemClickListener(int position) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_delete_all_shopping_list_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all_shopping_list_items) {
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
            database.shoppingListItemDao().deleteAll(dataList);
            Intent intent = new Intent(ShoppingList.this, ShoppingList.class);
            startActivity(intent);
        });
        builder.setNegativeButton("Nie", (dialog, which) -> {

        });
        builder.show();
    }
}