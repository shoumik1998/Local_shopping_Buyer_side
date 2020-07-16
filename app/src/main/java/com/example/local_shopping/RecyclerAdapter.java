package com.example.local_shopping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private List<Fetching_produtc_images> images_list;
    private Context context;
    private FileOutputStream outputStream;
    private  Uri image_uri;

    private  String product_name;
    private  String product_price;
    private  String shop_name;

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
        product_name=images_list.get(position).getName();
        product_price=images_list.get(position).getPrice();
        shop_name=images_list.get(position).getShop_Name();
       final String currency=images_list.get(position).getCrrency();
        final String location=images_list.get(position).getLocation();

            holder.Name.setText(product_name);

            holder.Price.setText(product_price + " " + currency);
            holder.ShopName.setText(shop_name);
            Glide.with(context).load(images_list.get(position).getImage_path()).into(holder.imageView);


            holder.parentLayout.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent intent = new Intent(context, Details_Activity.class);
                    intent.putExtra("ShopName", images_list.get(position).getShop_Name())
                            .putExtra("img_path", images_list.get(position).getImage_path())
                            .putExtra("Price", images_list.get(position).getPrice())
                            .putExtra("currency", currency)
                            .putExtra("Location", location)
                            .putExtra("Product_Name", images_list.get(position).getName());
                    context.startActivity(intent);
                    MainActivity.getInstance().from_location_search_act=false;


                }
            });

            holder.parentLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    save_and_sharing_task(holder.imageView, holder.Name.getText().toString(), holder.Price.getText().toString()
                            , images_list.get(position).getCrrency(), images_list.get(position).getLocation());
                    return false;
                }
            });

            if (context.getClass().getName() == Visit_Shop.class.getName()) {
                holder.ShopName.setVisibility(View.GONE);

            } else {
                holder.ShopName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, Visit_Shop.class);
                        intent.putExtra("userName", images_list.get(position).getUser_Name());
                        intent.putExtra("shopName", images_list.get(position).getShop_Name());
                        context.startActivity(intent);
                        Animatoo.animateWindmill(context);


                    }
                });
            }

    }



    @Override
    public int getItemCount() {
        return images_list.size();
    }


    public static class  MyViewHolder extends  RecyclerView.ViewHolder{
        ImageView imageView;
        TextView Name,Price,ShopName;
        CardView parentLayout;
        Button end_point_btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.proIMgID);
            Name=itemView.findViewById(R.id.proNameID);
            Price=itemView.findViewById(R.id.propriceID);
            ShopName=itemView.findViewById(R.id.shopNameID);
            parentLayout=itemView.findViewById(R.id.parentCardID);
            end_point_btn=itemView.findViewById(R.id.end_buttonID);
        }
    }
    public  void  addImages(List<Fetching_produtc_images> images){
        for (Fetching_produtc_images im:images){

            images_list.add(im);
        }
        notifyDataSetChanged();
    }


    public  void  save_and_sharing_task(final ImageView imgView, final String pro_name, final String pro_price,final String currency,final  String location){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
       View  view=LayoutInflater.from(context).inflate(R.layout.share_and_save_dialouge,null,false);
         TextView sharebtn=view.findViewById(R.id.shareID);
        TextView savebtn=view.findViewById(R.id.saveID);
        builder.setView(view);

        final AlertDialog dialog=builder.create();
        dialog.show();
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File filepath = Environment.getExternalStorageDirectory();
                File dir = new File(filepath.getAbsolutePath() + "/Local_Shopping/");
                if (!dir.exists()) {
                    dir.mkdir();
                }

                File file = new File(dir, System.currentTimeMillis() + ".jpg");

                try {

                    outputStream = new FileOutputStream(file);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                BitmapDrawable drawable = (BitmapDrawable) imgView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
                Canvas canvas = new Canvas(newBitmap);
                canvas.drawColor(Color.WHITE);
                canvas.drawBitmap(bitmap, 0, 0, null);

                    newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);



                try {
                        outputStream.flush();

                } catch (IOException e) {
                    Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                try {
                        outputStream.close();

                } catch (IOException e) {
                    Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
                    Intent imageScaner=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                     image_uri=Uri.fromFile(file);
                    imageScaner.setData(image_uri);
                    context.sendBroadcast(imageScaner);

                }else {
                    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,Uri.parse(String.valueOf(file))));
                }
                save_product_info(image_uri,pro_name,pro_price,currency,location);
                dialog.dismiss();

            }
        });
        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable drawable = (BitmapDrawable) imgView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
                Canvas canvas = new Canvas(newBitmap);
                canvas.drawColor(Color.WHITE);
                canvas.drawBitmap(bitmap, 0, 0, null);
                File file=new File(context.getExternalCacheDir(),"background.jpeg");
                try {
                    FileOutputStream fout=new FileOutputStream(file);
                    newBitmap.compress(Bitmap.CompressFormat.JPEG,100,fout);
                    fout.flush();
                    fout.close();
                    file.setReadable(true,false);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Intent intent=new Intent(android.content.Intent.ACTION_SEND);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(file));

                intent.putExtra(Intent.EXTRA_TEXT,pro_name);
                intent.setType("image/jpeg");


                context.startActivity(Intent.createChooser(intent, "Share Via : "));

                dialog.dismiss();


            }
        });
        dialog.setCancelable(true);


    }

    private void save_product_info(final Uri uri, final String sproduct_name, final String sproduct_price,final String sCurrency,final  String sShop_location) {


        class  Save_Product_Info extends AsyncTask<Void,Void,Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                MainActivity mainActivity=MainActivity.getInstance();
                Saved_Product_Model model=new Saved_Product_Model(sproduct_name,shop_name,
                        mainActivity.read_region(),sproduct_price,uri.toString(),sCurrency,sShop_location);

                Database_Client.getInstance(context).getAppDatabase().saved_pro_dao()
                        .insert_product(model);

                return null;
            }
        }
        Save_Product_Info product_info=new Save_Product_Info();
        product_info.execute();
        Toast.makeText(context, sproduct_name+" saved ", Toast.LENGTH_SHORT).show();

    }


}
