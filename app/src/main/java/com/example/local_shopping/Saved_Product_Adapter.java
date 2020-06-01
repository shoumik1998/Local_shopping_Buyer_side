package com.example.local_shopping;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;

import java.util.List;

public class Saved_Product_Adapter extends RecyclerView.Adapter<Saved_Product_Adapter.MySavedViewHolder> {
    private Context context;
    private List<Saved_Product_Model> saved_list;


    public Saved_Product_Adapter(Context context, List<Saved_Product_Model> saved_list) {
        this.context = context;
        this.saved_list = saved_list;
    }

    @NonNull
    @Override
    public MySavedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.saved_product_layout,parent,false);

        return new MySavedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MySavedViewHolder holder, final int position) {

        holder.saved_pro_name_text.setText(saved_list.get(position).getSaved_pro_name());
        holder.saved_pro_price_text.setText(saved_list.get(position).getSaved_pro_price());
        holder.saved_pro_shop_name_text.setText(saved_list.get(position).getSaved_pro_shop_name());
        holder.saved_pro_region_text.setText(saved_list.get(position).getSaved_pro_region());
        if (saved_list.get(position).getsImageUri()!=null) {
            Glide.with(context).load(Uri.parse(saved_list.get(position).getsImageUri())).into(holder.saved_imageView);
        }else {
            Toast.makeText(context, "Image Named "+saved_list.get(position).getSaved_pro_name()+"Is Not Found", Toast.LENGTH_SHORT).show();
        }



    }




    @Override
    public int getItemCount() {
        return saved_list.size();

    }

    private  void  delete_product_info(final Saved_Product_Model model){

        class  Delete_Product_Info extends AsyncTask<Void,Void,Void>{

            @Override
            protected Void doInBackground(Void... voids) {

                Database_Client.getInstance(context).getAppDatabase()
                        .saved_pro_dao().delete_product(model);


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

            }
        }

        Delete_Product_Info delete_product_info=new Delete_Product_Info();
        delete_product_info.execute();
    }


    public  class  MySavedViewHolder extends RecyclerView.ViewHolder{
        ImageView saved_imageView;
        TextView saved_pro_name_text,saved_pro_price_text,saved_pro_shop_name_text,saved_pro_region_text;

        public MySavedViewHolder(@NonNull View itemView) {
            super(itemView);
            saved_imageView=itemView.findViewById(R.id.saved_iamgeView_ID);
            saved_pro_name_text=itemView.findViewById(R.id.saved_product_name_ID);
            saved_pro_price_text=itemView.findViewById(R.id.saved_price_ID);
            saved_pro_shop_name_text=itemView.findViewById(R.id.saved_shop_name_ID);
            saved_pro_region_text=itemView.findViewById(R.id.saved_region_ID);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,Full_Screen_Pro.class);
                    intent.putExtra("full_img_uri",saved_list.get(getAdapterPosition()).getsImageUri());
                    context.startActivity(intent);
                    Animatoo.animateZoom(context);

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setTitle("Are You Sure ");
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Saved_Product_Model model=saved_list.get(getAdapterPosition());
                            delete_product_info(model);
                            saved_list.remove(getAdapterPosition());
                            notifyDataSetChanged();

                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();

                    return false;
                }
            });


        }
    }
}
