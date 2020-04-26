package com.example.local_shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Location_finder extends AppCompatActivity {
    private ListView listView;
    private  ApiInterface apiInterface;
    private List<Locations> locationsList;
    private Toolbar toolbar;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_finder);

        listView=findViewById(R.id.locationList_ID);
        toolbar=findViewById(R.id.toolbarID_location);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.location_search_menu,menu);
       final SearchView searchView= (SearchView) menu.findItem(R.id.location_searchID).getActionView();
        SearchManager searchManager=(SearchManager)getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("write shop location");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
                    Call<List<Locations>> call=apiInterface.getLocations(newText);
                    call.enqueue(new Callback<List<Locations>>() {
                        @Override
                        public void onResponse(Call<List<Locations>> call, Response<List<Locations>> response) {
                            if (response.isSuccessful()){
                                if (!TextUtils.isEmpty(response.body().toString())){
                                    locationsList=response.body();
                                    Location_Adapter location_adapter=new Location_Adapter(Location_finder.this,R.layout.location_layout,locationsList);
                                    listView.setVisibility(View.VISIBLE);
                                    listView.setAdapter(location_adapter);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Locations>> call, Throwable t) {

                        }
                    });
                }else {
                    listView.setVisibility(View.GONE);
                }
                return false;
            }
        });
        return  super.onCreateOptionsMenu(menu);
    }


}
