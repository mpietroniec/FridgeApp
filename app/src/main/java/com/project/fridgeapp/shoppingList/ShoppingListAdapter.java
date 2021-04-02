package com.project.fridgeapp.shoppingList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.fridgeapp.R;
import com.project.fridgeapp.database.DatabaseHelper;
import com.project.fridgeapp.entities.ShoppingListItem;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListItemsViewHolder> {

    private List<ShoppingListItem> shoppingListItemsList;
    private Context context;
    private DatabaseHelper database;

    public ShoppingListAdapter(List<ShoppingListItem> shoppingListItemsList, Context context) {
        this.shoppingListItemsList = shoppingListItemsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ShoppingListItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_shopping_list_row, parent, false);
        return new ShoppingListItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListItemsViewHolder holder, int position) {
        ShoppingListItem shoppingListItem = shoppingListItemsList.get(position);
        //Initialize database
        database = DatabaseHelper.getInstance(context);
        holder.txtName.setText(shoppingListItem.getShoppingListItemName());
        holder.txtAmount.setText(String.valueOf(shoppingListItem.getShoppingListItemAmount()));
        holder.txtShopName.setText(shoppingListItem.getShoppingListItemShopName());
    }

    @Override
    public int getItemCount() {
        return shoppingListItemsList.size();
    }

    public class ShoppingListItemsViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtAmount, txtShopName;

        public ShoppingListItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_shopping_list_name);
            txtAmount = itemView.findViewById(R.id.txt_shopping_list_amount);
            txtShopName = itemView.findViewById(R.id.txt_shopping_list_shop_name);
        }
    }
}
