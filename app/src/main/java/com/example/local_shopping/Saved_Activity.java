package com.example.local_shopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class Saved_Activity extends AppCompatActivity {
    private RecyclerView saved_pro_recyclerview;
    private  Saved_Product_Adapter product_adapter;
    private  RecyclerView.LayoutManager layoutManager;
    private List<Saved_Product_Model> pro_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_);

        saved_pro_recyclerview=findViewById(R.id.saved_act_recycler_ID);
        layoutManager=new LinearLayoutManager(this);
        saved_pro_recyclerview.setLayoutManager(layoutManager);
        saved_pro_recyclerview.setHasFixedSize(true);

        get_product_info();


    }

    private  void  get_product_info(){

        class Get_Product_Info extends AsyncTask<Void,Void,List<Saved_Product_Model>>{


            @Override
            protected List<Saved_Product_Model> doInBackground(Void... voids) {
                List<Saved_Product_Model> pro_model=Database_Client.getInstance(Saved_Activity.this)
                        .getAppDatabase()
                        .saved_pro_dao()
                        .get_saved_pro_list();

                return pro_model;
            }

            @Override
            protected void onPostExecute(List<Saved_Product_Model> saved_product_models) {
                super.onPostExecute(saved_product_models);

                Saved_Product_Adapter adapter=new Saved_Product_Adapter(Saved_Activity.this,saved_product_models);
                saved_pro_recyclerview.setAdapter(adapter);
                Toast.makeText(Saved_Activity.this, "hmmmm", Toast.LENGTH_SHORT).show();

            }
        }

        Get_Product_Info get_product_info=new Get_Product_Info();
        get_product_info.execute();

    }
}
