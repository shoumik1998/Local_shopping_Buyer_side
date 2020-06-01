package com.example.local_shopping;

import com.google.gson.annotations.SerializedName;

public class Visite_shop_Model {

    @SerializedName("name")
    String Name;
    @SerializedName("image_path")
    String Image_Path;
    @SerializedName("price")
    String Price;
    @SerializedName("response")
    String Response;

    public String getName() {
        return Name;
    }

    public String getImage_Path() {
        return Image_Path;
    }

    public String getPrice() {
        return Price;
    }

    public String getResponse() {
        return Response;
    }
}
