package com.example.alejandro.practica8pmdmjuego;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class Juego extends Activity {
    private VistaJuego vjuego;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vjuego = new VistaJuego(this);

        mp = MediaPlayer.create(this, R.raw.fondo2);

        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer arg0) {
                arg0.start();
            }
        });
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.seekTo(0);
                mp.start();
            }
        });
        setContentView(vjuego);
    }
    @Override
    public void onBackPressed() {
        mp.release();
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        vjuego.temporizador.cancel();
        mp.release();
        super.onPause();

    }


}
