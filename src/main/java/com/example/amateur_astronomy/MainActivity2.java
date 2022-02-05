package com.example.amateur_astronomy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    TextView title, messageField;



    // String mymessage = null;
   // String titleText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        title = findViewById(R.id.titletext);
        messageField = findViewById(R.id.messageWindow);

        Bundle extras = getIntent().getExtras();
        String message = "";
        String titleText = "";
        String lat = "", lon = "", v = "";

        if (extras != null){
            message = extras.getString("message");
            titleText = extras.getString("mytitle");
            lat = extras.getString("lat");
            lon = extras.getString("lon");
            v = extras.getString("vel");
        }
        title.setText(titleText);

        messageField.setText(message);
        messageField.setText("Latitude: "+lat+"\n"+"Longitude: "+lon+"\n"+"Velocity: "+v);
        messageField.setMovementMethod(new ScrollingMovementMethod());
    }
}