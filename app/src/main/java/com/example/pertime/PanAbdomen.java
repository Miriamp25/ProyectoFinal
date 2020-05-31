package com.example.pertime;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class PanAbdomen extends AppCompatActivity {

    TextView tvabdomen;
    private Typeface script;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pan_abdomen);
        tvabdomen=findViewById(R.id.tvabdomen);

        String fuente= "fonts/Balming.ttf";
        this.script=Typeface.createFromAsset(getAssets(),fuente);
        tvabdomen.setTypeface(script);
    }
}
