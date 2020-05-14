package com.example.local_shopping;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Saved_Pro_Dao {
    @Query("select * from  mysavedproducts")
    List<Saved_Product_Model> get_saved_pro_list();
    @Insert
    void  insert_product(Saved_Product_Model model);
    @Update
    void  update_product(Saved_Product_Model model);
    @Delete
    void  delete_product(Saved_Product_Model model);

}
