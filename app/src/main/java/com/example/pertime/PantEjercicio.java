package com.example.pertime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class PantEjercicio extends AppCompatActivity {

    TextView tventreno;
    ImageButton btngluteo, btnbrazos, btnabdomen;
    private Typeface script;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pant_ejercicio);

        tventreno=findViewById(R.id.tventreno);

        btngluteo=findViewById(R.id.btngluteo);
        btnbrazos=findViewById(R.id.btnbrazos);
        btnabdomen=findViewById(R.id.btnabdomen);


        String fuente= "fonts/Balming.ttf";
        this.script=Typeface.createFromAsset(getAssets(),fuente);
        tventreno.setTypeface(script);

    }
    public void irgluteo(View view){
        Intent glu =new Intent(this, PanGluteo.class );
        startActivity(glu);
    }
    public void irbrazo(View view){
        Intent bra =new Intent(this, PanBrazo.class );
        startActivity(bra);
    }
    public void irabdomen(View view){
        Intent abs =new Intent(this, PanAbdomen.class );
        startActivity(abs);
    }
    public void registroentreno(View view){

        Intent intentrecuperar = getIntent();

        String string_usuario_recibido = intentrecuperar.getStringExtra("str_usuario");

        Intent reg =new Intent(this, RegistroEntreno.class );

        reg.putExtra("str_usuario", string_usuario_recibido);

        startActivity(reg);
    }
}
