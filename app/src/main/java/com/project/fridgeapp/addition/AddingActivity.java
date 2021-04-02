package com.project.fridgeapp.addition;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.project.fridgeapp.R;
import com.project.fridgeapp.database.DatabaseHelper;
import com.project.fridgeapp.entities.FridgeProduct;

import java.util.ArrayList;
import java.util.List;

public class AddingActivity extends AppCompatActivity {

    private EditText etxtProductName, etxtProductAmount;
    private Button btnAddProduct;
    private DatabaseHelper databaseHelper;
    private List<FridgeProduct> fridgeProductsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);

        //Initialize database
        databaseHelper = DatabaseHelper.getInstance(this);
        //Store database
        fridgeProductsList = databaseHelper.fridgeProductDao().getAllFridgeProducts();

        etxtProductName = findViewById(R.id.etxt_add_name);
        etxtProductAmount = findViewById(R.id.etxt_add_amount);

        btnAddProduct = findViewById(R.id.btn_add_product);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sProductName = etxtProductName.getText().toString().trim();
                String sAmount = etxtProductAmount.getText().toString().trim();

                if (!sProductName.equals("") && !sAmount.equals("")) {
                    FridgeProduct fridgeProduct = new FridgeProduct();
                    int dbAmount = Integer.parseInt(sAmount);
                    fridgeProduct.setFridgeProductName(sProductName);
                    fridgeProduct.setFridgeProductAmount(dbAmount);
                    databaseHelper.fridgeProductDao().insert(fridgeProduct);
                    fridgeProductsList.clear();
                    fridgeProductsList.addAll(databaseHelper.fridgeProductDao().getAllFridgeProducts());
                }
            }
        });
    }
}