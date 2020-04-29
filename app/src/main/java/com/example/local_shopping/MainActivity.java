package com.example.local_shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
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
    private  int pastVisibleItems,visibleItemCount,totalItemCount,previous_total=0,view_ThreshHold=10;
    private  boolean isLoading =true;
    Context context;
    private Toolbar toolbar;
    private MaterialSearchView materialSearchView;
    public   String Country,District,Subdistrict,Region;
    private SharedPreferences sharedPreferences;
    public  static  MainActivity mainActivity;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerID);
        progressBar=findViewById(R.id.progressBarID);
        toolbar=findViewById(R.id.toolbarID);
        materialSearchView=findViewById(R.id.materialSearchID);
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
        if (!read_region_status()){
            Call<List<Fetching_produtc_images>> call=apiInterface.fetching_images(1);
            call.enqueue(new Callback<List<Fetching_produtc_images>>() {
                @Override
                public void onResponse(Call<List<Fetching_produtc_images>> call, Response<List<Fetching_produtc_images>> response) {
                    if (response.isSuccessful()){
                        images=response.body();
                        adapter=new RecyclerAdapter(images,MainActivity.this);
                        recyclerView.setAdapter(adapter);
                    }else {
                        Toast.makeText(MainActivity.this, "No", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Fetching_produtc_images>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Failed"+t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Call<List<Fetching_produtc_images>> call1=apiInterface.fetch_pro_after_search(read_country(),read_district(),read_subdistrict(),read_region());
            call1.enqueue(new Callback<List<Fetching_produtc_images>>() {
                @Override
                public void onResponse(Call<List<Fetching_produtc_images>> call, Response<List<Fetching_produtc_images>> response) {
                    if (response.isSuccessful()){
                        images=response.body();
                        adapter=new RecyclerAdapter(images,MainActivity.this);
                        recyclerView.setAdapter(adapter);
                    }else {
                        Toast.makeText(MainActivity.this, "No", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<Fetching_produtc_images>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Failed"+t.toString(), Toast.LENGTH_SHORT).show();

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
                        pagenate();
                    }
                }


            }
        });
    }
    public  void  pagenate(){
        apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        if (!read_region_status()){
            Call<List<Fetching_produtc_images>> call=apiInterface.fetching_images(1);
            call.enqueue(new Callback<List<Fetching_produtc_images>>() {
                @Override
                public void onResponse(Call<List<Fetching_produtc_images>> call, Response<List<Fetching_produtc_images>> response) {
                    if (response.isSuccessful()){

                        List<Fetching_produtc_images> new_images=response.body();
                        adapter.addImages(new_images);
                    }else {
                        Toast.makeText(MainActivity.this, "No", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Fetching_produtc_images>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Failed"+t.toString(), Toast.LENGTH_SHORT).show();

                }
            });
        }else {
            Call<List<Fetching_produtc_images>> call1 = apiInterface.fetch_pro_after_search(read_country(),read_district(),read_subdistrict(),read_region());
            call1.enqueue(new Callback<List<Fetching_produtc_images>>() {
                @Override
                public void onResponse(Call<List<Fetching_produtc_images>> call, Response<List<Fetching_produtc_images>> response) {
                    if (response.isSuccessful()) {

                        List<Fetching_produtc_images> new_images = response.body();
                        adapter.addImages(new_images);
                    } else {
                        Toast.makeText(MainActivity.this, "No", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onFailure(Call<List<Fetching_produtc_images>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Failed" + t.toString(), Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        final SearchView searchView=(SearchView) menu.findItem(R.id.searchID).getActionView();
        SearchManager searchManager=(SearchManager)getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("search product by name");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
                    Call<List<Locations>> call=apiInterface.getLocations(newText);
                    call.enqueue(new Callback<List<Locations>>() {
                        @Override
                        public void onResponse(Call<List<Locations>> call, Response<List<Locations>> response) {
                            if (response.isSuccessful()) {
                                if (!TextUtils.isEmpty(response.body().toString())) {




                                }else {
                                    Toast.makeText(MainActivity.this, "Not Exists", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(MainActivity.this, "Hoi ni", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<List<Locations>> call, Throwable t) {

                        }
                    });

                }else {
                }
                return false;




            }
        });
        return  true;



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.nextActivityID){
            startActivity(new Intent(MainActivity.this,Location_finder.class));

        }
        return false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Country=read_country();
        District=read_district();
        Subdistrict=read_subdistrict();
        Region=read_region();


        fetch_products_images();
        if (Country!=null){
            Toast.makeText(this, "Data Ase" +Country, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Data Nai......" +Country, Toast.LENGTH_SHORT).show();
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
