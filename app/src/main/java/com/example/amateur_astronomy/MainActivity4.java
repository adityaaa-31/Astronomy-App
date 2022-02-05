package com.example.amateur_astronomy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity4 extends AppCompatActivity {

    double lat, lon;
    TextView latt;
    TextView lonn, vell;
    Button b1;

    String myurl = "";
    String apiurl = "";
    String issapi = "https://api.wheretheiss.at/v1/satellites/25544";
    String issapi2 = "http://api.open-notify.org/iss-now.json";
    String issname = "";
    String lati = "",longi = "", v = "";
    double la, lo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        latt = findViewById(R.id.La);
        lonn = findViewById(R.id.Lo);
        vell = findViewById(R.id.Ve);
        b1 = findViewById(R.id.button2);
        Button b2  = findViewById(R.id.map);



            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    RequestQueue queue = Volley.newRequestQueue(MainActivity4.this);

                    JsonObjectRequest j2 = new JsonObjectRequest(Request.Method.GET, issapi, null, new Response.Listener<JSONObject>() {


                        @Override
                        public void onResponse(JSONObject response) {

//                        JSONObject a = null;
//                        try {
//                            a = response.getJSONObject("iss_position");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        try {
//                            lat = (double) a.get("latitude");
//                            lon = (double) a.get("longitude");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
                            try {
                                v = response.get("velocity").toString();
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
                            Toast.makeText(MainActivity4.this, "Error", Toast.LENGTH_LONG);

                        }
                    });

                    latt.setText(lati);
                    lonn.setText(longi);
                    vell.setText(v);

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

//                LatLng fromPosition = new LatLng(lat,lon);
//
//                Bundle args = new Bundle();
//                args.putParcelable("longLat_dataProvider", fromPosition);

                }
            });

            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(MainActivity4.this, MapsActivity.class);
                     it.putExtra("lat", lat);
                    it.putExtra("lon", lon);
                    startActivity(it);
                }
            });

    }
}