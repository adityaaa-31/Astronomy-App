package com.example.amateur_astronomy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

public class MainActivity3 extends AppCompatActivity {

    ImageView iv2;
    Button btnClose;
    String apiurl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            apiurl = extras.getString("myapi");

        }

//        btnClose.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                MainActivity3.this.finish();
//            }
//        });

        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity3.this, apiurl, Toast.LENGTH_SHORT);
            }
        });


        Glide.with(MainActivity3.this)
                .asBitmap()
                .load(apiurl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                       iv2.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });

    }
}