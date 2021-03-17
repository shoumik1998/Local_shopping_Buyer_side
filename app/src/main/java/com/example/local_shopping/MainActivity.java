package com.example.local_shopping;

import androidx.annotation.NonNull;
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

import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Fetching_produtc_images> images;
    private RecyclerAdapter adapter;
    private ApiInterface apiInterface;
    private ProgressBar progressBar;
    private int pastVisibleItems = 0, visibleItemCount = 0, totalItemCount, previous_total = 0, view_ThreshHold = 10;
    private boolean isLoading = true;
    private Toolbar toolbar;
    private SearchView searchView;
    private String query;
    public int end_point;
    private SharedPreferences sharedPreferences;
    public static MainActivity mainActivity;
    public boolean from_location_search_act = false;
    public boolean from_product_search_act = false;
    HashMap<String,Object> map=new HashMap<>();

    private FloatingActionButton F_Refresh, F_Saved, F_Shop_Search, F_Region_Search;
    private FloatingActionMenu FAM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerID);
        progressBar = findViewById(R.id.progressBarID);
        toolbar = findViewById(R.id.toolbarID);

        FAM = findViewById(R.id.main_famID);
        F_Refresh = findViewById(R.id.refresh_optionID);
        F_Saved = findViewById(R.id.savd_optionID);
        F_Shop_Search = findViewById(R.id.shop_nameoptionID);
        F_Region_Search = findViewById(R.id.shop_regionoptionID);

        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        sharedPreferences = getSharedPreferences("myRegionFile", Context.MODE_PRIVATE);
        mainActivity = this;

        F_Refresh.setOnClickListener(this);
        F_Saved.setOnClickListener(this);
        F_Shop_Search.setOnClickListener(this);
        F_Region_Search.setOnClickListener(this);


        if (!read_region_status()) {
            startActivity(new Intent(MainActivity.this, Location_finder.class));
            finish();
        }

        fetch_products_images();

        map.put("country",read_country());
        map.put("district", read_district());
        map.put("subdistrict", read_subdistrict());
        map.put("region", read_region());
        map.put("product_name", query);
    }


    public int read_end_point() {
        return end_point;
    }

    public void fetch_products_images() {
        progressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        if (from_product_search_act == true) {

            Call<List<Fetching_produtc_images>> call1 = apiInterface.fetch_pro_after_product_search(map);

            call1.enqueue(new Callback<List<Fetching_produtc_images>>() {
                @Override
                public void onResponse(Call<List<Fetching_produtc_images>> call, Response<List<Fetching_produtc_images>> response) {
                    if (response.isSuccessful()) {
                        images = response.body();
                        adapter = new RecyclerAdapter(images, MainActivity.this);
                        recyclerView.setAdapter(adapter);

                        progressBar.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(MainActivity.this, "No Response is found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Fetching_produtc_images>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Failed to connect" + t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (read_region_status()) {
            progressBar.setVisibility(View.VISIBLE);

            HashMap<String,Object> map=new HashMap<>();
            map.put("country", read_country());
            map.put("district", read_district());
            map.put("subdistrict", read_subdistrict());
            map.put("region", read_region());

            Call<List<Fetching_produtc_images>> call2 = apiInterface.fetch_pro_after_location_search(map);

            call2.enqueue(new Callback<List<Fetching_produtc_images>>() {
                @Override
                public void onResponse(Call<List<Fetching_produtc_images>> call, Response<List<Fetching_produtc_images>> response) {
                    if (response.isSuccessful()) {
                        images = response.body();
                        adapter = new RecyclerAdapter(images, MainActivity.this);
                        recyclerView.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<List<Fetching_produtc_images>> call, Throwable t) {

                }
            });

        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisibleItems = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if (dy > 0) {
                    if (isLoading) {
                        if (totalItemCount > previous_total) {
                            isLoading = false;
                            previous_total = totalItemCount;

                        }
                    }
                    if (!isLoading && (totalItemCount - visibleItemCount) <= (pastVisibleItems + view_ThreshHold)) {
                        isLoading = true;
                        progressBar.setVisibility(View.GONE);
                        pagenate();
                    }
                }
            }
        });
    }

    public void pagenate() {
        progressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        if (from_product_search_act == true) {
            Call<List<Fetching_produtc_images>> call1 = apiInterface.fetch_pro_after_product_search(map);

            call1.enqueue(new Callback<List<Fetching_produtc_images>>() {
                @Override
                public void onResponse(Call<List<Fetching_produtc_images>> call, Response<List<Fetching_produtc_images>> response) {
                    if (response.isSuccessful()) {

                        List<Fetching_produtc_images> new_images = response.body();
                        adapter.addImages(new_images);
                        if (read_end_point() == 20) {
                            recyclerView.setNestedScrollingEnabled(false);
                            Toast.makeText(MainActivity.this, "" + end_point, Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<List<Fetching_produtc_images>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Failed to fetch data  " + t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (read_region_status()) {
            progressBar.setVisibility(View.VISIBLE);

            HashMap<String,Object> map=new HashMap<>();
            map.put("country", read_country());
            map.put("district", read_district());
            map.put("subdistrict", read_subdistrict());
            map.put("region", read_region());

            Call<List<Fetching_produtc_images>> call2 = apiInterface.fetch_pro_after_location_search(map);

            call2.enqueue(new Callback<List<Fetching_produtc_images>>() {
                @Override
                public void onResponse(Call<List<Fetching_produtc_images>> call, Response<List<Fetching_produtc_images>> response) {
                    if (response.isSuccessful()) {
                        List<Fetching_produtc_images> new_images = response.body();
                        adapter.addImages(new_images);
                        progressBar.setVisibility(View.GONE);

                    }
                }

                @Override
                public void onFailure(Call<List<Fetching_produtc_images>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "failed to fetch images ", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.pro_finder_searchID));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search product ");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String Query) {
                query =Query;
                from_product_search_act = false;
                from_location_search_act=false;

                pastVisibleItems = 0;
                visibleItemCount = 0;
                previous_total = 0;
                progressBar.setVisibility(View.VISIBLE);

                apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
                Call<List<Fetching_produtc_images>> product_call = apiInterface.fetch_pro_after_product_search(map);
                product_call.enqueue(new Callback<List<Fetching_produtc_images>>() {
                    @Override
                    public void onResponse(Call<List<Fetching_produtc_images>> call, Response<List<Fetching_produtc_images>> response) {
                        if (response.isSuccessful()) {
                            pastVisibleItems = 0;
                            visibleItemCount = 0;
                            previous_total = 0;
                            images = response.body();
                            RecyclerAdapter adapter = new RecyclerAdapter(images, MainActivity.this);
                            recyclerView.setAdapter(adapter);
                            progressBar.setVisibility(View.GONE);
                            from_product_search_act = true;
                            fetch_products_images();


                        } else {
                            Toast.makeText(MainActivity.this, "No such product is found", Toast.LENGTH_SHORT).show();
                        }

                        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);
                                visibleItemCount = layoutManager.getChildCount();
                                totalItemCount = layoutManager.getItemCount();
                                pastVisibleItems = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                                if (dy > 0) {
                                    if (isLoading) {
                                        if (totalItemCount > previous_total) {
                                            isLoading = false;
                                            previous_total = totalItemCount;
                                        }
                                    }
                                    if (!isLoading && (totalItemCount - visibleItemCount) <= (pastVisibleItems + view_ThreshHold)) {
                                        isLoading = true;
                                        progressBar.setVisibility(View.GONE);
                                        pagenate();
                                    }
                                }
                            }

                            @Override
                            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                super.onScrollStateChanged(recyclerView, newState);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<List<Fetching_produtc_images>> call, Throwable t) {

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
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);
    }

    public void write_region_name(String Country, String District, String Subdistrict, String Region) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("country", Country)
                .putString("district", District)
                .putString("subdistrict", Subdistrict)
                .putString("region", Region);
        editor.commit();
    }

    public String read_country() {
        return sharedPreferences.getString("country", "Country Not Found");

    }

    public String read_district() {
        return sharedPreferences.getString("district", "District Not Found");
    }

    public String read_subdistrict() {
        return sharedPreferences.getString("subdistrict", "subdistrict Not Found");
    }

    public String read_region() {
        return sharedPreferences.getString("region", "region Not Found");
    }

    public void write_region_status(boolean status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("region_status", status);
        editor.commit();
    }

    public boolean read_region_status() {
        return sharedPreferences.getBoolean("region_status", false);
    }

    public static MainActivity getInstance() {
        return mainActivity;
    }

    public void onBackPressed() {
        if (from_product_search_act) {
            from_product_search_act = false;
            pastVisibleItems = 0;
            visibleItemCount = 0;
            previous_total = 0;
            fetch_products_images();
        } else {
            super.onBackPressed();
        }


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.refresh_optionID:
                finish();
                Animatoo.animateCard(this);
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                Animatoo.animateFade(this);
                FAM.close(true);
                break;
            case R.id.savd_optionID:
                startActivity(new Intent(MainActivity.this, Saved_Activity.class));
                FAM.close(true);
                break;
            case R.id.shop_nameoptionID:
                startActivity(new Intent(MainActivity.this, Search_shop_by_Name.class));
                FAM.close(true);
                break;
            case R.id.shop_regionoptionID:
                startActivity(new Intent(MainActivity.this, Location_finder.class));
                FAM.close(true);
                break;

        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
         if (from_location_search_act == true) {
            pastVisibleItems = 0;
            visibleItemCount = 0;
            previous_total = 0;
            fetch_products_images();
        }
    }

}
