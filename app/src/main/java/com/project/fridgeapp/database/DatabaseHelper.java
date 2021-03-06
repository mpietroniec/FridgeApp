package com.project.fridgeapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.project.fridgeapp.dao.FridgeProductDao;
import com.project.fridgeapp.dao.ShoppingListItemDao;
import com.project.fridgeapp.entities.FridgeProduct;
import com.project.fridgeapp.entities.ShoppingListItem;

@Database(entities = {FridgeProduct.class, ShoppingListItem.class}, version = 5, exportSchema = false)
public abstract class DatabaseHelper extends RoomDatabase {
    //Database instance
    private static DatabaseHelper database;
    private static String DATABASE_NAME = "fridge_database";

    public synchronized static DatabaseHelper getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), DatabaseHelper.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
    public abstract FridgeProductDao fridgeProductDao();
    public abstract ShoppingListItemDao shoppingListItemDao();
}
