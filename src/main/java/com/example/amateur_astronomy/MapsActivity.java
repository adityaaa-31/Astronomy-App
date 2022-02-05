package com.example.amateur_astronomy;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.amateur_astronomy.databinding.ActivityMapsBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    Double l, ll;
    String title = "";
    String myurl = "";
    String apiurl = "";
    String issapi = "https://api.wheretheiss.at/v1/satellites/25544";
    String issapi2 = "http://api.open-notify.org/iss-now.json";
    String issname = "";
    String lati = "",longi = "";
    Double lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


//    mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//        @Override
//        public void onMapClick(@NonNull LatLng latLng) {
//
//        }
//    });
        RequestQueue queue = Volley.newRequestQueue(MapsActivity.this);

        JsonObjectRequest j2 = new JsonObjectRequest(Request.Method.GET, issapi, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                try {
                    issname = response.get("name").toString();
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
                Toast.makeText(MapsActivity.this, "Error", Toast.LENGTH_LONG);

            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            l = extras.getDouble("lat");
            ll = extras.getDouble("lon");
        }

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(l, ll);
        mMap.addMarker(new MarkerOptions().position(sydney).title("ISS"));
       // marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.my_marker_icon)));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        Toast.makeText(MapsActivity.this, issname, Toast.LENGTH_SHORT).show();
             Toast.makeText(MapsActivity.this, lati, Toast.LENGTH_LONG).show();
             Toast.makeText(MapsActivity.this, longi, Toast.LENGTH_LONG).show();
    }
}