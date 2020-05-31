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

public class RegistroEntreno extends AppCompatActivity {


    TextView tv1;
    Button btndia;
    int dia, mes, ano;
    EditText etdia, etgmuscular, etejercicio, ettiempo;
    private Typeface script;
    String str_escribe_dia, str_escribe_gmuscular, str_escribe_ejercicio, str_escribe_tiempo;
    String url = "https://rogdomain.ddns.net:8860/yourtime/registerejercicio.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_entreno);

        tv1=findViewById(R.id.tv1);
        etdia=findViewById(R.id.etdia);
        btndia = findViewById(R.id.btndia);
        etgmuscular=findViewById(R.id.etgmuscular);
        etejercicio=findViewById(R.id.etejercicio);
        ettiempo=findViewById(R.id.ettiempo);

        String fuente= "fonts/Balming.ttf";
        this.script=Typeface.createFromAsset(getAssets(),fuente);
        tv1.setTypeface(script);

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
    public void registrarejercicio(View view)  {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Por favor espere...");

        String verificar_dia = etdia.getText().toString();

        if (verificar_dia.isEmpty()) {
            etdia.setError("Ingrese el dia");
        }else {

            progressDialog.show();

            str_escribe_dia = etdia.getText().toString().trim();
            str_escribe_gmuscular = etgmuscular.getText().toString().trim();
            str_escribe_ejercicio = etejercicio.getText().toString().trim();
            str_escribe_tiempo = ettiempo.getText().toString().trim();

            StringRequest request = new StringRequest (Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    etdia.setText("");
                    etgmuscular.setText("");
                    etejercicio.setText("");
                    ettiempo.setText("");

                    if (response.equalsIgnoreCase("Datos guardados")) {

                        Intent intentrecuperar1 = getIntent();

                        String string_usuario_recibido1 = intentrecuperar1.getStringExtra("str_usuario");

                        Intent intent1 = new Intent(getApplicationContext(), PantInicial.class);

                        intent1.putExtra("str_usuario", string_usuario_recibido1);

                        startActivity(intent1);

                        Toast.makeText(RegistroEntreno.this, response, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(RegistroEntreno.this, response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(RegistroEntreno.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    Intent intent = getIntent();

                    String string_usuario_recibido2 = intent.getStringExtra("str_usuario");

                    params.put("fecha", str_escribe_dia);
                    params.put("gmuscular", str_escribe_gmuscular);
                    params.put("ejercicio", str_escribe_ejercicio);
                    params.put("tiempo", str_escribe_tiempo);
                    params.put("usuario", string_usuario_recibido2);

                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(RegistroEntreno.this);
            requestQueue.add(request);

        }
    }
}
