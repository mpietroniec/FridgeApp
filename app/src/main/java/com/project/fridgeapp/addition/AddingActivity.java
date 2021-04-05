package com.project.fridgeapp.addition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.project.fridgeapp.R;
import com.project.fridgeapp.database.DatabaseHelper;
import com.project.fridgeapp.entities.FridgeProduct;
import com.project.fridgeapp.helpers.DateParser;
import com.project.fridgeapp.helpers.DatePickerFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText etxtProductName, etxtProductAmount;
    private TextView txtExpirationDate;
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

        txtExpirationDate = findViewById(R.id.txt_expiration_date);
        txtExpirationDate.setOnClickListener(view -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        });

        btnAddProduct = findViewById(R.id.btn_add_product);
        btnAddProduct.setOnClickListener(view -> {
            String sProductName = etxtProductName.getText().toString().trim();
            String sAmount = etxtProductAmount.getText().toString().trim();
            String sExpirationDate = txtExpirationDate.getText().toString().trim();

            if (!sProductName.equals("") && !sAmount.equals("")) {
                FridgeProduct fridgeProduct = new FridgeProduct();
                int dbAmount = Integer.parseInt(sAmount);
                fridgeProduct.setFridgeProductName(sProductName);
                fridgeProduct.setFridgeProductAmount(dbAmount);
                fridgeProduct.setFridgeProductExpirationDate(DateParser.stringToDateParser(sExpirationDate));
                databaseHelper.fridgeProductDao().insert(fridgeProduct);
                fridgeProductsList.clear();
                fridgeProductsList.addAll(databaseHelper.fridgeProductDao().getAllFridgeProducts());
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        TextView textView = findViewById(R.id.txt_expiration_date);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(calendar.getTime());

        textView.setText(dateString);
    }
}