package com.example.alejandro.practica8pmdmjuego;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class Principal extends Activity {
    private MediaPlayer mp;
    private ImageButton btEmpezar;
    private TextView tv;

    private ProgressDialog dialogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);


        final PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        btEmpezar = (ImageButton)findViewById(R.id.imageButton);
        tv = (TextView)findViewById(R.id.textView2);

        if(leerSharedPreferences()==""){
            tv.setText(0+"");
            guardarSharedPreferences("0");
        }else{
            tv.setText(leerSharedPreferences());
        }


        mp = MediaPlayer.create(this,R.raw.fondo);

        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer arg0) {
                if (powerManager.isScreenOn()){
                    arg0.start();
                }
            }
        });
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.seekTo(0);
                if (powerManager.isScreenOn()) {
                    mp.start();
                }
            }
        });
        Musica.cargarMusica(getApplicationContext());

        btEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Musica.isCompletado()){
                    Intent i = new Intent(Principal.this, Juego.class);
                    startActivityForResult(i, 1);
                }else{
                    tostada(getString(R.string.tostada));
                }
            }
        });
    }


    @Override
    protected void onRestart() {
        Log.v("gestor", "restart");

        super.onRestart();
    }
    @Override
    protected void onResume() {
        Log.v("gestor", "resume");
        if(!mp.isPlaying()){
            mp.start();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mp.release();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.v("gestor", "pause");
        if(mp.isPlaying()){
            mp.pause();
        }
        super.onPause();
    }
    private void tostada(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK){
            int puntuacionNueva = data.getIntExtra("puntuacion",0);
            Log.v("pun",puntuacionNueva+"");
           if(puntuacionNueva>Integer.parseInt(leerSharedPreferences())) {
                guardarSharedPreferences(puntuacionNueva+"");
            }
            tv.setText(leerSharedPreferences());
            mp.start();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void guardarSharedPreferences(String punt) {
        SharedPreferences pc;
        SharedPreferences.Editor ed;
        pc = getSharedPreferences("preferencia", MODE_PRIVATE);
        ed = pc.edit();
        ed.putString("puntuacion", punt);
        ed.apply();
    }
    private String leerSharedPreferences() {
        SharedPreferences pc;
        pc = getSharedPreferences("preferencia", MODE_PRIVATE);
        return pc.getString("puntuacion","");
    }


}
