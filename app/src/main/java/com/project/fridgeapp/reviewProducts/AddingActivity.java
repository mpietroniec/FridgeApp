package com.project.fridgeapp.reviewProducts;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.project.fridgeapp.R;
import com.project.fridgeapp.database.DatabaseHelper;
import com.project.fridgeapp.entities.FridgeProduct;
import com.project.fridgeapp.helpers.DateParser;
import com.project.fridgeapp.helpers.DatePickerFragment;
import com.project.fridgeapp.helpers.JsonPlaceHolderApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText etxtProductName, etxtProductAmount;
    private TextView txtExpirationDate, txtScanBarcode;
    private ImageView ivDeleteDate;
    private Button btnAddProduct;
    private DatabaseHelper databaseHelper;
    private List<FridgeProduct> fridgeProductsList = new ArrayList<>();
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private String barcodeScan;

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

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        etxtProductName = findViewById(R.id.etxt_add_name);

        txtScanBarcode = findViewById(R.id.txt_scan_barcode);
        txtScanBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                IntentIntegrator intentIntegrator = new IntentIntegrator(AddingActivity.this);
//                intentIntegrator.setBeepEnabled(true);
//                intentIntegrator.setOrientationLocked(true);
//                intentIntegrator.setCaptureActivity(Capture.class);
//                intentIntegrator.initiateScan();
                getProductName("737628064502");
            }
        });

        etxtProductAmount = findViewById(R.id.etxt_add_amount);

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
            } else if (sProductName.equals("") && !sAmount.equals("")) {
                Toast.makeText(getApplicationContext(), "Uzupełnij nazwe.", Toast.LENGTH_SHORT).show();
            } else if (!sProductName.equals("") && sAmount.equals("")) {
                Toast.makeText(getApplicationContext(), "Uzupełnij ilość.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Uzupełnij puste pola.", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, data
        );
        if (intentResult.getContents() != null) {
            barcodeScan = intentResult.getContents();
            getProductName("737628064502");
        }
    }

    private void getProductName(String barcode) {
        Call<String> call = jsonPlaceHolderApi.getNameFromApi(barcode);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Nie znaleziono", Toast.LENGTH_SHORT).show();
                    return;
                }
                etxtProductName.setText(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}