package com.example.ficheros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Ejer2 extends AppCompatActivity {

    private Spinner spnProvincias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejer2);
        spnProvincias = findViewById(R.id.spnProvincias);
        leerRecurso();

    }


    public void leerRecurso() {
        try {
            InputStream fraw = getResources().openRawResource(R.raw.ejer2);
            BufferedReader brin = new BufferedReader(new InputStreamReader(fraw));

            String texto="";
            String linea= brin.readLine();
            List<String> provs = new ArrayList<String>();
            while (linea!=null){
                provs.add(linea);
                linea=brin.readLine();
            }
            spnProvincias.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,provs));
            fraw.close();
        }catch (Exception ex) {
            Log.e ("Ficheros", "Error al leer fichero desde recurso raw");}
    }
}