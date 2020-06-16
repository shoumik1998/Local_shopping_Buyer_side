package com.example.local_shopping;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities ={ Saved_Product_Model.class},version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public  abstract Saved_Pro_Dao saved_pro_dao();
}
