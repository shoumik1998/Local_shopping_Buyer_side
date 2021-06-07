package com.example.local_shopping;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
    String product_id,status_code,issue_date;


    public Orders_Recycler_Adapter(Context context, List<Orders_Model> orders_list,String id,String status_code,String issue_date) {
        this.context = context;
        this.orders_list = orders_list;
        this.product_id=id;
        this.status_code=status_code;
        this.issue_date=issue_date;

    }

    @NonNull
    @Override
    public Orders_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orders_item_view, parent, false);

        return new Orders_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Orders_ViewHolder holder, final int position) {

        if (status_code != null) {
            if ( product_id.equals(String.valueOf(orders_list.get(position).getProduct_id())) && status_code.equals(String.valueOf(1)) && issue_date.equals(orders_list.get(position).getIssue_date())) {
                holder.cardView.setCardBackgroundColor(Color.parseColor("#ff222f"));

            }
        }

        if (orders_list.get(position).getOrder_status()==1) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#ff222f"));
        }

        holder.textView.setText(orders_list.get(position).getProduct_name());
        Glide.with(context).load(orders_list.get(position).getImagepath()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Ordered_Details_Activity.class);
                intent.putExtra("p_name", orders_list.get(position).getProduct_name())
                        .putExtra("p_price", orders_list.get(position).getPrice())
                        .putExtra("p_number", orders_list.get(position).getProduct_number())
                        .putExtra("c_name", orders_list.get(position).getClient_name())
                        .putExtra("c_contact", orders_list.get(position).getContact_no())
                        .putExtra("address", orders_list.get(position).getAddress())
                        .putExtra("issue_date", orders_list.get(position).getIssue_date())
                        .putExtra("delivering_date", orders_list.get(position).getDelivering_date())
                        .putExtra("order_status", orders_list.get(position).getOrder_status())
                        .putExtra("imagepath", orders_list.get(position).getImagepath())
                        .putExtra("user_name", orders_list.get(position).getUser_name());
                context.startActivity(intent);
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.cardView.setBackgroundColor(Color.parseColor("#ff222f"));
                return false;
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
