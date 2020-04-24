package com.example.local_shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    private ListView listView;

    private  int pastVisibleItems,visibleItemCount,totalItemCount,previous_total=0,view_ThreshHold=10;
    private  boolean isLoading =true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerID);
        progressBar=findViewById(R.id.progressBarID);
        listView=findViewById(R.id.listID);

        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        fetch_products_images();

    }


    public  void  fetch_products_images(){
        apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<Fetching_produtc_images>> call=apiInterface.fetching_images(1);
        call.enqueue(new Callback<List<Fetching_produtc_images>>() {
            @Override
            public void onResponse(Call<List<Fetching_produtc_images>> call, Response<List<Fetching_produtc_images>> response) {
                if (response.isSuccessful()){
                    images=response.body();
                    adapter=new RecyclerAdapter(images,MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    Toast.makeText(MainActivity.this, "Hmm OK", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "No", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Fetching_produtc_images>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed"+t.toString(), Toast.LENGTH_SHORT).show();

            }
        });

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
                        pagenate();
                    }
                }


            }
        });
    }
    public  void  pagenate(){
        progressBar.setVisibility(View.VISIBLE);

        apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<Fetching_produtc_images>> call=apiInterface.fetching_images(1);
        call.enqueue(new Callback<List<Fetching_produtc_images>>() {
            @Override
            public void onResponse(Call<List<Fetching_produtc_images>> call, Response<List<Fetching_produtc_images>> response) {
                if (response.isSuccessful()){

                    List<Fetching_produtc_images> new_images=response.body();
                    adapter.addImages(new_images);
                    Toast.makeText(MainActivity.this, "Hmm OK", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        final SearchView searchView=(SearchView) menu.findItem(R.id.searchID).getActionView();
        SearchManager searchManager=(SearchManager)getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
                Call<List<Locations>> call=apiInterface.getLocations(query);
                call.enqueue(new Callback<List<Locations>>() {
                    @Override
                    public void onResponse(Call<List<Locations>> call, Response<List<Locations>> response) {
                        if (response.isSuccessful()) {
                            List<Locations> locationsList = response.body();
                            Location_Adapter location_adapter = new Location_Adapter(getApplicationContext(), locationsList);
                            listView.setAdapter(location_adapter);
                            notifyAll();
                            Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, "Hoi ni", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Locations>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Failed : "+t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return  true;



    }
}
