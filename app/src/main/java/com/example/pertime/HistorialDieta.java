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

public class HistorialDieta extends AppCompatActivity {

    private Typeface script;
    TextView tv1;

    Button btnmostrarhistorialdie;
    ListView listviewdieta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_dieta);

        tv1=findViewById(R.id.tv1);
        btnmostrarhistorialdie=findViewById(R.id.btnmostrarhistorialdie);
        listviewdieta=findViewById(R.id.listviewdieta);

        String fuente = "fonts/Balming.ttf";
        this.script = Typeface.createFromAsset(getAssets(), fuente);
        tv1.setTypeface(script);

        btnmostrarhistorialdie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
    }


    private void getData() {

        Intent intent = getIntent();

        String string_usuario_recibido1 = intent.getStringExtra("str_usuario");

        String url = Keysdieta.DATA_URL + string_usuario_recibido1;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HistorialDieta.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSON(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Keysdieta.JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {

                JSONObject jo = result.getJSONObject(i);

                String mostrarfecha = jo.getString(Keysdieta.KEY_FECHA);
                String mostrarpeso = jo.getString(Keysdieta.KEY_PESO);
                String mostrarbrazo = jo.getString(Keysdieta.KEY_BRAZO);
                String mostrarcintura = jo.getString(Keysdieta.KEY_CINTURA);
                String mostrarcadera = jo.getString(Keysdieta.KEY_CADERA);
                String mostrarmuslo = jo.getString(Keysdieta.KEY_MUSLO);

                final HashMap<String, String> employees = new HashMap<>();

                employees.put(Keysdieta.KEY_FECHA, mostrarfecha);
                employees.put(Keysdieta.KEY_PESO, mostrarpeso);
                employees.put(Keysdieta.KEY_BRAZO, mostrarbrazo);
                employees.put(Keysdieta.KEY_CINTURA, mostrarcintura);
                employees.put(Keysdieta.KEY_CADERA, mostrarcadera);
                employees.put(Keysdieta.KEY_MUSLO, mostrarmuslo);

                list.add(employees);

            }

        } catch (JSONException e) {

            e.printStackTrace();

        }
        ListAdapter adapter = new SimpleAdapter(

                HistorialDieta.this, list, R.layout.activity_listadieta,

                new String[]{Keysdieta.KEY_FECHA, Keysdieta.KEY_PESO, Keysdieta.KEY_BRAZO, Keysdieta.KEY_CINTURA, Keysdieta.KEY_CADERA, Keysdieta.KEY_MUSLO},

                new int[]{R.id.mostrarfecha, R.id.mostrarpeso, R.id.mostrarbrazo, R.id.mostrarcintura, R.id.mostrarcadera, R.id.mostrarmuslo});

        listviewdieta.setAdapter(adapter);
    }
}
