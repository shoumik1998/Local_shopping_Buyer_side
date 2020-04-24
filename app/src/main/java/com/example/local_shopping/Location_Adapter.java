package com.example.local_shopping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Location_Adapter extends ArrayAdapter {
    private Context context;
    private List<Locations> locations;

    public Location_Adapter( Context context,  List<Locations> locations) {
        super(context,R.layout.location_layout,locations);
        this.context=context;
        this.locations=locations;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View view= LayoutInflater.from(context).inflate(R.layout.location_layout,parent,false);
       Locations location=locations.get(position);

        TextView Country_text=convertView.findViewById(R.id.countryID);
        TextView District_text=convertView.findViewById(R.id.districtID);
        TextView SubDis_text=convertView.findViewById(R.id.subdistrictID);
        TextView Region_text=convertView.findViewById(R.id.regionID);

        Country_text.setText(location.getCountry());
        District_text.setText(location.getDistrict());
        SubDis_text.setText(location.getSubDistrict());
        Region_text.setText(location.getRegion());



       return  view;
    }
}
