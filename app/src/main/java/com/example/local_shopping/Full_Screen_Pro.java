package com.example.local_shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class Full_Screen_Pro extends AppCompatActivity {

    private  Saved_Activity saved_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full__screen__pro);

        ImageView imageView=findViewById(R.id.full_scrn_img_ID);

        String full_img_uri=getIntent().getStringExtra("full_img_uri");
        if (full_img_uri!=null) {
            imageView.setImageURI(Uri.parse(full_img_uri));
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSplit(this);

    }
}
