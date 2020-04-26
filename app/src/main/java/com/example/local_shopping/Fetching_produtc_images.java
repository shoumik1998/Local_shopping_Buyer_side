package com.example.local_shopping;

import com.google.gson.annotations.SerializedName;

public class Fetching_produtc_images {

    @SerializedName("shop_name")
    String Shop_Name;
    @SerializedName("name")
    String Name;
    @SerializedName("image_path")
    String Image_path;
    @SerializedName("price")
    String Price;

    public String getName() {
        return Name;
    }

    public String getImage_path() {
        return Image_path;
    }

    public String getPrice() {
        return Price;
    }

    public String getShop_Name() {
        return Shop_Name;
    }
}
