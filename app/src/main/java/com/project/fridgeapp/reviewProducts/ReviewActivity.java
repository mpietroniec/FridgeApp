package com.project.fridgeapp.reviewProducts;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.fridgeapp.InfoActivity;
import com.project.fridgeapp.ItemClickListener;
import com.project.fridgeapp.R;
import com.project.fridgeapp.database.DatabaseHelper;
import com.project.fridgeapp.entities.FridgeProduct;
import com.project.fridgeapp.shoppingList.ShoppingList;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity implements ItemClickListener {

    private DrawerLayout drawerLayout;
    private FloatingActionButton btnAddProduct, btnGoToShoppingList;
    private RecyclerView recyclerView;
    private LinearLayout llEmptyView;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    List<FridgeProduct> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    DatabaseHelper database;
    ReviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        database = DatabaseHelper.getInstance(this);
        dataList = database.fridgeProductDao().getAllFridgeProducts();
        initRecyclerView(dataList);

        btnAddProduct = findViewById(R.id.fbtn_add_product);
        btnAddProduct.setOnClickListener(view -> {
            Intent intent = new Intent(ReviewActivity.this, AddingActivity.class);
            startActivity(intent);
        });

        btnGoToShoppingList = findViewById(R.id.fbtn_shopping_cart);
        btnGoToShoppingList.setOnClickListener(view -> {
            Intent intent = new Intent(ReviewActivity.this, ShoppingList.class);
            startActivity(intent);
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().setGroupCheckable(0, false, true);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }

    @Override
    public void onItemClickListener(int position) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_fridge_products, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        database = DatabaseHelper.getInstance(this);
        if (item.getItemId() == R.id.delete_all) {
            confirmDialog();
        } else if (item.getItemId() == R.id.sort_by_expiration_date) {
            dataList = database.fridgeProductDao().getAllFridgeProductsByExpirationDate();
            initRecyclerView(dataList);
        } else if (item.getItemId() == R.id.show_everything) {
            dataList = database.fridgeProductDao().getAllFridgeProducts();
            initRecyclerView(dataList);
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete_all_q);
        builder.setMessage(R.string.delete_everything);
        builder.setPositiveButton(R.string.yes, (dialog, which) -> {
            database = DatabaseHelper.getInstance(this);
            database.fridgeProductDao().deleteAll(dataList);
            Intent intent = new Intent(ReviewActivity.this, ReviewActivity.class);
            startActivity(intent);
        });
        builder.setNegativeButton(R.string.no, (dialog, which) -> {

        });
        builder.show();
    }

    void initRecyclerView(List<FridgeProduct> dataList) {
        recyclerView = findViewById(R.id.rv_review_products);
        llEmptyView = findViewById(R.id.ll_empty_text);
        if (dataList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            llEmptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            llEmptyView.setVisibility(View.GONE);
        }
        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ReviewAdapter(dataList, ReviewActivity.this, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        database = DatabaseHelper.getInstance(this);
        dataList = database.fridgeProductDao().getAllFridgeProducts();
        initRecyclerView(dataList);
    }

    public void startInfoActivity(MenuItem item) {
        Intent intent = new Intent(ReviewActivity.this, InfoActivity.class);
        startActivity(intent);
        drawerLayout.closeDrawers();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                database = DatabaseHelper.getInstance(this);
                switch (item.getItemId()) {
                    case R.id.id_menu_groceries:
                        dataList = database.fridgeProductDao().getAllGroceries();
                        bottomNavigationView.getMenu().setGroupCheckable(0, true, true);
                        //bottomNavigationView.getMenu().getItem(0).setChecked(true);
                        initRecyclerView(dataList);
                        break;
                    case R.id.id_menu_diapers:
                        dataList = database.fridgeProductDao().getAllDiapers();
                        bottomNavigationView.getMenu().setGroupCheckable(0, true, true);
                        // bottomNavigationView.getMenu().getItem(1).setChecked(true);
                        initRecyclerView(dataList);
                        break;
                    case R.id.id_menu_industrial_goods:
                        dataList = database.fridgeProductDao().getAllIndustrialGoods();
                        bottomNavigationView.getMenu().setGroupCheckable(0, false, true);
                        bottomNavigationView.getMenu().setGroupCheckable(2, true, true);
                        // bottomNavigationView.getMenu().getItem(2).setChecked(true);
                        initRecyclerView(dataList);
                        break;

                }
                return false;
            };
}