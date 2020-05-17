package com.example.local_shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class Splash_Screen extends AppCompatActivity {
            private  MainActivity mainActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                        startActivity(new Intent(Splash_Screen.this, MainActivity.class));
                        Animatoo.animateShrink(Splash_Screen.this);
                        finish();



            }
        },1500);
    }
}
