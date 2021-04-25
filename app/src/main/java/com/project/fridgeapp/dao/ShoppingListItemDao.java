package com.project.fridgeapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.project.fridgeapp.entities.ShoppingListItem;

import java.util.List;

@Dao
public interface ShoppingListItemDao {

    @Insert
    long insert(ShoppingListItem shoppingListItem);

    @Delete
    void delete(ShoppingListItem shoppingListItem);

    //Delete all
    @Delete
    void deleteAll(List<ShoppingListItem> shoppingListItems);

    //Update query
    @Query("UPDATE shopping_list_item " +
            "SET " +
            "shopping_list_item_name = :sShoppingListItemName, " +
            "shopping_list_item_amount = :sShoppingListItemAmount, " +
            "shopping_list_item_shop_name = :sShoppingListItemShopName " +
            "WHERE shoppingListItemID = :sShoppingListItemID")
    void update(long sShoppingListItemID, String sShoppingListItemName,
                int sShoppingListItemAmount, String sShoppingListItemShopName);

    //Get all data query
    @Query("SELECT * FROM shopping_list_item")
    List<ShoppingListItem> getAllShoppingListItems();

    @Query("SELECT * FROM shopping_list_item ORDER BY shopping_list_item_name")
    List<ShoppingListItem> getAllShoppingListByName();

    @Query("SELECT * FROM shopping_list_item ORDER BY shopping_list_item_shop_name")
    List<ShoppingListItem> getAllShoppingListByShopName();
}
