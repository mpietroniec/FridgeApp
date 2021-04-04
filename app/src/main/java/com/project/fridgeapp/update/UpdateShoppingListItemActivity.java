package com.project.fridgeapp.update;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.project.fridgeapp.R;
import com.project.fridgeapp.entities.ShoppingListItem;

public class UpdateShoppingListItemActivity extends AppCompatActivity {
    private EditText etxtUpdateShoppingListItemName, etxtUpdateShoppingListItemAmount, etxtUpdateShoppingListItemShopName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_shopping_list_item);

        Intent intent = getIntent();
        ShoppingListItem shoppingListItem = intent.getParcelableExtra("Shopping List Item");

        String shoppingListItemName = shoppingListItem.getShoppingListItemName();
        int shoppingListItemAmount = shoppingListItem.getShoppingListItemAmount();
        String shoppingListItemShopName = shoppingListItem.getShoppingListItemShopName();

        etxtUpdateShoppingListItemName = findViewById(R.id.etxt_update_shopping_list_name);
        etxtUpdateShoppingListItemName.setText(shoppingListItemName);

        etxtUpdateShoppingListItemAmount = findViewById(R.id.etxt_update_shopping_list_amount);
        etxtUpdateShoppingListItemAmount.setText(String.valueOf(shoppingListItemAmount));

        etxtUpdateShoppingListItemShopName = findViewById(R.id.etxt_update_shopping_list_shop_name);
        etxtUpdateShoppingListItemShopName.setText(shoppingListItemShopName);
    }
}