package com.example.amateur_astronomy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
//import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    Button b1, b2, b3;
    TextView tv1;
    ImageView v1;
    TextView d1;


    String explanation = "";
    String title = "";
    String myurl = "";
    String apiurl = "";
    String issapi = "https://api.wheretheiss.at/v1/satellites/25544";
    String issapi2 = "http://api.open-notify.org/iss-now.json";
    String issname = "";
    String lati = "",longi = "";
    double la, lo, lat, lon;
    String date = "";

   // v1.setImageResource(0);

    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        date = "" + year + "-" + (month + 1) + "-" + dayOfMonth;
        d1.setText(date);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.buttonISS);
        //b3 = findViewById(R.id.wallpaper);

        tv1 = findViewById(R.id.textView);
        v1 = findViewById(R.id.imageView);
        d1 = findViewById(R.id.dateText);

        //select date
        d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });



        //show
        b1.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View view) {
             Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
             apiurl ="https://api.nasa.gov/planetary/apod?api_key=p7RRU1XMSj1gNTa4oZ3xZWZ8j86IuptQQocxZtJE&date="+date ;


             // Instantiate the RequestQueue.
             RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

             JsonObjectRequest j1 = new JsonObjectRequest(Request.Method.GET, apiurl,null,  new Response.Listener<JSONObject>() {

                 @Override
                 public void onResponse(JSONObject response) {
                     try {
                         myurl = response.get("url").toString();
                         explanation = response.get("explanation").toString();
                         title = response.get("title").toString();

                         tv1.setText(title);
                         //tv1.setMovementMethod(new ScrollingMovementMethod());
                         Toast.makeText(MainActivity.this, response.get("date").toString(), Toast.LENGTH_SHORT).show();
                         Toast.makeText(MainActivity.this, myurl, Toast.LENGTH_SHORT).show();
                         Toast.makeText(MainActivity.this, title, Toast.LENGTH_SHORT).show();


                         // v1.setImageBitmap(getBitmapFromURL(myurl));


                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 }
             }, new Response.ErrorListener() {
                 @Override
                 public void onErrorResponse(VolleyError error) {
                    tv1.setText("Error" + error);
                    tv1.setText("Image not available");
                 }
             });


             //Add the request to the RequestQueue.
             queue.add(j1);

             Glide.with(MainActivity.this)
                     .asBitmap()
                     .load(myurl)
                     .into(new CustomTarget<Bitmap>() {
                         @Override
                         public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                             v1.setImageBitmap(resource);
                         }

                         @Override
                         public void onLoadCleared(@Nullable Drawable placeholder) {
                         }
                     });


         }


     });



        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                JsonObjectRequest j2 = new JsonObjectRequest(Request.Method.GET, issapi, null, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {

//
                        try {
                            issname = response.get("velocity").toString();
                            lati = response.getString("latitude");
                            lat = (double) response.get("latitude");
                            longi = response.getString("longitude");
                            lon = (double) response.get("longitude");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG);

                    }
                });

                tv1.setText(issname);
                tv1.setText(lati);
                tv1.setText(longi);

                String mapuri = "http://maps.google.com/maps?q="+lati+"%2C"+longi;

               try {
                    URI myuri = new URI(mapuri);
                } catch (URISyntaxException e) {
                   e.printStackTrace();
                }


//               Toast.makeText(MainActivity.this, mapuri, Toast.LENGTH_LONG).show();
//                Intent i = new Intent(Intent.ACTION_VIEW,
//                        Uri.parse(mapuri));
//                startActivity(i);
//

                queue.add(j2);

                Intent it = new Intent(MainActivity.this, MapsActivity.class);
                it.putExtra("lat", lat);
                it.putExtra("lon", lon);
                startActivity(it);

            }
        });


        tv1.setOnClickListener(new View.OnClickListener() {
            String value = "value";
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity2.class);
                i.putExtra("message", explanation);
                i.putExtra("mytitle", title);
                i.putExtra("lat", lati);
                i.putExtra("lon", longi);
                i.putExtra("vel", issname);


                startActivity(i);
            }
        });

        v1.setOnClickListener(new View.OnClickListener() {
            String value = "value";

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity3.class);

                i.putExtra("myapi", apiurl);

                startActivity(i);
            }
        });
    }






}