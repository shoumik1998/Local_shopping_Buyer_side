package com.example.local_shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Details_Activity extends AppCompatActivity {
    private ImageView imageView;
    private TextView Country_Text,District_Text,Sub_District_Text,Region_Text,Shop_Name_Text,Shop_Location_Text,
    Product_Name_text,Product_price_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_);

        imageView=findViewById(R.id.datils_image_id);
        Country_Text=findViewById(R.id.details_country_text_DI);
        District_Text=findViewById(R.id.details_district_text_DI);
        Sub_District_Text=findViewById(R.id.details_subdistrict_text_DI);
        Region_Text=findViewById(R.id.details_region_text_DI);
        Shop_Name_Text=findViewById(R.id.details_shopName_text_DI);
        Shop_Location_Text=findViewById(R.id.details_Shop_Location_text_DI);
        Product_Name_text=findViewById(R.id.details_product_Name_text_DI);
        Product_price_text=findViewById(R.id.details_product_price_text_DI);

        Glide.with(this).load(getIntent().getStringExtra("img_path")).into(imageView);
        Country_Text.setText(MainActivity.getInstance().read_country());
        District_Text.setText(MainActivity.getInstance().read_district());
        Sub_District_Text.setText(MainActivity.getInstance().read_subdistrict());
        Region_Text.setText(MainActivity.getInstance().read_region());
        Shop_Name_Text.setText(getIntent().getStringExtra("ShopName"));
        Product_Name_text.setText(getIntent().getStringExtra("Product_Name"));
        Product_price_text.setText(getIntent().getStringExtra("Price")+" "+getIntent().getStringExtra("currency"));



    }
}
