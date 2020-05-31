package com.example.pertime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HistorialEntreno extends AppCompatActivity {
    private Typeface script;
    TextView tv1;

    Button btnhistorialentreno;
    ListView listviewentreno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_entreno);

        tv1=findViewById(R.id.tv1);
        btnhistorialentreno=findViewById(R.id.btnhistorialentreno);
        listviewentreno=findViewById(R.id.listviewentreno);

        String fuente = "fonts/Balming.ttf";
        this.script = Typeface.createFromAsset(getAssets(), fuente);
        tv1.setTypeface(script);

        btnhistorialentreno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
    }

    private void getData() {

        Intent intent = getIntent();

        String string_usuario_recibido1 = intent.getStringExtra("str_usuario");

        String url = Keysentreno.DATA_URL + string_usuario_recibido1;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HistorialEntreno.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSON(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Keysentreno.JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {

                JSONObject jo = result.getJSONObject(i);

                String mostrarfecha = jo.getString(Keysentreno.KEY_FECHA);
                String mostrargrupo = jo.getString(Keysentreno.KEY_GRUPO);
                String mostrarejercicio = jo.getString(Keysentreno.KEY_EJERCICIO);
                String mostrartiempo = jo.getString(Keysentreno.KEY_TIEMPO);


                final HashMap<String, String> employees = new HashMap<>();

                employees.put(Keysentreno.KEY_FECHA, mostrarfecha);
                employees.put(Keysentreno.KEY_GRUPO, mostrargrupo);
                employees.put(Keysentreno.KEY_EJERCICIO, mostrarejercicio);
                employees.put(Keysentreno.KEY_TIEMPO, mostrartiempo);


                list.add(employees);

            }

        } catch (JSONException e) {

            e.printStackTrace();

        }
        ListAdapter adapter = new SimpleAdapter(

                HistorialEntreno.this, list, R.layout.activity_listaentreno,

                new String[]{Keysentreno.KEY_FECHA, Keysentreno.KEY_GRUPO, Keysentreno.KEY_EJERCICIO, Keysentreno.KEY_TIEMPO},

                new int[]{R.id.mostrarfecha, R.id.mostrargrupo, R.id.mostrarejercicio, R.id.mostrartiempo});

        listviewentreno.setAdapter(adapter);
    }
}
