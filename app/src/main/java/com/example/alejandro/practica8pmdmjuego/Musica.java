package com.example.alejandro.practica8pmdmjuego;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Created by Alejandro on 27/02/2015.
 */
public class Musica {
    static SoundPool sp;
    static int cancionReloj, cancionDisparo,cancionPajaro;
    static boolean completado=false;

    static void cargarMusica(Context context) {
        sp = new SoundPool(40, AudioManager.STREAM_MUSIC, 0);

        cancionReloj=sp.load(context, R.raw.reloj, 0);
        cancionDisparo=sp.load(context, R.raw.disparo, 0);
        cancionPajaro=sp.load(context, R.raw.pajaro, 0);


        sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                completado = true;
            }
        });

    }
    static void cReloj(){
        sp.play(cancionReloj,1,1,1,0,1);
    }
    static void cDisparo(){
        sp.play(cancionDisparo,0.2f,0.2f,1,0,1);
    }
    static void cPajaro(){
        sp.play(cancionPajaro,3,3,1,0,1);
    }

    public static boolean isCompletado() {
        return completado;
    }

    public static void setCompletado(boolean completado) {
        Musica.completado = completado;
    }

}
