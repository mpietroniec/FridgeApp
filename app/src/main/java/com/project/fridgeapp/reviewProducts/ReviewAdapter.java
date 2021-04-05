package com.project.fridgeapp.reviewProducts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.project.fridgeapp.ItemClickListener;
import com.project.fridgeapp.R;
import com.project.fridgeapp.helpers.DateParser;
import com.project.fridgeapp.database.DatabaseHelper;
import com.project.fridgeapp.entities.FridgeProduct;
import com.project.fridgeapp.update.UpdateFridgeProductActivity;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.FridgeProductsViewHolder> {

    private List<FridgeProduct> fridgeProductsList;
    private Context context;
    private DatabaseHelper database;
    private ItemClickListener mItemClickListener;

    public ReviewAdapter(List<FridgeProduct> fridgeProductsList, Context context, ItemClickListener itemClickListener) {
        this.fridgeProductsList = fridgeProductsList;
        this.context = context;
        this.mItemClickListener = itemClickListener;
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
        return new FridgeProductsViewHolder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FridgeProductsViewHolder holder, int position) {
        FridgeProduct fridgeProduct = fridgeProductsList.get(position);
        //Initialize database
        database = DatabaseHelper.getInstance(context);
        holder.txtName.setText(fridgeProduct.getFridgeProductName());
        holder.txtAmount.setText(String.valueOf(fridgeProduct.getFridgeProductAmount()));
        holder.txtExpirationDate.setText(DateParser.dateToStringParser(fridgeProduct.getFridgeProductExpirationDate()));
        holder.cardViewReview.setOnClickListener(view -> {
            if (holder.expandableLinearLayout.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(holder.cardViewReview, new AutoTransition());
                holder.expandableLinearLayout.setVisibility(View.VISIBLE);
            } else {
                TransitionManager.beginDelayedTransition(holder.cardViewReview, new AutoTransition());
                holder.expandableLinearLayout.setVisibility(View.GONE);
            }
        });

        holder.txtBtnEditFridgeProduct.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateFridgeProductActivity.class);
            intent.putExtra("Fridge Product", fridgeProductsList.get(position));
            context.startActivity(intent);
        });

        holder.txtBtnDeleteFridgeProduct.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
            builder.setTitle("Delete?")
                    .setMessage("Are you sure you want to delete?")
                    .setPositiveButton("yes", (dialog, which) -> {
                        FridgeProduct item = fridgeProductsList.get(holder.getAdapterPosition());
                        database.fridgeProductDao().delete(item);
                        int pos = holder.getAdapterPosition();
                        fridgeProductsList.remove(pos);
                        notifyItemRemoved(pos);
                        notifyItemRangeChanged(pos, fridgeProductsList.size());
                    }).setNegativeButton("no", (dialog, which) -> {
                dialog.cancel();
            });
            builder.show();
        });
    }

    @Override
    public int getItemCount() {
        return fridgeProductsList.size();
    }

    public class FridgeProductsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtName, txtAmount, txtBtnEditFridgeProduct, txtBtnDeleteFridgeProduct, txtExpirationDate;
        LinearLayout expandableLinearLayout;
        CardView cardViewReview;
        ItemClickListener itemClickListener;

        public FridgeProductsViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_product_name);
            txtAmount = itemView.findViewById(R.id.txt_product_amount);
            txtExpirationDate = itemView.findViewById(R.id.txt_fridge_product_expiration_date);

            txtBtnEditFridgeProduct = itemView.findViewById(R.id.txt_btn_fridge_product_edit);
            txtBtnDeleteFridgeProduct = itemView.findViewById(R.id.txt_btn_fridge_product_delete);
            expandableLinearLayout = itemView.findViewById(R.id.ll_fridge_product_edit);
            cardViewReview = itemView.findViewById(R.id.cv_review);
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClickListener(getAdapterPosition());
        }
    }
}
