package com.project.fridgeapp.update;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.project.fridgeapp.R;
import com.project.fridgeapp.entities.FridgeProduct;

public class UpdateFridgeProductActivity extends AppCompatActivity {
    private EditText etxtUpdateName, etxtUpdateAmount;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_fridge_product);

        Intent intent = getIntent();
        FridgeProduct fridgeProduct = intent.getParcelableExtra("Fridge Product");

        String productName = fridgeProduct.getFridgeProductName();
        int productAmount = fridgeProduct.getFridgeProductAmount();

        etxtUpdateName = findViewById(R.id.etxt_update_name);
        etxtUpdateName.setText(productName);

        etxtUpdateAmount = findViewById(R.id.etxt_update_amount);
        etxtUpdateAmount.setText(String.valueOf(productAmount));
    }
}