package com.example.local_shopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Visit_Shop extends AppCompatActivity {

    private  RecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private  RecyclerView.LayoutManager layoutManager;
    private ProgressBar progressBar;
    private  TextView textView;
    private  List<Fetching_produtc_images> products;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit__shop);

        recyclerView=findViewById(R.id.shop_visit_recyclerVID);
        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
         textView=findViewById(R.id.visit_textID);
         textView.setText(getIntent().getStringExtra("shopName"));
        fetch_pro_after_visiting_shop();
    }

    public  void  fetch_pro_after_visiting_shop(){


        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<Fetching_produtc_images>> call=apiInterface.fetching_pro_visiting_shop(getIntent().getStringExtra("userName"));

        call.enqueue(new Callback<List<Fetching_produtc_images>>() {
            @Override
            public void onResponse(Call<List<Fetching_produtc_images>> call, Response<List<Fetching_produtc_images>> response) {
                if (response.isSuccessful()){
                    products = response.body();
               recyclerAdapter=new RecyclerAdapter(products,Visit_Shop.this);
               recyclerView.setAdapter(recyclerAdapter);

                }else {
                    Toast.makeText(Visit_Shop.this, "no response ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Fetching_produtc_images>> call, Throwable t) {
                Toast.makeText(Visit_Shop.this, "Failed..."+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}
