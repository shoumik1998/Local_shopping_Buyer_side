package com.example.local_shopping;

import com.google.gson.annotations.SerializedName;

public class Locations {
    @SerializedName("country")
    String Country;
    @SerializedName("district")
    String District;
    @SerializedName("subdistrict")
    String SubDistrict;
    @SerializedName("region")
    String Region;
    @SerializedName("Location")
    String Location_xt;
    @SerializedName("name")
    String Shop_name;
    @SerializedName("user_name")
    String User_name;
    String currency;
    String cell_number;


    public String getCountry() {
        return Country;
    }

    public String getDistrict() {
        return District;
    }

    public String getSubDistrict() {
        return SubDistrict;
    }

    public String getRegion() {
        return Region;
    }

    public String getLocation_xt() {
        return Location_xt;
    }

    public String getShop_name() {
        return Shop_name;
    }

    public String getUser_name() {
        return User_name;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCell_number() {
        return cell_number;
    }


    public void setCountry(String country) {
        Country = country;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public void setSubDistrict(String subDistrict) {
        SubDistrict = subDistrict;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public void setLocation_xt(String location_xt) {
        Location_xt = location_xt;
    }

    public void setShop_name(String shop_name) {
        Shop_name = shop_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCell_number(String cell_number) {
        this.cell_number = cell_number;
    }
}
