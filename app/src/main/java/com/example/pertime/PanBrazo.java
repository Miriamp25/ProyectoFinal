package com.example.pertime;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PanBrazo extends AppCompatActivity {

    TextView tvbrazos, tv1, tv2, tv3, tv4, tv5, tv6, tv7;
    ImageView imagen1, imagen2, imagen3, imagen4, imagen5, imagen6, imagen7;;
    private Typeface script;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pan_brazo);

        tvbrazos=findViewById(R.id.tvbrazos);

        String fuente= "fonts/Balming.ttf";
        this.script=Typeface.createFromAsset(getAssets(),fuente);
        tvbrazos.setTypeface(script);

        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        tv3=findViewById(R.id.tv3);
        tv4=findViewById(R.id.tv4);
        tv5=findViewById(R.id.tv5);
        tv6=findViewById(R.id.tv6);
        tv7=findViewById(R.id.tv7);

        imagen1=findViewById(R.id.imagen1);
        imagen2=findViewById(R.id.imagen2);
        imagen3=findViewById(R.id.imagen3);
        imagen4=findViewById(R.id.imagen4);
        imagen5=findViewById(R.id.imagen5);
        imagen6=findViewById(R.id.imagen6);
        imagen7=findViewById(R.id.imagen7);
    }
}
