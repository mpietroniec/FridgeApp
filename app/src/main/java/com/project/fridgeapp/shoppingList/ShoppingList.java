package com.project.fridgeapp.shoppingList;

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
import com.project.fridgeapp.database.DatabaseHelper;
import com.project.fridgeapp.entities.ShoppingListItem;

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

        database = DatabaseHelper.getInstance(this);

        dataList = database.shoppingListItemDao().getAllShoppingListItems();
        initRecyclerView(dataList);

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
        inflater.inflate(R.menu.menu_shopping_list_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all_shopping_list_items) {
            confirmDialog();
        } else if (item.getItemId() == R.id.sort_by_name) {

            database = DatabaseHelper.getInstance(this);

            dataList = database.shoppingListItemDao().getAllShoppingListByName();
            initRecyclerView(dataList);

        } else if (item.getItemId() == R.id.sort_by_shop_name) {

            database = DatabaseHelper.getInstance(this);

            dataList = database.shoppingListItemDao().getAllShoppingListByShopName();
            initRecyclerView(dataList);

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

    void initRecyclerView(List<ShoppingListItem> dataList) {
        recyclerView = findViewById(R.id.rv_shopping_list);

        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ShoppingListAdapter(dataList, ShoppingList.this, this);
        recyclerView.setAdapter(adapter);
    }
}