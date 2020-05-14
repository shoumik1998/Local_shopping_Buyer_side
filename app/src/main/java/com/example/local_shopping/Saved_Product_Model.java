package com.example.local_shopping;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mysavedproducts")
public class Saved_Product_Model {

    @PrimaryKey(autoGenerate = true)
    public   int id;
    @ColumnInfo(name = "saved_pro_name")
    public   String saved_pro_name;
    @ColumnInfo(name = "saved_pro_shop_name")
    public   String saved_pro_shop_name;
    @ColumnInfo(name = "saved_pro_region")
    public   String saved_pro_region;
    @ColumnInfo(name = "saved_pro_price")
    public   String  saved_pro_price;
    @ColumnInfo(name = "sImageUri")
    public   String sImageUri;

    public Saved_Product_Model(String saved_pro_name, String saved_pro_shop_name, String saved_pro_region, String saved_pro_price, String sImageUri) {
        this.saved_pro_name = saved_pro_name;
        this.saved_pro_shop_name = saved_pro_shop_name;
        this.saved_pro_region = saved_pro_region;
        this.saved_pro_price = saved_pro_price;
        this.sImageUri = sImageUri;
    }

    public String getSaved_pro_name() {
        return saved_pro_name;
    }



    public String getSaved_pro_shop_name() {
        return saved_pro_shop_name;
    }



    public String getSaved_pro_region() {
        return saved_pro_region;
    }



    public String getSaved_pro_price() {
        return saved_pro_price;
    }



    public String getsImageUri() {
        return sImageUri;
    }

}
