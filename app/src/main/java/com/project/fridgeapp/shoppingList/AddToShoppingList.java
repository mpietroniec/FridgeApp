package com.project.fridgeapp.shoppingList;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.fridgeapp.R;
import com.project.fridgeapp.database.DatabaseHelper;
import com.project.fridgeapp.entities.ShoppingListItem;

import java.util.ArrayList;
import java.util.List;

public class AddToShoppingList extends AppCompatActivity {

    private EditText etxtShopListItemName, etxtShopListItemAmount, etxtShopListItemShopName;
    private ImageView ivDeleteShopName;
    private Button btnAddToShoppingList;
    private DatabaseHelper databaseHelper;
    private List<ShoppingListItem> shoppingListItemsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_shopping_list);

        //Initialize database
        databaseHelper = DatabaseHelper.getInstance(this);
        //Store database
        shoppingListItemsList = databaseHelper.shoppingListItemDao().getAllShoppingListItems();

        etxtShopListItemName = findViewById(R.id.etxt_shopping_list_add_name);
        etxtShopListItemAmount = findViewById(R.id.etxt_shopping_list_add_amount);
        etxtShopListItemShopName = findViewById(R.id.etxt_shopping_list_add_shop_name);

        ivDeleteShopName = findViewById(R.id.iv_delete_add_to_shopping_list);

        etxtShopListItemShopName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ivDeleteShopName.setVisibility(View.VISIBLE);
            }
        });

        ivDeleteShopName.setOnClickListener(view -> {
            etxtShopListItemShopName.setText("");
            ivDeleteShopName.setVisibility(View.GONE);
        });

        btnAddToShoppingList = findViewById(R.id.btn_shopping_list_add);
        btnAddToShoppingList.setOnClickListener(view -> {
            String sShoppingListItemName = etxtShopListItemName.getText().toString().trim();
            String sShoppingListItemAmount = etxtShopListItemAmount.getText().toString().trim();
            String sShoppingListItemShopName = etxtShopListItemShopName.getText().toString().trim();

            if (!sShoppingListItemName.equals("") && !sShoppingListItemAmount.equals("")) {
                ShoppingListItem shoppingListItem = new ShoppingListItem();
                int dbShoppingListItemAmount = Integer.parseInt(sShoppingListItemAmount);
                shoppingListItem.setShoppingListItemName(sShoppingListItemName);
                shoppingListItem.setShoppingListItemAmount(dbShoppingListItemAmount);
                shoppingListItem.setShoppingListItemShopName(sShoppingListItemShopName);
                databaseHelper.shoppingListItemDao().insert(shoppingListItem);

                etxtShopListItemName.setText("");
                etxtShopListItemAmount.setText("");
                etxtShopListItemShopName.setText("");

                Toast.makeText(AddToShoppingList.this, "Added!.", Toast.LENGTH_SHORT).show();

                shoppingListItemsList.clear();
                shoppingListItemsList.addAll(databaseHelper.shoppingListItemDao().getAllShoppingListItems());
            } else if (sShoppingListItemName.equals("") && !sShoppingListItemAmount.equals("") && !sShoppingListItemShopName.equals("")) {
                Toast.makeText(AddToShoppingList.this, "Enter the product name.", Toast.LENGTH_SHORT).show();
            } else if (sShoppingListItemName.equals("") && sShoppingListItemAmount.equals("") && !sShoppingListItemShopName.equals("")) {
                Toast.makeText(AddToShoppingList.this, "Enter the product amount.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddToShoppingList.this, "Uzupełnij puste pola.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}