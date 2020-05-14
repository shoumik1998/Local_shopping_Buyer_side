package com.example.local_shopping;

import android.content.Context;

import androidx.room.Room;

public class Database_Client {
    private Context context;
    private static  Database_Client mInstance;
    private  AppDatabase appDatabase;

    public Database_Client(Context context) {
        this.context = context;
        appDatabase= Room.databaseBuilder(context,AppDatabase.class,"Saved_Products")
                .build();

    }

    public  static  synchronized Database_Client getInstance(Context context){
        if (mInstance==null){
            mInstance=new Database_Client(context);
        }
        return  mInstance;
    }

    public  AppDatabase getAppDatabase(){
        return  appDatabase;
    }
}
