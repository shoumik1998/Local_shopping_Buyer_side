package com.example.local_shopping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.List;



public class Location_Adapter extends ArrayAdapter {
    private Context context;
    private List<Locations> locations;

    public Location_Adapter(Context context, int v, List<Locations> locations) {
        super(context,v,locations);
        this.context=context;
        this.locations=locations;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       LayoutInflater inflater= LayoutInflater.from(context);
             convertView= inflater.inflate(R.layout.location_layout,parent,false);
       Locations location=locations.get(position);

        final TextView Country_text=convertView.findViewById(R.id.countryID);
        final TextView District_text=convertView.findViewById(R.id.districtID);
        final TextView SubDis_text=convertView.findViewById(R.id.subdistrictID);
        final TextView Region_text=convertView.findViewById(R.id.regionID);

        Country_text.setText(location.getCountry());
        District_text.setText(location.getDistrict());
        SubDis_text.setText(location.getSubDistrict());
        Region_text.setText(location.getRegion());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getInstance().write_region_status(true);
                MainActivity.getInstance().write_region_name(Country_text.getText().toString(),
                        District_text.getText().toString(),SubDis_text.getText().toString(),
                        Region_text.getText().toString());
                MainActivity.getInstance().from_location_search_act=true;
                context.startActivity(new Intent(context,MainActivity.class));
                ((Activity)context).finish();

            }
        });

       return convertView;
    }


}
