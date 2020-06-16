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
    @SerializedName("location_xt")
    String Location_xt;

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
}
