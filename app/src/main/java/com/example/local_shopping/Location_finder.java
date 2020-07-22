package com.example.local_shopping;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.SearchManager;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Location_finder extends AppCompatActivity {
    private ListView listView;
    private TextView search_description;
    private  ApiInterface apiInterface;
    private List<Locations> locationsList;
    private Toolbar toolbar;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_finder);

        listView=findViewById(R.id.locationList_ID);
        search_description=findViewById(R.id.region_finder_textID);
        toolbar=findViewById(R.id.toolbarID_location);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getting_permissions();

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
                                    search_description.setVisibility(View.GONE);
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

    public  void  getting_permissions(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }


        @Override
        public void onRequestPermissionsResult(int requestCode,
        String[] permissions, int[] grantResults) {
            switch (requestCode) {
                case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // permission was granted, yay! Do the
                        // contacts-related task you need to do.
                    } else {
                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.
                    }
                    return;
                }

                // other 'case' lines to check for other
                // permissions this app might request.
            }
        }

    }

