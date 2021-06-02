package com.example.local_shopping;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("user_data_fetching/:page_num")
    Call<List<Fetching_produtc_images>> fetching_images(@Field("page_num") int page_number);

    @POST("lf")
    Call<List<Locations>> getLocations(@Body HashMap<String,Object> map);

    @GET("fetching_product_name/:product_name")
    Call<List<Fetching_produtc_images>> getProductName(@Query("product_name") String productName);

    @POST("fetch_pro_after_location_search")
    Call<List<Fetching_produtc_images>> fetch_pro_after_location_search(@Body HashMap<String,Object> map);

    @POST("fetching_pro_by_name_specific_region")
    Call<List<Fetching_produtc_images>> fetch_pro_after_product_search(@Body HashMap<String,Object> map);

    @POST("data_fetching")
    Call<List<Fetching_produtc_images>> fetching_pro_visiting_shop(@Body HashMap<String,Object> map);

    @POST("shop_name_fetching")
    Call<List<Locations>> get_Shop_Name(@Body HashMap<String,Object> map);

    @POST("user_signup")
    Call<SignUp_Model> signUp(@Body HashMap<String,String> map);

    @POST("user_login")
    Call<SignUp_Model> logoIn(@Body HashMap<String,String> map);

    @POST("pusher")
    Call<String>  onOrder(@Body  HashMap< String,String> map);

    @POST("ordered_products")
    Call<List<Orders_Model>> ordered_products( @Body  HashMap<String,String> map);

    @POST("shop_details")
    Call<List<Locations>> onShop_Details(@Body HashMap<String,String> map);




}
