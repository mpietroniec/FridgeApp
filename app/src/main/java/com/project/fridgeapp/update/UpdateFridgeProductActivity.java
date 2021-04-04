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
import com.project.fridgeapp.entities.FridgeProduct;
import com.project.fridgeapp.entities.ShoppingListItem;

import java.util.List;

public class UpdateFridgeProductActivity extends AppCompatActivity {
    private EditText etxtUpdateName, etxtUpdateAmount;
    private Button btnUpdate;
    private DatabaseHelper database;
    private Context context;
    private List<ShoppingListItem> shoppingListItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_fridge_product);

        Intent intent = getIntent();
        FridgeProduct fridgeProduct = intent.getParcelableExtra("Fridge Product");

        database = DatabaseHelper.getInstance(context);

        long sFridgeID = fridgeProduct.getFridgeID();
        String sProductName = fridgeProduct.getFridgeProductName();
        int sAmount = fridgeProduct.getFridgeProductAmount();

        etxtUpdateName = findViewById(R.id.etxt_update_name);
        etxtUpdateName.setText(sProductName);

        etxtUpdateAmount = findViewById(R.id.etxt_update_amount);
        etxtUpdateAmount.setText(String.valueOf(sAmount));

        btnUpdate = findViewById(R.id.btn_update_product);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sProductName = etxtUpdateName.getText().toString().trim();
                int sAmount = Integer.parseInt(etxtUpdateAmount.getText().toString().trim());

                database.fridgeProductDao().update(sFridgeID, sProductName, sAmount);
            }
        });
    }
}