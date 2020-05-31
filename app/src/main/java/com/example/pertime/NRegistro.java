package com.example.pertime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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

import java.util.HashMap;
import java.util.Map;

public class NRegistro extends AppCompatActivity {

    TextView tvunete;
    EditText usuario, email, contra, edad, peso;
    Button btnunirse;
    String str_escribe_usuario, str_escribe_email, str_escribe_edad, str_escribe_peso, str_escribe_contra;
    String url = "https://rogdomain.ddns.net:8860/yourtime/register.php";
    private Typeface script;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_registro);

        tvunete=findViewById(R.id.tvunete);
        usuario=findViewById(R.id.nombre);
        email=findViewById(R.id.email);
        contra=findViewById(R.id.contra);
        edad=findViewById(R.id.edad);
        peso=findViewById(R.id.peso);

        String fuente = "fonts/Balming.ttf";
        this.script = Typeface.createFromAsset(getAssets(), fuente);
        tvunete.setTypeface(script);

        btnunirse=findViewById(R.id.btnunirse);

    }

    public void registrar(View view) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Por favor espere...");

        String verificar_nombre = usuario.getText().toString();
        String verificar_email = email.getText().toString();
        String verificar_contra = contra.getText().toString();
        String verificar_edad = edad.getText().toString();
        String verificar_peso = peso.getText().toString();


        if (verificar_nombre.isEmpty()) {
            usuario.setError("Ingrese un nombre de usuario");
        } else if (verificar_email.isEmpty()) {
            email.setError("Ingrese un correo electronico");
        } else if (verificar_contra.isEmpty()) {
            contra.setError("Ingrese su contraseña");
        } else if (verificar_edad.isEmpty()) {
            edad.setError("Ingrese su edad");
        } else if (verificar_peso.isEmpty()) {
            peso.setError("Ingrese su peso");
        } else {

            progressDialog.show();

            str_escribe_usuario = usuario.getText().toString().trim();
            str_escribe_email = email.getText().toString().trim();
            str_escribe_contra = contra.getText().toString().trim();
            str_escribe_edad = edad.getText().toString().trim();
            str_escribe_peso = peso.getText().toString().trim();


            StringRequest request = new StringRequest (Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    usuario.setText("");
                    email.setText("");
                    contra.setText("");
                    edad.setText("");
                    peso.setText("");


                    if (response.equalsIgnoreCase("Usuario registrado")) {

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        Toast.makeText(NRegistro.this, response, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(NRegistro.this, response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(NRegistro.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("usuario", str_escribe_usuario);
                    params.put("email", str_escribe_email);
                    params.put("contra", str_escribe_contra);
                    params.put("edad", str_escribe_edad);
                    params.put("peso", str_escribe_peso);

                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(NRegistro.this);
            requestQueue.add(request);

        }
    }
    }



