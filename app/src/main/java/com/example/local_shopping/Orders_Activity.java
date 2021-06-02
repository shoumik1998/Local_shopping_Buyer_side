package com.example.local_shopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
    private RecyclerView recyclerView;
    private  RecyclerView.LayoutManager layoutManager;
    Orders_Recycler_Adapter order_adapter;
    ApiInterface apiInterface;
    List<Orders_Model> orders_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_);
        Paper.init(this);

        onRecyclerViewTrigger();

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
        },ConnectionState.ALL);

        Channel channel=pusher.subscribe("buyer-channel");
        channel.bind("buyer-event", new SubscriptionEventListener() {
            @Override
            public void onEvent(PusherEvent event) {
                if (event != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Orders_Activity.this, "" +event.getData().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(Orders_Activity.this, "event null", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public  void  onRecyclerViewTrigger(){
        recyclerView = findViewById(R.id.order_recyclerID);
        layoutManager = new LinearLayoutManager(Orders_Activity.this);
        recyclerView.setLayoutManager(layoutManager);

        HashMap<String,String> map=new HashMap<>();
        map.put("phn_gmail", Paper.book().read("phn_email").toString());

        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<Orders_Model>> call = apiInterface.ordered_products(map);

        call.enqueue(new Callback<List<Orders_Model>>() {
            @Override
            public void onResponse(Call<List<Orders_Model>> call, Response<List<Orders_Model>> response) {
                if (response.isSuccessful()) {
                    orders_list = response.body();
                    order_adapter = new Orders_Recycler_Adapter(Orders_Activity.this, orders_list);
                    recyclerView.setAdapter(order_adapter);
                } else {
                    Toast.makeText(Orders_Activity.this, "invalid Response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Orders_Model>> call, Throwable t) {
                Toast.makeText(Orders_Activity.this, "Not responding "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}