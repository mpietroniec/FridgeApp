package com.project.fridgeapp.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shopping_list_item")
public class ShoppingListItem implements Parcelable{
    @PrimaryKey(autoGenerate = true)
    private long shoppingListItemID;
    @ColumnInfo(name = "shopping_list_item_name")
    private String shoppingListItemName;
    @ColumnInfo(name = "shopping_list_item_amount")
    private int shoppingListItemAmount;
    @ColumnInfo(name = "shopping_list_item_shop_name")
    private String shoppingListItemShopName;

    public ShoppingListItem() {
    }

    protected ShoppingListItem(Parcel in) {
        shoppingListItemID = in.readLong();
        shoppingListItemName = in.readString();
        shoppingListItemAmount = in.readInt();
        shoppingListItemShopName = in.readString();
    }

    public static final Creator<ShoppingListItem> CREATOR = new Creator<ShoppingListItem>() {
        @Override
        public ShoppingListItem createFromParcel(Parcel in) {
            return new ShoppingListItem(in);
        }

        @Override
        public ShoppingListItem[] newArray(int size) {
            return new ShoppingListItem[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(shoppingListItemID);
        parcel.writeString(shoppingListItemName);
        parcel.writeInt(shoppingListItemAmount);
        parcel.writeString(shoppingListItemShopName);
    }
}
