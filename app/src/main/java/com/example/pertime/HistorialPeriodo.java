package com.example.pertime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class HistorialPeriodo extends AppCompatActivity {

    private Typeface script;
    TextView tv1;
    Button btnmostrarhistorialper;
    ListView listviewperiodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_periodo);

        tv1=findViewById(R.id.tv1);
        btnmostrarhistorialper=findViewById(R.id.btnmostrarhistorialper);
        listviewperiodo=findViewById(R.id.listviewperfil);

        String fuente = "fonts/Balming.ttf";
        this.script = Typeface.createFromAsset(getAssets(), fuente);
        tv1.setTypeface(script);

        btnmostrarhistorialper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
    }


    private void getData() {

        Intent intent = getIntent();

        String string_usuario_recibido1 = intent.getStringExtra("str_usuario");

        String url = Keysperiodo.DATA_URL + string_usuario_recibido1;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HistorialPeriodo.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSON(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Keysperiodo.JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {

                JSONObject jo = result.getJSONObject(i);

                String mostrarfecha = jo.getString(Keysperiodo.KEY_FECHA);
                String mostrarsangrado = jo.getString(Keysperiodo.KEY_SANGRADO);
                String mostrardolor = jo.getString(Keysperiodo.KEY_DOLOR);
                String mostraremociones = jo.getString(Keysperiodo.KEY_EMOCIONES);

                final HashMap<String, String> employees = new HashMap<>();

                employees.put(Keysperiodo.KEY_FECHA, mostrarfecha);
                employees.put(Keysperiodo.KEY_SANGRADO, mostrarsangrado);
                employees.put(Keysperiodo.KEY_DOLOR, mostrardolor);
                employees.put(Keysperiodo.KEY_EMOCIONES, mostraremociones);

                list.add(employees);

            }

        } catch (JSONException e) {

            e.printStackTrace();

        }
        ListAdapter adapter = new SimpleAdapter(

                HistorialPeriodo.this, list, R.layout.activity_listaperiodo,

                new String[]{Keysperiodo.KEY_FECHA, Keysperiodo.KEY_SANGRADO, Keysperiodo.KEY_DOLOR, Keysperiodo.KEY_EMOCIONES},

                new int[]{R.id.mostrarfecha, R.id.mostrarsangrado, R.id.mostrardolor, R.id.mostraremociones});

        listviewperiodo.setAdapter(adapter);

    }

    }

