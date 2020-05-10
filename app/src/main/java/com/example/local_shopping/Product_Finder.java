package com.example.local_shopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.app.SearchManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;




import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product_Finder extends AppCompatActivity {
    private Toolbar pro_finder_toolbar;
    private ListView listView;
    private  ApiInterface apiInterface;
    private  List<Fetching_produtc_images> product_name_list;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__finder);
        
        listView=findViewById(R.id.proFinderList_ID);
        pro_finder_toolbar=findViewById(R.id.proFindertoolbarID);
        setSupportActionBar(pro_finder_toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pro_finder_menu,menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.pro_finder_searchID));
        SearchManager searchManager=(SearchManager)getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search product by name");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
                    Call<List<Fetching_produtc_images>> call=apiInterface.getProductName(newText);

                    call.enqueue(new Callback<List<Fetching_produtc_images>>() {
                        @Override
                        public void onResponse(Call<List<Fetching_produtc_images>> call, Response<List<Fetching_produtc_images>> response) {
                            if (response.isSuccessful()){
                                if (!TextUtils.isEmpty(response.body().toString())){
                                    product_name_list=response.body();
                                    Product_Name_Adapter adapter=new Product_Name_Adapter(Product_Finder.this,
                                            R.layout.product_name_layout,product_name_list);
                                    listView.setVisibility(View.VISIBLE);
                                    listView.setAdapter(adapter);

                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<List<Fetching_produtc_images>> call, Throwable t) {

                        }
                    });

                }else {
                    listView.setVisibility(View.GONE);
                }





                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
