package com.example.pertime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class PantPerfil extends AppCompatActivity {

    TextView tvperfil;
    Button btnmostrarhistorialperfil;
    ListView listviewperfil;
    private Typeface script;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pant_perfil);

        tvperfil = findViewById(R.id.tvperfil);
        btnmostrarhistorialperfil = findViewById(R.id.btnmostrarhistorialperfil);
        listviewperfil = findViewById(R.id.listviewperfil);

        String fuente = "fonts/Balming.ttf";
        this.script = Typeface.createFromAsset(getAssets(), fuente);
        tvperfil.setTypeface(script);

        btnmostrarhistorialperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.over, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.terminos) {
            Toast.makeText(this, "Terminos", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.info) {
            Toast.makeText(this, "Info", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.cerrar) {
            Toast.makeText(this, "Cerrar sesion", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void historialdieta(View view) {

        Intent intentrecuperar1 = getIntent();

        String string_usuario_recibido1 = intentrecuperar1.getStringExtra("str_usuario");

        Intent is = new Intent(this, HistorialDieta.class);

        is.putExtra("str_usuario", string_usuario_recibido1);

        startActivity(is);
    }

    public void historialperiodo(View view) {

        Intent intentrecuperar2 = getIntent();

        String string_usuario_recibido2 = intentrecuperar2.getStringExtra("str_usuario");

        Intent i = new Intent(this, HistorialPeriodo.class);

        i.putExtra("str_usuario", string_usuario_recibido2);

        startActivity(i);
    }

    public void historialentreno(View view) {

        Intent intentrecuperar4 = getIntent();

        String string_usuario_recibido4 = intentrecuperar4.getStringExtra("str_usuario");

        Intent i = new Intent(this, HistorialEntreno.class);

        i.putExtra("str_usuario", string_usuario_recibido4);

        startActivity(i);
    }

    private void getData() {

        Intent intent = getIntent();

        String string_usuario_recibido3 = intent.getStringExtra("str_usuario");

        String url = Keysperfil.DATA_URL + string_usuario_recibido3;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PantPerfil.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSON(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Keysperfil.JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {

                JSONObject jo = result.getJSONObject(i);
                String mostrarusuario = jo.getString(Keysperfil.KEY_USUARIO);
                String mostraremail = jo.getString(Keysperfil.KEY_EMAIL);
                String mostrarcontra = jo.getString(Keysperfil.KEY_CONTRA);
                String mostraredad = jo.getString(Keysperfil.KEY_EDAD);
                String mostrarpeso = jo.getString(Keysperfil.KEY_PESO);

                final HashMap<String, String> perfil = new HashMap<>();

                perfil.put(Keysperfil.KEY_USUARIO, mostrarusuario);
                perfil.put(Keysperfil.KEY_EMAIL, mostraremail);
                perfil.put(Keysperfil.KEY_CONTRA, mostrarcontra);
                perfil.put(Keysperfil.KEY_EDAD, mostraredad);
                perfil.put(Keysperfil.KEY_PESO, mostrarpeso);

                list.add(perfil);

            }

        } catch (JSONException e) {

            e.printStackTrace();

        }
        ListAdapter adapter = new SimpleAdapter(

                PantPerfil.this, list, R.layout.activity_listaperfil,

                new String[]{Keysperfil.KEY_USUARIO, Keysperfil.KEY_EMAIL, Keysperfil.KEY_CONTRA, Keysperfil.KEY_EDAD, Keysperfil.KEY_PESO},

                new int[]{R.id.mostrarusuario, R.id.mostraremail, R.id.mostrarcontra, R.id.mostraredad, R.id.mostrarpeso});

        listviewperfil.setAdapter(adapter);

    }
    public void cerrarsesion(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
