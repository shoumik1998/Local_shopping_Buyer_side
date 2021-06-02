package com.example.local_shopping;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ordered_Details_Activity extends AppCompatActivity {
    TextView p_nameTxt,p_priceTxt,p_numberTxt,totalTxt,c_nameTxt,c_contactTxt,addressTxt,issueTxt,deliveringTxt;
    Button shop_detailsBtn;
    ImageView imageView;
    ApiInterface apiInterface;
    int no_of_product,price;


    String p_nameStr, p_priceStr, p_numberStr, totalStr, c_nameStr, c_contactStr,
            c_addressStr, issue_dateStr, delivering_dateStr, s_user_nameStr, s_countryStr
            , s_districtStr, s_sub_districtStr, s_regionStr, s_locationStr, s_nameStr, s_contactStr, s_currencyStr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered__details_);

        imageView = findViewById(R.id.order_dialog_imageID);
        p_nameTxt = findViewById(R.id.order_dialog_p_nameID);
        p_priceTxt = findViewById(R.id.order_dialog_p_priceID);
        p_numberTxt = findViewById(R.id.order_dialog_p_numberID);
        totalTxt = findViewById(R.id.order_dialog_totalID);
        c_nameTxt = findViewById(R.id.order_dialog_c_nameID);
        c_contactTxt = findViewById(R.id.order_dialog_contactID);
        addressTxt = findViewById(R.id.order_dialog_addressID);
        issueTxt = findViewById(R.id.order_dialog_issue_dateID);
        deliveringTxt = findViewById(R.id.order_dialog_deliver_dateID);
        shop_detailsBtn = findViewById(R.id.order_dialog_shop_dateilsBtnID);


        p_nameStr = getIntent().getStringExtra("p_name");
        p_priceStr = getIntent().getStringExtra("p_price");
        p_numberStr = getIntent().getStringExtra("p_number");
        c_nameStr = getIntent().getStringExtra("p_name");
        c_contactStr = getIntent().getStringExtra("c_contact");
        c_addressStr = getIntent().getStringExtra("address");
        issue_dateStr = getIntent().getStringExtra("issue_date");
        delivering_dateStr = getIntent().getStringExtra("delivering_date");

        no_of_product = Integer.valueOf(p_numberStr);
        price = Integer.valueOf(p_priceStr);



        s_user_nameStr = getIntent().getStringExtra("user_name");
        HashMap<String, String> map = new HashMap<>();
        map.put("user_name", s_user_nameStr);

        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<Locations>> call = apiInterface.onShop_Details(map);

        call.enqueue(new Callback<List<Locations>>() {
            @Override
            public void onResponse(Call<List<Locations>> call, Response<List<Locations>> response) {
                if (response.isSuccessful()) {
                    s_countryStr = response.body().get(0).getCountry();
                    s_districtStr =response.body().get(0).getDistrict();
                    s_sub_districtStr = response.body().get(0).getSubDistrict();
                    s_regionStr = response.body().get(0).getRegion();
                    s_locationStr = response.body().get(0).getLocation_xt();
                    s_nameStr = response.body().get(0).getShop_name();
                    s_contactStr = response.body().get(0).getCell_number();
                    s_currencyStr = response.body().get(0).getCurrency();
                    p_priceTxt.setText(p_priceStr +" "+ s_currencyStr);
                    totalTxt.setText(String.valueOf(no_of_product*price +" "+ s_currencyStr));

                } else {
                    Toast.makeText(Ordered_Details_Activity.this, "invalid response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Locations>> call, Throwable t) {
                Toast.makeText(Ordered_Details_Activity.this, "not responding "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });




        p_nameTxt.setText(p_nameStr);
        p_numberTxt.setText(p_numberStr +" unit");
        c_nameTxt.setText(c_nameStr);
        c_contactTxt.setText(c_contactStr);
        addressTxt.setText(c_addressStr);
        issueTxt.setText(issue_dateStr);
        deliveringTxt.setText(delivering_dateStr);
        Glide.with(Ordered_Details_Activity.this).load(getIntent().getStringExtra("imagepath")).into(imageView);

        shop_detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView s_name,s_location,s_region,s_sub_district,s_district,s_country,s_contact;

                View view= LayoutInflater.from(Ordered_Details_Activity.this).inflate(R.layout.shop_details_dialog_layout,null);
                s_name = view.findViewById(R.id.shop_details_dialog_shop_nameTxtID);
                s_location = view.findViewById(R.id.shop_details_dialog_locationTxtID);
                s_region=view.findViewById(R.id.shop_details_dialog_regionTxtID);
                s_sub_district=view.findViewById(R.id.shop_details_dialog_subdistrictTxtID);
                s_district=view.findViewById(R.id.shop_details_dialog_districtTxtID);
                s_country=view.findViewById(R.id.shop_details_dialog_countryTxtID);
                s_contact = view.findViewById(R.id.shop_details_dialog_contactTxtID);

                s_name.setText(s_nameStr);
                s_location.setText(s_locationStr);
                s_region.setText(s_regionStr);
                s_sub_district.setText(s_sub_districtStr);
                s_district.setText(s_districtStr);
                s_country.setText(s_countryStr);
                s_contact.setText(s_contactStr);

                AlertDialog alertDialog=new AlertDialog.Builder(Ordered_Details_Activity.this)
                        .setView(view)
                        .setPositiveButton("",null)
                        .setNegativeButton("",null)
                        .show();
            }
        });
    }
}