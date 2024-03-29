package com.example.local_shopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.app.SearchManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search_shop_by_Name extends AppCompatActivity {
    private ListView shop_list_view;
    private TextView search_shop_text;
    private List<Locations> shop_list;
    private Toolbar toolbar;
    private  ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_shop_by__name);

        shop_list_view=findViewById(R.id.shop_List_ID);
        search_shop_text=findViewById(R.id.shop_search_textID);
        toolbar=findViewById(R.id.searched_shop_toolbarID_location);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.shop_search_menu,menu);

        final SearchView searchView= (SearchView) menu.findItem(R.id.shop_search_menuDI).getActionView();
        SearchManager manager= (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("search shop by name");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    HashMap<String, Object> map = new HashMap<>();
                    apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
                    MainActivity mainActivity=MainActivity.getInstance();
                    map.put("country", mainActivity.read_country());
                    map.put("district", mainActivity.read_district());
                    map.put("subdistrict", mainActivity.read_subdistrict());
                    map.put("region", mainActivity.read_region());
                    map.put("shop_name", newText);
                    Call<List<Locations>> call=apiInterface.get_Shop_Name(map);

                    call.enqueue(new Callback<List<Locations>>() {
                        @Override
                        public void onResponse(Call<List<Locations>> call, Response<List<Locations>> response) {
                            if (response.isSuccessful()){
                                shop_list=response.body();
                                Shop_search_Adapter adapter=new Shop_search_Adapter(Search_shop_by_Name.this,R.layout.shop_search_layout,shop_list);
                                shop_list_view.setVisibility(View.VISIBLE);
                                search_shop_text.setVisibility(View.GONE);
                                shop_list_view.setAdapter(adapter);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<Locations>> call, Throwable t) {

                        }
                    });
                }else {
                    shop_list_view.setVisibility(View.GONE);
                }
                return false;
            }
        });





        return super.onCreateOptionsMenu(menu);
    }
}
