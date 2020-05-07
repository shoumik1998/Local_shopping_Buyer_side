package com.example.local_shopping;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Product_Name_Adapter extends ArrayAdapter {
    private  Context context;
    private List<Fetching_produtc_images> product_name_list;


    public Product_Name_Adapter(@NonNull Context context, int resource, List<Fetching_produtc_images> product_name_list) {
        super(context, resource, product_name_list);
        this.context=context;
        this.product_name_list=product_name_list;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        convertView=inflater.inflate(R.layout.product_name_layout,parent,false);

        TextView textView=convertView.findViewById(R.id.pro_name_text_ID);
        textView.setText(product_name_list.get(position).getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getInstance().from_product_search_act=true;
                MainActivity.getInstance().parsing_pro_name=product_name_list.get(position).getName();
                context.startActivity(new Intent(context,MainActivity.class));

            }
        });


        return convertView;
    }
}
