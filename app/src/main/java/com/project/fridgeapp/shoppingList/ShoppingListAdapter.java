package com.project.fridgeapp.shoppingList;

import android.content.Context;
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
import com.project.fridgeapp.database.DatabaseHelper;
import com.project.fridgeapp.entities.ShoppingListItem;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListItemsViewHolder> {

    private List<ShoppingListItem> shoppingListItemsList;
    private Context context;
    private DatabaseHelper database;
    private ItemClickListener mItemClickListener;

    public ShoppingListAdapter(List<ShoppingListItem> shoppingListItemsList, Context context, ItemClickListener itemClickListener) {
        this.shoppingListItemsList = shoppingListItemsList;
        this.context = context;
        this.mItemClickListener = itemClickListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShoppingListItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_shopping_list_row, parent, false);
        return new ShoppingListItemsViewHolder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListItemsViewHolder holder, int position) {
        ShoppingListItem shoppingListItem = shoppingListItemsList.get(position);
        //Initialize database
        database = DatabaseHelper.getInstance(context);
        holder.txtName.setText(shoppingListItem.getShoppingListItemName());
        holder.txtAmount.setText(String.valueOf(shoppingListItem.getShoppingListItemAmount()));
        holder.txtShopName.setText(shoppingListItem.getShoppingListItemShopName());
        holder.cardViewShoppingList.setOnClickListener(view -> {
            if(holder.expandableLinearLayout.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(holder.cardViewShoppingList, new AutoTransition());
                holder.expandableLinearLayout.setVisibility(View.VISIBLE);
            } else {
                TransitionManager.beginDelayedTransition(holder.cardViewShoppingList, new AutoTransition());
                holder.expandableLinearLayout.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shoppingListItemsList.size();
    }

    public class ShoppingListItemsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtName, txtAmount, txtShopName;
        LinearLayout expandableLinearLayout;
        CardView cardViewShoppingList;
        ItemClickListener itemClickListener;

        public ShoppingListItemsViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_shopping_list_name);
            txtAmount = itemView.findViewById(R.id.txt_shopping_list_amount);
            txtShopName = itemView.findViewById(R.id.txt_shopping_list_shop_name);
            expandableLinearLayout = itemView.findViewById(R.id.ll_shopping_list_item_edit);
            cardViewShoppingList = itemView.findViewById(R.id.cv_shopping_list_item);
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClickListener(getAdapterPosition());
        }
    }
}
