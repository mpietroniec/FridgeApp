package com.project.fridgeapp.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shopping_list_item")
public class ShoppingListItem {
    @PrimaryKey(autoGenerate = true)
    private long shoppingListItemID;
    @ColumnInfo(name = "shopping_list_item_name")
    private String shoppingListItemName;
    @ColumnInfo(name = "shopping_list_item_amount")
    private int shoppingListItemAmount;
    @ColumnInfo(name = "shopping_list_item_shop_name")
    private String shoppingListItemShopName;

    public long getShoppingListItemID() {
        return shoppingListItemID;
    }

    public void setShoppingListItemID(long shoppingListItemID) {
        this.shoppingListItemID = shoppingListItemID;
    }

    public String getShoppingListItemName() {
        return shoppingListItemName;
    }

    public void setShoppingListItemName(String shoppingListItemName) {
        this.shoppingListItemName = shoppingListItemName;
    }

    public int getShoppingListItemAmount() {
        return shoppingListItemAmount;
    }

    public void setShoppingListItemAmount(int shoppingListItemAmount) {
        this.shoppingListItemAmount = shoppingListItemAmount;
    }

    public String getShoppingListItemShopName() {
        return shoppingListItemShopName;
    }

    public void setShoppingListItemShopName(String shoppingListItemShopName) {
        this.shoppingListItemShopName = shoppingListItemShopName;
    }
}
