package com.example.pertime;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {
    EditText usuario, contra;
    Button btniniciar, btnregis;

    String str_usuario, str_contra;

    String url= "https://rogdomain.ddns.net:8860/yourtime/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contra = findViewById(R.id.contra);
        usuario = findViewById(R.id.nombre);

        btnregis=findViewById(R.id.btnregis);
        btniniciar=findViewById(R.id.btniniciar);

    }

    public void registro (View view){
        Intent i =  new Intent(this, NRegistro.class);
        startActivity(i);
    }
    public void iniciar (View view){
        String verificar_correo = usuario.getText().toString();
        String verificar_contraseña = contra.getText().toString();

        if(verificar_correo.isEmpty()){
            usuario.setError("Ingrese un correo electronico");
        }
        else if(verificar_contraseña.isEmpty()){
            contra.setError("Ingrese una contraseña");
        }
        else{

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Por Favor espere..");

            progressDialog.show();

            str_usuario = usuario.getText().toString().trim();
            str_contra = contra.getText().toString().trim();


            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();



                    if(response.equalsIgnoreCase("Sesion iniciada")){

                        //startActivity(new Intent(getApplicationContext(),PantInicial.class));
                        usuario.setText("");
                        contra.setText("");
                        Intent intent = new Intent(getApplicationContext(), PantInicial.class);

                        intent.putExtra("str_usuario", str_usuario);

                        startActivity(intent);
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    }

                    else{
                        usuario.setError("Prueba de nuevo");
                    }
                }
            },new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();

                    params.put("usuario",str_usuario);
                    params.put("contra",str_contra);
                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(request);

        }

    }

}







