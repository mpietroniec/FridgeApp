package com.project.fridgeapp.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fridge_product")
public class FridgeProduct {
    //ID column
    @PrimaryKey(autoGenerate = true)
    private long fridgeID;
    @ColumnInfo(name = "table_fridge_product_name")
    private String fridgeProductName;
    @ColumnInfo(name = "table_fridge_product_amount")
    private int fridgeProductAmount;

    public long getFridgeID() {
        return fridgeID;
    }

    public void setFridgeID(long fridgeID) {
        this.fridgeID = fridgeID;
    }

    public String getFridgeProductName() {
        return fridgeProductName;
    }

    public void setFridgeProductName(String fridgeProductName) {
        this.fridgeProductName = fridgeProductName;
    }

    public int getFridgeProductAmount() {
        return fridgeProductAmount;
    }

    public void setFridgeProductAmount(int fridgeProductAmount) {
        this.fridgeProductAmount = fridgeProductAmount;
    }
}
