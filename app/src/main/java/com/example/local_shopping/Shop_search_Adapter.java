package com.example.local_shopping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Shop_search_Adapter extends ArrayAdapter {
    private Context context ;
    private List<Locations> shop_list;

    public Shop_search_Adapter( Context context, int v,  List<Locations> shop_list) {
        super(context, v, shop_list);
        this.context=context;
        this.shop_list=shop_list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=LayoutInflater.from(context);

        final Locations shops=shop_list.get(position);


        convertView=inflater.inflate(R.layout.shop_search_layout,parent,false);

        final TextView shop_name=convertView.findViewById(R.id.searched_shop_nameID);
        final TextView shop_location=convertView.findViewById(R.id.searched_shop_locationID);

            shop_name.setText(shops.getShop_name());
            shop_location.setText(shops.getLocation_xt());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,Visit_Shop.class);
                    intent.putExtra("userName",shops.getUser_name());
                    intent.putExtra("shopName",shops.getShop_name());
                    context.startActivity(intent);
                    ((Activity)context).finish();

                }
            });


        return convertView;
    }
}
