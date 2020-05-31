package com.example.pertime;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegistroPeriodo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView tv1, tv2, tvsangrado, tvdolor, tvemociones;
    EditText etdia;
    Button btndia;
    Spinner spinnersangrado, spinnerdolor, spinneremociones;
    private Typeface script;
    int dia, mes, ano;

    String str_escribe_dia, str_escribe_sangrado, str_escribe_dolor, str_escribe_emocion;

    String url = "https://rogdomain.ddns.net:8860/yourtime/registerperiodo.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_periodo);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        //tv3 =findViewById(R.id.tv3);

        tvsangrado=findViewById(R.id.tvsangrado);
        tvdolor=findViewById(R.id.tvdolor);
        tvemociones=findViewById(R.id.tvemociones);


        etdia = findViewById(R.id.etdia);
        btndia = findViewById(R.id.btndia);
        spinnersangrado = findViewById(R.id.spinnersangrado);
        spinnerdolor = findViewById(R.id.spinnerdolor);
        spinneremociones = findViewById(R.id.spinneremociones);

        String[] opsangrado = {"Leve", "Medio", "Abundante"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, opsangrado);
        spinnersangrado.setAdapter(adapter);
        spinnersangrado.setOnItemSelectedListener(this);

        String[] opdolor = {"Cabeza", "Ovulación", "Senos", "Cólicos"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, opdolor);
        spinnerdolor.setAdapter(adapter1);
        spinnerdolor.setOnItemSelectedListener(this);

        String[] opemociones = {"Alegre", "Sensible", "Triste", "Enfado"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, opemociones);
        spinneremociones.setAdapter(adapter2);
        spinneremociones.setOnItemSelectedListener(this);

        String fuente = "fonts/Balming.ttf";
        this.script = Typeface.createFromAsset(getAssets(), fuente);
        tv1.setTypeface(script);
        tv2.setTypeface(script);
        //tv3.setTypeface(script);

    }

    public void mostrarcalendario(View view) {
        if (view == btndia) {
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            ano = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    etdia.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                }
            }
                    , dia, mes, ano);
            datePickerDialog.show();
        }
    }
    public void registrarperiodo(View view){

        String verificar_dia = etdia.getText().toString();
        String verificar_sangrado = spinnersangrado.getSelectedItem().toString();
        String verificar_dolor = spinnerdolor.getSelectedItem().toString();
        String verificar_emocion = spinneremociones.getSelectedItem().toString();


        if (verificar_dia.isEmpty()) {
            etdia.setError("Ingrese el dia");
        }else{

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Por favor espere...");

            progressDialog.show();

            str_escribe_dia = etdia.getText().toString().trim();
            str_escribe_sangrado = tvsangrado.getText().toString().trim();
            str_escribe_dolor = tvdolor.getText().toString().trim();
            str_escribe_emocion = tvemociones.getText().toString().trim();


            StringRequest request = new StringRequest (Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    etdia.setText("");
                    tvsangrado.setText("");
                    tvdolor.setText("");
                    tvemociones.setText("");



                    if (response.equalsIgnoreCase("Datos guardados")) {

                        //startActivity(new Intent(getApplicationContext(), PantInicial.class));
                        Intent intentrecuperar1 = getIntent();

                        String string_usuario_recibido1 = intentrecuperar1.getStringExtra("str_usuario");

                        Intent intent1 = new Intent(getApplicationContext(), PantInicial.class);

                        intent1.putExtra("str_usuario", string_usuario_recibido1);

                        startActivity(intent1);

                        Toast.makeText(RegistroPeriodo.this, response, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(RegistroPeriodo.this, response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(RegistroPeriodo.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    Intent intent = getIntent();

                    String string_usuario_recibido2 = intent.getStringExtra("str_usuario");

                    params.put("fecha", str_escribe_dia);
                    params.put("sangrado", str_escribe_sangrado);
                    params.put("dolor", str_escribe_dolor);
                    params.put("emociones", str_escribe_emocion);
                    params.put("usuario", string_usuario_recibido2);


                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(RegistroPeriodo.this);
            requestQueue.add(request);

        }
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tvsangrado.setText(spinnersangrado.getSelectedItem().toString());
        tvdolor.setText(spinnerdolor.getSelectedItem().toString());
        tvemociones.setText(spinneremociones.getSelectedItem().toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

