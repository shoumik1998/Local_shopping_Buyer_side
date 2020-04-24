package com.example.local_shopping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private List<Fetching_produtc_images> images_list;
    private Context context;

    public RecyclerAdapter(List<Fetching_produtc_images> images, Context context) {
        this.images_list = images;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.raw_layout,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.Name.setText(images_list.get(position).getName());
        holder.Price.setText(images_list.get(position).getPrice());
        Glide.with(context).load(images_list.get(position).getImage_path()).into(holder.imageView);

    }



    @Override
    public int getItemCount() {
        return images_list.size();
    }


    public static class  MyViewHolder extends  RecyclerView.ViewHolder{
        ImageView imageView;
        TextView Name,Price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.proIMgID);
            Name=itemView.findViewById(R.id.proNameID);
            Price=itemView.findViewById(R.id.propriceID);

        }


    }
    public  void  addImages(List<Fetching_produtc_images> images){
        for (Fetching_produtc_images im:images){

            images_list.add(im);
        }
        notifyDataSetChanged();
    }

}
