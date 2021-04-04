package com.project.fridgeapp.update;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.project.fridgeapp.R;
import com.project.fridgeapp.database.DatabaseHelper;
import com.project.fridgeapp.entities.ShoppingListItem;

import java.util.List;

public class UpdateShoppingListItemActivity extends AppCompatActivity {
    private EditText etxtUpdateShoppingListItemName, etxtUpdateShoppingListItemAmount, etxtUpdateShoppingListItemShopName;
    private Button btnUpdate;
    private DatabaseHelper database;
    private Context context;
    private List<ShoppingListItem> shoppingListItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_shopping_list_item);

        Intent intent = getIntent();
        ShoppingListItem shoppingListItem = intent.getParcelableExtra("Shopping List Item");

        database = DatabaseHelper.getInstance(context);

        long sShoppingListItemID = shoppingListItem.getShoppingListItemID();
        String shoppingListItemName = shoppingListItem.getShoppingListItemName();
        int shoppingListItemAmount = shoppingListItem.getShoppingListItemAmount();
        String shoppingListItemShopName = shoppingListItem.getShoppingListItemShopName();

        etxtUpdateShoppingListItemName = findViewById(R.id.etxt_update_shopping_list_name);
        etxtUpdateShoppingListItemName.setText(shoppingListItemName);

        etxtUpdateShoppingListItemAmount = findViewById(R.id.etxt_update_shopping_list_amount);
        etxtUpdateShoppingListItemAmount.setText(String.valueOf(shoppingListItemAmount));

        etxtUpdateShoppingListItemShopName = findViewById(R.id.etxt_update_shopping_list_shop_name);
        etxtUpdateShoppingListItemShopName.setText(shoppingListItemShopName);

        btnUpdate = findViewById(R.id.btn_update_shopping_list);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sShoppingListItemName = etxtUpdateShoppingListItemName.getText().toString().trim();
                int sShoppingListItemAmount = Integer.parseInt(etxtUpdateShoppingListItemAmount.getText().toString().trim());
                String sShoppingListItemShopName = etxtUpdateShoppingListItemShopName.getText().toString().trim();

                database.shoppingListItemDao().update(sShoppingListItemID, sShoppingListItemName, sShoppingListItemAmount, sShoppingListItemShopName);
            }
        });
    }
}