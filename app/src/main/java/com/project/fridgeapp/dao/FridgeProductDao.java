package com.project.fridgeapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.project.fridgeapp.entities.FridgeProduct;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface FridgeProductDao {

    @Insert(onConflict = REPLACE)
    void insert(FridgeProduct fridgeProduct);

    @Delete
    void delete(FridgeProduct fridgeProduct);

    //Delete all
    @Delete
    void deleteAll(List<FridgeProduct> fridgeProducts);

    //Update query
    @Query("UPDATE fridge_product " +
            "SET " +
            "table_fridge_product_name = :sProductName, " +
            "table_fridge_product_amount = :sAmount " +
            "WHERE fridgeID = :sFridgeID")
    void update(long sFridgeID, String sProductName, int sAmount);

    //Get all data query
    @Query("SELECT * FROM fridge_product")
    List<FridgeProduct> getAllFridgeProducts();
}
