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
                Intent intent=new Intent(context,MainActivity.class);
                intent.putExtra("country", Country_text.getText().toString());
                intent.putExtra("district", District_text.getText().toString());
                intent.putExtra("subdistrict", SubDis_text.getText().toString());
                intent.putExtra("region",Region_text.getText().toString());
                context.startActivity(intent);
            }
        });

       return convertView;
    }


}
