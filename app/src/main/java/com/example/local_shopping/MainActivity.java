package com.example.local_shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    Context context;
    private Toolbar toolbar;
    private MaterialSearchView materialSearchView;
    public   String Country,District,Subdistrict,Region;
    private SharedPreferences sharedPreferences;
    public  static  MainActivity mainActivity;
    public boolean from_search_act=false;
    private  String[] array=new String[]{"a","b","c","d"};
    public  View view;





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


        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
                Call<List<Fetching_produtc_images>> call=apiInterface.getProductName(newText);
                call.enqueue(new Callback<List<Fetching_produtc_images>>() {
                    @Override
                    public void onResponse(Call<List<Fetching_produtc_images>> call, Response<List<Fetching_produtc_images>> response) {
                        if (response.isSuccessful()){
                            materialSearchView.setEllipsize(true);
                            try {

                            }catch (Exception e){
                                Toast.makeText(MainActivity.this, "  "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            Toast.makeText(MainActivity.this, "Hmm Response "+response.body().toString(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, " Response Failed", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Fetching_produtc_images>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Failed To connect", Toast.LENGTH_SHORT).show();



                    }
                });

                return false;
            }
        });



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
                        Toast.makeText(MainActivity.this, "No response is found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Fetching_produtc_images>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Failed  to connect"+t.toString(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MainActivity.this, "No Response is found", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<Fetching_produtc_images>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Failed to connect"+t.toString(), Toast.LENGTH_SHORT).show();

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
                    }
                }

                @Override
                public void onFailure(Call<List<Fetching_produtc_images>> call, Throwable t) {
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
                    }


                }

                @Override
                public void onFailure(Call<List<Fetching_produtc_images>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Failed to fetch data  " + t.toString(), Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem item=menu.findItem(R.id.searchID);
        materialSearchView.setMenuItem(item);
        /*final SearchView searchView=(SearchView) menu.findItem(R.id.searchID).getActionView();
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
        });*/
        return  true;



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nextActivityID:
                startActivity(new Intent(MainActivity.this,Location_finder.class));
            case R.id.searchID:
                return  true;
                default:
         return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (from_search_act==true){
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

    @Override
    public void onBackPressed() {
        if (materialSearchView.isSearchOpen()){
            materialSearchView.closeSearch();
        }else {

            super.onBackPressed();
        }
    }

    public  void  save_and_sharing_task(){
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        view=getLayoutInflater().inflate(R.layout.share_and_save_dialouge,null,false);
        Button sharebtn=view.findViewById(R.id.shareID);
        Button savebtn=view.findViewById(R.id.saveID);
        builder.setView(view);
        final AlertDialog dialog=builder.create();
        dialog.show();
        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "share  btn is clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "save btn is clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();


            }
        });
        dialog.setCancelable(true);


    }
}
