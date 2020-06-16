package com.example.local_shopping;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

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
    @ColumnInfo(name = "saved_pro_currency")
    public  String sCurrency;
    @ColumnInfo(name = "saved_shop_location")
    public  String saved_shop_location;

    public Saved_Product_Model(String saved_pro_name, String saved_pro_shop_name, String saved_pro_region, String saved_pro_price, String sImageUri,String sCurrency,String saved_shop_location) {
        this.saved_pro_name = saved_pro_name;
        this.saved_pro_shop_name = saved_pro_shop_name;
        this.saved_pro_region = saved_pro_region;
        this.saved_pro_price = saved_pro_price;
        this.sImageUri = sImageUri;
        this.sCurrency=sCurrency;
        this.saved_shop_location=saved_shop_location;
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

    public String getsCurrency() {
        return sCurrency;
    }

    public String getSaved_shop_location() {
        return saved_shop_location;
    }
}
