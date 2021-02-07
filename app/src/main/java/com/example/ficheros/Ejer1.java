package com.example.ficheros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Ejer1 extends AppCompatActivity {



    private Button btnAniadeInt, btnAniadeExt, btnLeeInt, btnLeeExt,btnLeeRec,btnBorraInt,btnBorraExt;
    private EditText etTexto;
    private TextView txtVisionado;
    private static final int SOLICITUD_PERMISO_WRITE_SD = 0;
    private static final int SOLICITUD_PERMISO_READ_SD = 1;

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
                if (comprobarPermisos()){
                    Toast.makeText(Ejer1.this, "Tenemos permisos para escribir",Toast.LENGTH_SHORT).show();
                    if (sdDisponible()) {escribirEnSD();}
                    else Toast.makeText(Ejer1.this, "Tarjeta NO lista para poder escribir",Toast.LENGTH_SHORT).show();
                }else {
                    solicitarPermiso(Manifest.permission.WRITE_EXTERNAL_STORAGE,"Sin este permiso no se puede ESCRIBIR en el dispositivo externo",SOLICITUD_PERMISO_WRITE_SD, Ejer1.this);
                }
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
                if (comprobarPermisos()){
                    Toast.makeText(Ejer1.this, "Tenemos permisos para borrar",Toast.LENGTH_SHORT).show();
                    if (sdDisponible()) {borrarEnSD();}
                    else Toast.makeText(Ejer1.this, "Tarjeta NO lista para poder escribir",Toast.LENGTH_SHORT).show();
                }else {
                    solicitarPermiso(Manifest.permission.WRITE_EXTERNAL_STORAGE,"Sin este permiso no se puede ESCRIBIR en el dispositivo externo",SOLICITUD_PERMISO_WRITE_SD, Ejer1.this);
                }

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
                    try {
                        InputStream fraw = getResources().openRawResource(R.raw.ejer1);
                        BufferedReader brin = new BufferedReader(new InputStreamReader(fraw));

                        String texto="";
                        String linea= brin.readLine();
                        while (linea!=null){
                            texto+=linea;
                           linea=brin.readLine();
                        }
                        txtVisionado.setText(texto);
                        fraw.close();
                    }catch (Exception ex) {
                        Log.e ("Ficheros", "Error al leer fichero desde recurso raw");}
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
                if (comprobarPermisos()){
                    Toast.makeText(Ejer1.this,"Tenemos permisos para leer",Toast.LENGTH_SHORT).show();
                    if (sdDisponible()) {
                        leerDeSD();
                    }
                    else
                        Toast.makeText(Ejer1.this,"Tarjeta NO lista para poder leer",Toast.LENGTH_SHORT).show();
                }else {
                    solicitarPermiso(Manifest.permission.WRITE_EXTERNAL_STORAGE,"Sin este permiso no se puede LEER en el dispositivo externo",SOLICITUD_PERMISO_WRITE_SD, Ejer1.this);
                }
            }
        });
    }
    private static void solicitarPermiso (final String permiso,String justificacion,final int requestCode,final Activity actividad){
        if (ActivityCompat.shouldShowRequestPermissionRationale(actividad, permiso)){
                new AlertDialog.Builder(actividad).setTitle("Solicitud de permiso").setMessage(justificacion).setPositiveButton("OK",new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(actividad,new String[]{permiso},requestCode);
                }
            }).show();
        }else {//Muestra el cuadro de dialogo para la solicitud de permisos y//registra el permiso según respuesta del usuarioç
            ActivityCompat.requestPermissions(actividad,new String[]{permiso}, requestCode);}
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == SOLICITUD_PERMISO_WRITE_SD){
            if (grantResults.length >=1 &&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Log.e("AAAA", "Escribir Memoria SD");
                sdDisponible();
                escribirEnSD();
            }else {
                Toast.makeText(this,"No se puede ESCRIBIR en memoria SD",Toast.LENGTH_LONG ).show();
            }
        }else if (requestCode == SOLICITUD_PERMISO_READ_SD){
            if (grantResults.length == 1 &&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                leerDeSD();
            }else {
                Toast.makeText(this,"No se puede LEER de memoria SD",Toast.LENGTH_LONG ).show();
            }
        }
    }


    private boolean comprobarPermisos (){
        //Comprobamos que tenemos permiso para realizar la operación.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {return true;}else {return false;}
    }
    private boolean sdDisponible (){
        boolean sdDisponible = false;
        boolean sdAccesoEscritura= false;//Comprobamos el estado de la memoria externa
        String estado = Environment.getExternalStorageState();
        Log.i("Memoria", estado);
        if (estado.equals(Environment.MEDIA_MOUNTED)) {
            sdDisponible = true;
            sdAccesoEscritura = true;
        }
        else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {sdDisponible = true;
        sdAccesoEscritura = false;} else {sdDisponible = false;sdAccesoEscritura = false;
        }
        if (sdDisponible)Toast.makeText(getApplicationContext(),"Tengo Tarjeta SD",Toast.LENGTH_SHORT).show();if (sdAccesoEscritura)Toast.makeText(getApplicationContext(),"La tarjeta SD es escribible",Toast.LENGTH_SHORT).show();if (sdDisponible &&sdAccesoEscritura)

        return true;else return false;}
    private void escribirEnSD (){
        try {
            File ruta_sd = Environment.getExternalStorageDirectory();
            File f = new File(ruta_sd.getAbsolutePath(), "prueba_sd.txt");
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(f));
            osw.write(etTexto.getText().toString());
            osw.close();
            Log.i("Ficheros", "fichero escrito correctamente");
        } catch (Exception ex) {
            Log.e("Ficheros", "Error al escribir fichero en tarjeta SD");
        }
    }
    private void borrarEnSD (){
        try {
            File ruta_sd = Environment.getExternalStorageDirectory();
            File f = new File(ruta_sd.getAbsolutePath(), "prueba_sd.txt");
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(f));
            osw.write("");
            osw.close();
            Log.i("Ficheros", "fichero escrito correctamente");
        } catch (Exception ex) {
            Log.e("Ficheros", "Error al escribir fichero en tarjeta SD");
        }
    }
    private void leerDeSD(){
        try {
            File ruta_sd = Environment.getExternalStorageDirectory();
            File f = new File(ruta_sd.getAbsolutePath(), "prueba_sd.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String texto = "";
            String linea = br.readLine();
            while (linea != null) {
                texto = texto + linea + "\n";
                linea = br.readLine();
            }
            br.close();
            Log.i("Ficheros", texto);
            txtVisionado.setText(texto);
        } catch (Exception ex) {
            Log.e("Ficheros", "ERROR!! en la lectura del fichero en SD");
        }
    }














}