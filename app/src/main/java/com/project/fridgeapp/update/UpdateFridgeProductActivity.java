package com.project.fridgeapp.update;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.project.fridgeapp.R;
import com.project.fridgeapp.helpers.DateParser;
import com.project.fridgeapp.helpers.DatePickerFragment;
import com.project.fridgeapp.database.DatabaseHelper;
import com.project.fridgeapp.entities.FridgeProduct;
import com.project.fridgeapp.entities.ShoppingListItem;
import com.project.fridgeapp.shoppingList.ShoppingList;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UpdateFridgeProductActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText etxtUpdateName, etxtUpdateAmount;
    private TextView txtUpdateDate;
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
        Date sExpirationDate = fridgeProduct.getFridgeProductExpirationDate();

        etxtUpdateName = findViewById(R.id.etxt_update_name);
        etxtUpdateName.setText(sProductName);

        etxtUpdateAmount = findViewById(R.id.etxt_update_amount);
        etxtUpdateAmount.setText(String.valueOf(sAmount));

        txtUpdateDate = findViewById(R.id.txt_update_expiration_date);
        if (!sExpirationDate.toString().equals("Thu Jan 01 00:00:00 GMT 1970")) {
            txtUpdateDate.setText(dateFormat(sExpirationDate));
        }

        txtUpdateDate.setOnClickListener(view -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        });

        btnUpdate = findViewById(R.id.btn_update_product);
        btnUpdate.setOnClickListener(view -> {
            String sProductName1 = etxtUpdateName.getText().toString().trim();
            int sAmount1 = Integer.parseInt(etxtUpdateAmount.getText().toString().trim());
            Date sExpirationDate1 = DateParser.stringToDateParser(txtUpdateDate.getText().toString().trim());
            database.fridgeProductDao().update(sFridgeID, sProductName1, sAmount1, sExpirationDate1);
        });
    }

    public String dateFormat(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(date.getTime());
        return dateString;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        TextView textView = findViewById(R.id.txt_update_expiration_date);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(calendar.getTime());

        textView.setText(dateString);
    }
}