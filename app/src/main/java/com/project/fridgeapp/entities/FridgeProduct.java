package com.project.fridgeapp.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.project.fridgeapp.helpers.DateTypeConverter;

import java.util.Date;

@Entity(tableName = "fridge_product")
public class FridgeProduct implements Parcelable {
    //ID column
    @PrimaryKey(autoGenerate = true)
    private long fridgeID;
    @ColumnInfo(name = "table_fridge_product_name")
    private String fridgeProductName;
    @ColumnInfo(name = "table_fridge_product_amount")
    private int fridgeProductAmount;
    @ColumnInfo(name = "table_fridge_product_expiration_date")
    @TypeConverters({DateTypeConverter.class})
    private Date fridgeProductExpirationDate;

    public FridgeProduct() {
    }

    protected FridgeProduct(Parcel in) {
        fridgeID = in.readLong();
        fridgeProductName = in.readString();
        fridgeProductAmount = in.readInt();
        fridgeProductExpirationDate = new Date(in.readLong());
    }

    public static final Creator<FridgeProduct> CREATOR = new Creator<FridgeProduct>() {
        @Override
        public FridgeProduct createFromParcel(Parcel in) {
            return new FridgeProduct(in);
        }

        @Override
        public FridgeProduct[] newArray(int size) {
            return new FridgeProduct[size];
        }
    };

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

    public Date getFridgeProductExpirationDate() {
        return fridgeProductExpirationDate;
    }

    public void setFridgeProductExpirationDate(Date fridgeProductExpirationDate) {
        this.fridgeProductExpirationDate = fridgeProductExpirationDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(fridgeID);
        parcel.writeString(fridgeProductName);
        parcel.writeInt(fridgeProductAmount);
        if (fridgeProductExpirationDate != null) {
            parcel.writeLong(fridgeProductExpirationDate.getTime());
        }
    }
}
