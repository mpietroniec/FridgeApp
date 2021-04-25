package com.project.fridgeapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.project.fridgeapp.entities.FridgeProduct;
import com.project.fridgeapp.helpers.DateTypeConverter;

import java.util.Date;
import java.util.List;

@Dao
public interface FridgeProductDao {

    @Insert
    long insert(FridgeProduct fridgeProduct);

    @Delete
    void delete(FridgeProduct fridgeProduct);

    //Delete all
    @Delete
    void deleteAll(List<FridgeProduct> fridgeProducts);

    //Update query
    @Query("UPDATE fridge_product " +
            "SET " +
            "table_fridge_product_name = :sProductName, " +
            "table_fridge_product_amount = :sAmount, " +
            "table_fridge_product_type = :sProductType, " +
            "table_fridge_product_expiration_date = :sExpirationDate " +
            "WHERE fridgeID = :sFridgeID")
    long update(long sFridgeID, String sProductName, int sAmount, String sProductType, @TypeConverters(DateTypeConverter.class) Date sExpirationDate);

    //Get all data query
    @Query("SELECT * FROM fridge_product")
    List<FridgeProduct> getAllFridgeProducts();

    @Query("SELECT * FROM fridge_product ORDER BY table_fridge_product_expiration_date")
    List<FridgeProduct> getAllFridgeProductsByExpirationDate();

    @Query("SELECT * FROM fridge_product WHERE table_fridge_product_type = 0 ")
    List<FridgeProduct> getAllGroceries();

    @Query("SELECT * FROM fridge_product WHERE table_fridge_product_type = 1 ")
    List<FridgeProduct> getAllDiapers();

    @Query("SELECT * FROM fridge_product WHERE table_fridge_product_type = 2 ")
    List<FridgeProduct> getAllIndustrialGoods();
}
