package com.example.local_shopping;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import id.zelory.compressor.Compressor;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private List<Fetching_produtc_images> images_list;
    private Context context;
    private  List<Locations> locationsList;
    private FileOutputStream outputStream;
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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.Name.setText(images_list.get(position).getName());
        holder.Price.setText(images_list.get(position).getPrice());
        holder.ShopName.setText(images_list.get(position).getShop_Name());
        Glide.with(context).load(images_list.get(position).getImage_path()).into(holder.imageView);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Details_Activity.class);
                intent.putExtra("ShopName",images_list.get(position).getShop_Name())
                        .putExtra("img_path",images_list.get(position).getImage_path())
                        .putExtra("Price",images_list.get(position).getPrice())
                        .putExtra("Product_Name",images_list.get(position).getName());
                MainActivity.getInstance().from_search_act=false;
                context.startActivity(intent);



            }
        });

        holder.parentLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                save_and_sharing_task(holder.imageView);
                return false;
            }
        });


    }



    @Override
    public int getItemCount() {
        return images_list.size();
    }


    public static class  MyViewHolder extends  RecyclerView.ViewHolder{
        ImageView imageView;
        TextView Name,Price,ShopName;
        CardView parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.proIMgID);
            Name=itemView.findViewById(R.id.proNameID);
            Price=itemView.findViewById(R.id.propriceID);
            ShopName=itemView.findViewById(R.id.shopNameID);
            parentLayout=itemView.findViewById(R.id.parentCardID);
        }
    }
    public  void  addImages(List<Fetching_produtc_images> images){
        for (Fetching_produtc_images im:images){

            images_list.add(im);
        }
        notifyDataSetChanged();
    }


    public  void  save_and_sharing_task(final ImageView imgView){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
       View  view=LayoutInflater.from(context).inflate(R.layout.share_and_save_dialouge,null,false);
        Button sharebtn=view.findViewById(R.id.shareID);
        Button savebtn=view.findViewById(R.id.saveID);
        builder.setView(view);
        final AlertDialog dialog=builder.create();
        dialog.show();
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable drawable=(BitmapDrawable) imgView.getDrawable();
                Bitmap bitmap=drawable.getBitmap();

                File filepath= Environment.getExternalStorageDirectory();
                File dir=new File(filepath.getAbsolutePath()+"/Local_Shopping/");
                dir.mkdir();
                File file=new File(dir,System.currentTimeMillis()+".jpg");

                try {
                    outputStream=new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                try {
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Toast.makeText(context, "image  saved ", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "save btn is clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();


            }
        });
        dialog.setCancelable(true);


    }

}
