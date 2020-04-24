package com.example.local_shopping;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("user_data_fetching.php")
    Call<List<Fetching_produtc_images>> fetching_images(@Query("page_num") int page_number);

    @GET("location_fetching.php")
    Call<List<Locations>> getLocations(@Query("region") String region);
}
