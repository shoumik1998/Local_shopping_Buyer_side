package com.example.local_shopping;

import com.google.gson.annotations.SerializedName;

public class Fetching_produtc_images {

    @SerializedName("user_name")
    String User_Name;
    @SerializedName("shop_name")
    String Shop_Name;
    @SerializedName("name")
    String Name;
    @SerializedName("image_path")
    String Image_path;
    @SerializedName("price")
    String Price;
    @SerializedName("id")
    int Id;
    @SerializedName("response")
    String Response;
    @SerializedName("currency")
    String Crrency;
    @SerializedName("location")
    String Location;


    public  String getUser_Name(){
        return User_Name;
    }
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

    public int getId() {
        return Id;
    }

    public String getCrrency() {
        return Crrency;
    }

    public String getResponse() {
        return Response;
    }

    public String getLocation() {
        return Location;
    }
}
