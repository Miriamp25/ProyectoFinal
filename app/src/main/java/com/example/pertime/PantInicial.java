package com.example.pertime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class PantInicial extends AppCompatActivity {

    ImageButton btnperiodo, btndieta, btnejercicio, btnperfil;
    TextView tvinicio;
    public Typeface script;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pant_inicial);

        tvinicio=findViewById(R.id.tvinicio);
        btnperiodo=findViewById(R.id.btnperiodo);
        btndieta=findViewById(R.id.btnsalud);
        btnejercicio=findViewById(R.id.btnejercicio);
        btnperfil=findViewById(R.id.btnperfil);


        String fuente= "fonts/Balming.ttf";
        this.script=Typeface.createFromAsset(getAssets(),fuente);
        tvinicio.setTypeface(script);

    }

    public void irperiodo(View view){

        Intent intentrecuperar1 = getIntent();

        String string_usuario_recibido1 = intentrecuperar1.getStringExtra("str_usuario");

        Intent per = new Intent(this, RegistroPeriodo.class);

        per.putExtra("str_usuario", string_usuario_recibido1);

        startActivity(per);

    }

    public void irdieta(View view){

        Intent intentrecuperar2 = getIntent();

        String string_usuario_recibido2 = intentrecuperar2.getStringExtra("str_usuario");

        Intent die =new Intent(this, RegistroDieta.class );

        die.putExtra("str_usuario", string_usuario_recibido2);

        startActivity(die);

    }
    public void irejercicio(View view){

        Intent intentrecuperar3 = getIntent();

        String string_usuario_recibido3 = intentrecuperar3.getStringExtra("str_usuario");

        Intent ejer =new Intent(this, PantEjercicio.class );

        ejer.putExtra("str_usuario", string_usuario_recibido3);

        startActivity(ejer);
    }
    public void irperfil(View view){

        Intent intentrecuperar4 = getIntent();

        String string_usuario_recibido4 = intentrecuperar4.getStringExtra("str_usuario");

        Intent perf =new Intent(this, PantPerfil.class );

        perf.putExtra("str_usuario", string_usuario_recibido4);

        startActivity(perf);
    }


}
