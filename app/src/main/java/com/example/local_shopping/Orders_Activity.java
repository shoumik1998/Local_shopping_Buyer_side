package com.example.local_shopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Orders_Activity extends AppCompatActivity {
    private TextView textView;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    Orders_Recycler_Adapter order_adapter;
    ApiInterface apiInterface;
    List<Orders_Model> orders_list;
    String product_id,issue_date;
    public  static  Orders_Activity orders_activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_);
        Paper.init(this);
        Toast.makeText(Orders_Activity.this, ""+Paper.book().read("phn_email"), Toast.LENGTH_SHORT).show();
        textView = findViewById(R.id.order_activity_textID);

        if (Paper.book().read("phn_email").equals(null)) {
            textView.setText("No orders to show.");
        } else {
            textView.setVisibility(View.GONE);
            onRecyclerViewTrigger();
            onPush();

        }




    }

    public static Orders_Activity getInstance(){
        return  orders_activity;
    }

    public void onRecyclerViewTrigger() {
        recyclerView = findViewById(R.id.order_recyclerID);
        layoutManager = new LinearLayoutManager(Orders_Activity.this);
        recyclerView.setLayoutManager(layoutManager);

        HashMap<String, String> map = new HashMap<>();
        map.put("phn_gmail", Paper.book().read("phn_email"));

        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<Orders_Model>> call = apiInterface.ordered_products(map);

        call.enqueue(new Callback<List<Orders_Model>>() {
            @Override
            public void onResponse(Call<List<Orders_Model>> call, Response<List<Orders_Model>> response) {
                if (response.isSuccessful()) {
                    orders_list = response.body();
                    order_adapter = new Orders_Recycler_Adapter(Orders_Activity.this, orders_list,null,null,null);
                    recyclerView.setAdapter(order_adapter);
                } else {
                    Toast.makeText(Orders_Activity.this, "invalid Response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Orders_Model>> call, Throwable t) {
                Toast.makeText(Orders_Activity.this, "Not responding " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    public String onPush() {
        PusherOptions options = new PusherOptions();
        options.setCluster("ap2");
        Pusher pusher = new Pusher("f4294e0ad72b1a26ebb2", options);

        pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {

            }

            @Override
            public void onError(String message, String code, Exception e) {

            }
        }, ConnectionState.ALL);

        Channel channel = pusher.subscribe(Paper.book().read("phn_email"));
        channel.bind("id-event", new SubscriptionEventListener() {
            @Override
            public void onEvent(PusherEvent event) {
                if (event != null) {

                    product_id =event.getData();

                } else {
                    Toast.makeText(Orders_Activity.this, "event null", Toast.LENGTH_SHORT).show();
                }

            }
        });

        channel.bind("issue-date", new SubscriptionEventListener() {
            @Override
            public void onEvent(PusherEvent event) {
                issue_date=event.getData();

            }
        });

        channel.bind("status-event", new SubscriptionEventListener() {
            @Override
            public void onEvent(PusherEvent event1) {



                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        order_adapter = new Orders_Recycler_Adapter(Orders_Activity.this, orders_list, product_id, event1.getData(),issue_date);
                        recyclerView.setAdapter(order_adapter);
                        Toast.makeText(Orders_Activity.this,Integer.valueOf(product_id)+" "+event1.getData(), Toast.LENGTH_SHORT).show();
                        MediaPlayer mp = MediaPlayer.create(Orders_Activity.this, R.raw.android_m);
                        mp.start();
                    }
                });
            }
        });


        return product_id;
    }
}