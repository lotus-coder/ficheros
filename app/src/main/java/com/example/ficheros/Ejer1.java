package com.example.ficheros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Ejer1 extends AppCompatActivity {



    private Button btnAniadeInt, btnAniadeExt, btnLeeInt, btnLeeExt,btnLeeRec,btnBorraInt,btnBorraExt;
    private EditText etTexto;
    private TextView txtVisionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejer1);
        btnAniadeInt = findViewById(R.id.btnAniadeInt);
        btnAniadeExt = findViewById(R.id.btnAniadeExt);
        btnLeeInt = findViewById(R.id.btnLeeInt);
        btnLeeExt = findViewById(R.id.btnLeeExt);
        btnLeeRec = findViewById(R.id.btnLeeRec);
        btnBorraInt = findViewById(R.id.btnBorraInt);
        btnBorraExt = findViewById(R.id.btnBorraExt);
        etTexto = findViewById(R.id.etContenido);
        txtVisionado = findViewById(R.id.txtVisionado);
        eventos();
    }

    private void eventos() {
        btnAniadeExt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnAniadeInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String s = etTexto.getText().toString();
                    if(!s.equals("")) {
                        OutputStreamWriter osw = new OutputStreamWriter(openFileOutput("prueba_int.txt", Context.MODE_PRIVATE));
                        osw.write(s);
                        osw.close();
                    }else{
                        Toast.makeText(Ejer1.this, "Tienes que escribir algo", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e) {
                    Log.e ("Ficheros", "ERROR!! al escribir fichero enmemoria interna");
                }
            }
        });
        btnBorraExt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnBorraInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    OutputStreamWriter osw = new OutputStreamWriter(openFileOutput("prueba_int.txt", Context.MODE_PRIVATE));
                    osw.write("");
                    osw.close();
                }
                catch (Exception e) {
                    Log.e ("Ficheros", "ERROR!! al escribir fichero enmemoria interna");
                }
            }
        });
        btnLeeRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnLeeInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput("prueba_int.txt")));
                    String texto = fin.readLine();
                    fin.close();
                    Log.i("Ficheros", "Fichero leido!");
                    Log.i("Ficheros", "Texto: " + texto);
                    txtVisionado.setText(texto);
                }catch (Exception ex){
                    Log.e("Ficheros", "Error al leer fichero desde memoria interna");
                }
            }
        });
        btnLeeExt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}