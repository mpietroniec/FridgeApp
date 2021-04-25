package com.project.fridgeapp.reviewProducts;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.project.fridgeapp.R;
import com.project.fridgeapp.database.DatabaseHelper;
import com.project.fridgeapp.entities.FridgeProduct;
import com.project.fridgeapp.helpers.DateParser;
import com.project.fridgeapp.helpers.DatePickerFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText etxtProductName, etxtProductAmount;
    private TextView txtExpirationDate, txtScanBarcode;
    private ImageView ivDeleteDate;
    private Spinner spinProductType;
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://world.openfoodfacts.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        etxtProductName = findViewById(R.id.etxt_add_name);

        etxtProductAmount = findViewById(R.id.etxt_add_amount);

        spinProductType = findViewById(R.id.spin_product_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.products_types, R.layout.spinner_product_type_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinProductType.setAdapter(adapter);

        ivDeleteDate = findViewById(R.id.iv_delete_date_in_add_activity);

        txtExpirationDate = findViewById(R.id.txt_expiration_date);
        txtExpirationDate.setOnClickListener(view -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        });

        txtExpirationDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ivDeleteDate.setVisibility(View.VISIBLE);
            }
        });

        ivDeleteDate.setOnClickListener(view -> {
            txtExpirationDate.setText("");
            ivDeleteDate.setVisibility(View.GONE);
        });

        btnAddProduct = findViewById(R.id.btn_add_product);
        btnAddProduct.setOnClickListener(view -> {
            String sProductName = etxtProductName.getText().toString().trim();
            String sAmount = etxtProductAmount.getText().toString().trim();
            long sProductType = spinProductType.getSelectedItemId();
            String sExpirationDate = txtExpirationDate.getText().toString().trim();

            if (!sProductName.equals("") && !sAmount.equals("")) {
                FridgeProduct fridgeProduct = new FridgeProduct();
                int dbAmount = Integer.parseInt(sAmount);
                fridgeProduct.setFridgeProductName(sProductName);
                fridgeProduct.setFridgeProductAmount(dbAmount);
                fridgeProduct.setFridgeProductType(sProductType);
                fridgeProduct.setFridgeProductExpirationDate(DateParser.stringToDateParser(sExpirationDate));
                long result = databaseHelper.fridgeProductDao().insert(fridgeProduct);
                if (result != -1) {
                    Toast.makeText(getApplicationContext(), R.string.added, Toast.LENGTH_SHORT).show();

                    fridgeProductsList.clear();
                    fridgeProductsList.addAll(databaseHelper.fridgeProductDao().getAllFridgeProducts());

                    etxtProductName.setText("");
                    etxtProductAmount.setText("");
                    spinProductType.setSelection(0);
                    txtExpirationDate.setText("");

                } else {
                    Toast.makeText(getApplicationContext(), R.string.failed, Toast.LENGTH_SHORT).show();
                }
            } else if (sProductName.equals("") && !sAmount.equals("")) {
                Toast.makeText(getApplicationContext(), R.string.complete_the_name, Toast.LENGTH_SHORT).show();
            } else if (!sProductName.equals("") && sAmount.equals("")) {
                Toast.makeText(getApplicationContext(), R.string.complete_the_amount, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), R.string.fill_in_the_blanks, Toast.LENGTH_SHORT).show();
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