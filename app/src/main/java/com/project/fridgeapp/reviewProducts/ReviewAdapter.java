package com.project.fridgeapp.reviewProducts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.fridgeapp.R;
import com.project.fridgeapp.database.DatabaseHelper;
import com.project.fridgeapp.entities.FridgeProduct;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.FridgeProductsViewHolder> {

    private List<FridgeProduct> fridgeProductsList;
    private Context context;
    private DatabaseHelper database;

    public ReviewAdapter(List<FridgeProduct> fridgeProductsList, Context context) {
        this.fridgeProductsList = fridgeProductsList;
        this.context = context;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public FridgeProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_review_row, parent, false);
        return new FridgeProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FridgeProductsViewHolder holder, int position) {
        FridgeProduct fridgeProduct = fridgeProductsList.get(position);
        //Initialize database
        database = DatabaseHelper.getInstance(context);
        holder.txtProductID.setText(String.valueOf(position + 1));
        holder.txtName.setText(fridgeProduct.getFridgeProductName());
        holder.txtAmount.setText(String.valueOf(fridgeProduct.getFridgeProductAmount()));
    }

    @Override
    public int getItemCount() {
        return fridgeProductsList.size();
    }

    public class FridgeProductsViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtAmount, txtProductID;

        public FridgeProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductID = itemView.findViewById(R.id.txt_product_id);
            txtName = itemView.findViewById(R.id.txt_product_name);
            txtAmount = itemView.findViewById(R.id.txt_product_amount);
        }
    }
}
