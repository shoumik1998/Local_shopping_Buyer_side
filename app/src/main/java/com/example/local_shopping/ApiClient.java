package com.example.local_shopping;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Headers;

public class ApiClient {
    public  static  final  String BASE_URL="http://192.168.43.17:80/loginapp/";
    public  static Retrofit retrofit=null;

    public  static  Retrofit getRetrofit(){
        if (retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit;
    }
}
