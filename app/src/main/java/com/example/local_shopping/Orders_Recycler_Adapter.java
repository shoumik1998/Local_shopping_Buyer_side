package com.example.local_shopping;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Orders_Recycler_Adapter extends RecyclerView.Adapter<Orders_Recycler_Adapter.Orders_ViewHolder> {
    Context context;
    List<Orders_Model> orders_list;


    public Orders_Recycler_Adapter(Context context, List<Orders_Model> orders_list) {
        this.context = context;
        this.orders_list = orders_list;
    }

    @NonNull
    @Override
    public Orders_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orders_item_view, parent, false);

        return new Orders_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Orders_ViewHolder holder, final int position) {

        holder.textView.setText(orders_list.get(position).getProduct_name());
        Glide.with(context).load(orders_list.get(position).getImagepath()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Ordered_Details_Activity.class);
                intent.putExtra("p_name",orders_list.get(position).getProduct_name())
                        .putExtra("p_price",orders_list.get(position).getPrice())
                        .putExtra("p_number",orders_list.get(position).getProduct_number())
                        .putExtra("c_name",orders_list.get(position).getClient_name())
                        .putExtra("c_contact",orders_list.get(position).getContact_no())
                        .putExtra("address",orders_list.get(position).getAddress())
                        .putExtra("issue_date",orders_list.get(position).getIssue_date())
                        .putExtra("delivering_date",orders_list.get(position).getDelivering_date())
                        .putExtra("imagepath",orders_list.get(position).getImagepath())
                        .putExtra("user_name",orders_list.get(position).getUser_name());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders_list.size();
    }

    public class Orders_ViewHolder extends  RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        CardView cardView;

        public Orders_ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.order_item_view_p_nameID);
            imageView = itemView.findViewById(R.id.order_item_view_imageID);
            cardView = itemView.findViewById(R.id.order_item_viewID);

        }
    }
}
