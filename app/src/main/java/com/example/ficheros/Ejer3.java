package com.example.ficheros;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Ejer3 extends AppCompatActivity {

    private ListView lvWebs;
    private List<WebFavorita> provs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejer3);
        lvWebs = findViewById(R.id.lvPaginas);
        leerRecurso();
    }



    class AdaptadorTitulares extends ArrayAdapter<WebFavorita> {
        public AdaptadorTitulares(@NonNull Context context, WebFavorita[] datos) {
            super(context, R.layout.webs_favoritas_view, datos);
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.webs_favoritas_view, null);
            TextView tvId = (TextView)item.findViewById(R.id.id);
            tvId.setText(provs.get(position).getId());
            TextView tvNombre= (TextView)item.findViewById(R.id.nombre);
            tvNombre.setText(provs.get(position).getNombre());
            TextView tvEnlace= (TextView)item.findViewById(R.id.enlace);
            tvEnlace.setText(provs.get(position).getNombre());
            TextView tvLogo= (TextView)item.findViewById(R.id.logo);
            tvLogo.setText(provs.get(position).getNombre());
            return (item);
        }
    }
    public void leerRecurso() {
        try {
            InputStream fraw = getResources().openRawResource(R.raw.ejer3);
            BufferedReader brin = new BufferedReader(new InputStreamReader(fraw));

            String texto="";
            String linea= brin.readLine();
            provs = new ArrayList<WebFavorita>();
            while (linea!=null){
                //parsear las webs
                String [] s = linea.split(";");
                WebFavorita w = new WebFavorita(s[0],s[1],s[2],s[3]);
                provs.add(w);
                linea=brin.readLine();
            }
            fraw.close();
        }catch (Exception ex) {
            Log.e ("Ficheros", "Error al leer fichero desde recurso raw");
        }
    }
}