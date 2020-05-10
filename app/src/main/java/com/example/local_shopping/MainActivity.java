package com.example.local_shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private  RecyclerView.LayoutManager layoutManager;
    private List<Fetching_produtc_images> images;
    private  RecyclerAdapter adapter;
    private  ApiInterface apiInterface;
    private ProgressBar progressBar;
    private  int pastVisibleItems=0,visibleItemCount=0,totalItemCount,previous_total=0,view_ThreshHold=10;
    private  boolean isLoading =true;
    private Toolbar toolbar;


    private SharedPreferences sharedPreferences;
    public  static  MainActivity mainActivity;
    public boolean from_location_search_act=false;
    public  boolean from_product_search_act=false;
    public  String parsing_pro_name=null;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        recyclerView=findViewById(R.id.recyclerID);
        progressBar=findViewById(R.id.progressBarID);
        toolbar=findViewById(R.id.toolbarID);


        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        setSupportActionBar(toolbar);
        sharedPreferences =getSharedPreferences("myRegionFile",Context.MODE_PRIVATE);
        mainActivity=this;

        fetch_products_images();


    }


    public  void  fetch_products_images(){
        apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
         if (from_product_search_act==true){
            Call<List<Fetching_produtc_images>> call1=apiInterface.fetch_prp_after_product_search(read_country(),read_district(),read_subdistrict(),read_region(),parsing_pro_name);

            call1.enqueue(new Callback<List<Fetching_produtc_images>>() {
                @Override
                public void onResponse(Call<List<Fetching_produtc_images>> call, Response<List<Fetching_produtc_images>> response) {
                    if (response.isSuccessful()){
                        images=response.body();
                        adapter=new RecyclerAdapter(images,MainActivity.this);
                        recyclerView.setAdapter(adapter);

                    }else {
                        Toast.makeText(MainActivity.this, "No Response is found", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<Fetching_produtc_images>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Failed to connect"+t.toString(), Toast.LENGTH_SHORT).show();

                }
            });
        }
        else  if (read_region_status()){
            Call<List<Fetching_produtc_images>> call2=apiInterface.fetch_pro_after_location_search(read_country(),read_district(),read_subdistrict(),read_region());

            call2.enqueue(new Callback<List<Fetching_produtc_images>>() {
                @Override
                public void onResponse(Call<List<Fetching_produtc_images>> call, Response<List<Fetching_produtc_images>> response) {
                    if (response.isSuccessful()){
                        images=response.body();
                        adapter=new RecyclerAdapter(images,MainActivity.this);
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<List<Fetching_produtc_images>> call, Throwable t) {

                }
            });

        }
        else if (!read_region_status()){
            Call<List<Fetching_produtc_images>> call=apiInterface.fetching_images(1);
            call.enqueue(new Callback<List<Fetching_produtc_images>>() {
                @Override
                public void onResponse(Call<List<Fetching_produtc_images>> call, Response<List<Fetching_produtc_images>> response) {
                    if (response.isSuccessful()){
                        images=response.body();
                        adapter=new RecyclerAdapter(images,MainActivity.this);
                        recyclerView.setAdapter(adapter);

                    }else {
                        Toast.makeText(MainActivity.this, "No response is found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Fetching_produtc_images>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Failed  to connect"+t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount=layoutManager.getChildCount();
                totalItemCount=layoutManager.getItemCount();
                pastVisibleItems=((GridLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if (dy>0){
                    if (isLoading){
                        if (totalItemCount>previous_total){
                            isLoading=false;
                            previous_total=totalItemCount;
                        }
                    }
                    if (!isLoading && (totalItemCount-visibleItemCount)<=(pastVisibleItems+view_ThreshHold)){
                        isLoading=true;
                        progressBar.setVisibility(View.VISIBLE);
                        pagenate(null);
                    }
                }


            }
        });
    }
    public  void  pagenate(String query){
        apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
         if(from_product_search_act==true) {
            Call<List<Fetching_produtc_images>> call1=apiInterface.fetch_prp_after_product_search(read_country(),read_district(),read_subdistrict(),read_region(),query);

            call1.enqueue(new Callback<List<Fetching_produtc_images>>() {
                @Override
                public void onResponse(Call<List<Fetching_produtc_images>> call, Response<List<Fetching_produtc_images>> response) {
                    if (response.isSuccessful()) {

                        List<Fetching_produtc_images> new_images = response.body();
                        adapter.addImages(new_images);
                    }


                }

                @Override
                public void onFailure(Call<List<Fetching_produtc_images>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Failed to fetch data  " + t.toString(), Toast.LENGTH_SHORT).show();

                }
            });
        }else  if (read_region_status()){
            Call<List<Fetching_produtc_images>> call2 = apiInterface.fetch_pro_after_location_search(read_country(),read_district(),read_subdistrict(),read_region());

            call2.enqueue(new Callback<List<Fetching_produtc_images>>() {
                @Override
                public void onResponse(Call<List<Fetching_produtc_images>> call, Response<List<Fetching_produtc_images>> response) {
                    if (response.isSuccessful()){
                        List<Fetching_produtc_images> new_images=response.body();
                        adapter.addImages(new_images);
                    }
                }

                @Override
                public void onFailure(Call<List<Fetching_produtc_images>> call, Throwable t) {

                }
            });
        }
       else if (!read_region_status()){
            Call<List<Fetching_produtc_images>> call=apiInterface.fetching_images(1);
            call.enqueue(new Callback<List<Fetching_produtc_images>>() {
                @Override
                public void onResponse(Call<List<Fetching_produtc_images>> call, Response<List<Fetching_produtc_images>> response) {
                    if (response.isSuccessful()){

                        List<Fetching_produtc_images> new_images=response.body();
                        adapter.addImages(new_images);
                    }
                }

                @Override
                public void onFailure(Call<List<Fetching_produtc_images>> call, Throwable t) {
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.pro_finder_searchID));
        SearchManager searchManager=(SearchManager)getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search product by name");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                if (query.length()>0){
                    pastVisibleItems=0;
                    visibleItemCount=0;
                    previous_total=0;
                    progressBar.setVisibility(View.VISIBLE);
                    from_product_search_act=true;
                    Toast.makeText(MainActivity.this, "hmmmmmm", Toast.LENGTH_SHORT).show();
                    apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
                     Call<List<Fetching_produtc_images>> product_call=apiInterface.fetch_prp_after_product_search(read_country(),read_district(),read_subdistrict(),read_region(),query);
                    product_call.enqueue(new Callback<List<Fetching_produtc_images>>() {
                        @Override
                        public void onResponse(Call<List<Fetching_produtc_images>> call, Response<List<Fetching_produtc_images>> response) {
                            if (response.isSuccessful()){
                                images=response.body();
                                RecyclerAdapter adapter=new RecyclerAdapter(images,MainActivity.this);
                                recyclerView.setAdapter(adapter);
                                progressBar.setVisibility(View.GONE);

                            }else {
                                Toast.makeText(MainActivity.this, "No such product is found", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Fetching_produtc_images>> call, Throwable t) {

                        }
                    });
                }else {
                    from_product_search_act=false;
                    Toast.makeText(MainActivity.this, "type more than 3 letter then search", Toast.LENGTH_SHORT).show();
                }

                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        visibleItemCount=layoutManager.getChildCount();
                        totalItemCount=layoutManager.getItemCount();
                        pastVisibleItems=((GridLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                        if (dy>0){
                            if (isLoading){
                                if (totalItemCount>previous_total){
                                    isLoading=false;
                                    previous_total=totalItemCount;
                                }
                            }
                            if (!isLoading && (totalItemCount-visibleItemCount)<=(pastVisibleItems+view_ThreshHold)){
                                isLoading=true;
                                progressBar.setVisibility(View.VISIBLE);
                                pagenate(query);
                            }
                        }


                    }
                });


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.nextActivityID){
            startActivity(new Intent(MainActivity.this, Location_finder.class));
        }else if (item.getItemId()==R.id.searchProByNameID){
            startActivity(new Intent(MainActivity.this, Product_Finder.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (from_product_search_act==true){
            pastVisibleItems=0;
            visibleItemCount=0;
            previous_total=0;
            fetch_products_images();
        }
        else if (from_location_search_act==true){
            pastVisibleItems=0;
            visibleItemCount=0;
            previous_total=0;
            fetch_products_images();


        }

    }

    public  void write_region_name(String Country,String District,String Subdistrict,String Region){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("country",Country)
                .putString("district",District)
                .putString("subdistrict",Subdistrict)
                .putString("region",Region);
        editor.commit();
    }

    public String read_country(){
        return  sharedPreferences.getString("country", "Country Not Found");

    }
    public String  read_district(){
        return  sharedPreferences.getString("district","District Not Found");
    }
    public String read_subdistrict(){
return sharedPreferences.getString("subdistrict","subdistrict Not Found");
    }
    public  String read_region(){
        return  sharedPreferences.getString("region","region Not Found");
    }

    public  void  write_region_status(boolean status){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("region_status",status);
        editor.commit();
    }
    public  boolean read_region_status(){
        return sharedPreferences.getBoolean("region_status",false);
    }
    public  static  MainActivity getInstance(){
        return mainActivity;
    }



}
