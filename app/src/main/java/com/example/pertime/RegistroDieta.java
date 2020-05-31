package com.example.pertime;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class RegistroDieta extends AppCompatActivity  {

    TextView tv1, tv2;
    Button btndia;
    int dia, mes, ano;
    EditText etdia, etpeso, etcmbrazo, etcmcintura, etcmcadera, etcmmuslo;
    private Typeface script;
    String str_escribe_dia, str_escribe_peso, str_escribe_brazo, str_escribe_cintura, str_escribe_cadera, str_escribe_muslo;
    String url = "https://rogdomain.ddns.net:8860/yourtime/registerdieta.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_dieta);

        tv1 = findViewById(R.id.tv1);
        etdia=findViewById(R.id.etdia);
        etpeso=findViewById(R.id.etpeso);
        etcmbrazo=findViewById(R.id.etcmbrazo);
        etcmcintura=findViewById(R.id.etejercicio);
        etcmcadera=findViewById(R.id.ettiempo);
        etcmmuslo=findViewById(R.id.etcmmuslo);
        btndia = findViewById(R.id.btndia);
        //usuario=findViewById(R.id.usuario);

        String fuente= "fonts/Balming.ttf";
        this.script=Typeface.createFromAsset(getAssets(),fuente);
        tv1.setTypeface(script);
        //tv2.setTypeface(script);
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
    public void registrardieta(View view){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Por favor espere...");

        String verificar_dia = etdia.getText().toString();

        if (verificar_dia.isEmpty()) {
            etdia.setError("Ingrese el dia");
        } else {

            progressDialog.show();

            str_escribe_dia = etdia.getText().toString().trim();
            str_escribe_peso = etpeso.getText().toString().trim();
            str_escribe_brazo = etcmbrazo.getText().toString().trim();
            str_escribe_cintura = etcmcintura.getText().toString().trim();
            str_escribe_cadera = etcmcadera.getText().toString().trim();
            str_escribe_muslo = etcmmuslo.getText().toString().trim();
            //str_escribe_usuario = usuario.getText().toString().trim();

            StringRequest request = new StringRequest (Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    etdia.setText("");
                    etpeso.setText("");
                    etcmbrazo.setText("");
                    etcmcintura.setText("");
                    etcmcadera.setText("");
                    etcmmuslo.setText("");

                    if (response.equalsIgnoreCase("Datos guardados")) {

                        //startActivity(new Intent(getApplicationContext(), PantInicial.class));

                        Intent intentrecuperar1 = getIntent();

                        String string_usuario_recibido1 = intentrecuperar1.getStringExtra("str_usuario");

                        Intent intent1 = new Intent(getApplicationContext(), PantInicial.class);

                        intent1.putExtra("str_usuario", string_usuario_recibido1);

                        startActivity(intent1);

                        Toast.makeText(RegistroDieta.this, response, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(RegistroDieta.this, response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(RegistroDieta.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    Intent intent = getIntent();

                    String string_usuario_recibido1 = intent.getStringExtra("str_usuario");

                    params.put("fecha", str_escribe_dia);
                    params.put("peso", str_escribe_peso);
                    params.put("brazo", str_escribe_brazo);
                    params.put("cintura", str_escribe_cintura);
                    params.put("cadera", str_escribe_cadera);
                    params.put("muslo", str_escribe_muslo);
                    params.put("usuario", string_usuario_recibido1);

                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(RegistroDieta.this);
            requestQueue.add(request);

        }

    }


}
