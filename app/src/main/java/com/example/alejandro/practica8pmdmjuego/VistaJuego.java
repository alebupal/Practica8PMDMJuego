package com.example.alejandro.practica8pmdmjuego;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.VelocityTracker;

import java.util.Timer;
import java.util.TimerTask;


public class VistaJuego extends SurfaceView implements SurfaceHolder.Callback {

    private Bitmap bmp;
    private int alto, ancho;
    private HebraJuego hebraJuego;

    private Juego j = null;

    private Sprite[] sprites;
    private Drawable fondo;
    Timer temporizador;
    int puntuacion = 0;
    private int segundos = 31;

    Paint paint = new Paint();

    private  SoundPool sp;
    private int cancionReloj, cancionDisparo,cancionPajaro;
    private int velocidadx=20,velocidady=10;




    /**********************************************************************************************/
    /*    constructor                                                                             */

    /**
     * ******************************************************************************************
     */

    public VistaJuego(Context contexto) {
        super(contexto);
        this.j =(Juego) contexto;

        getHolder().addCallback(this);
        fondo = this.getResources().getDrawable(R.drawable.fondo);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.sprite3);

        paint.setColor(Color.BLACK);
        paint.setTextSize(50);

        sprites = new Sprite[5];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = new Sprite(this, bmp);
        }

        hebraJuego = new HebraJuego(this);
        temporizador = new Timer();

        temporizador.schedule(new TimerTask() {
            @Override
            public void run() {
                segundos--;
                if(segundos==4){
                    Musica.cReloj();
                }
                if(segundos == 0){
                    this.cancel();
                    j.mp.release();
                    Intent i = new Intent();
                    i.putExtra("puntuacion",puntuacion);
                    j.setResult(Activity.RESULT_OK, i);
                    j.finish();
                }
            }
        }, segundos,1000);


    }

    /**********************************************************************************************/
    /*    metodo de la clase surfaceview                                                          */

    /**
     * ******************************************************************************************
     */

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        Rect imageBounds = canvas.getClipBounds();  // Adjust this for where you want it
        fondo.setBounds(canvas.getClipBounds());
        fondo.draw(canvas);
        canvas.drawText("PUNTUACIÓN: "+ puntuacion+ " TIEMPO: "+segundos, 10, 50, paint);

        for (int i = 0; i < sprites.length; i++) {
            sprites[i].dibujar(canvas);
        }
        for (int i = 0; i < sprites.length; i++) {
           /* for (int j = 0; j < i - 1; j++) {
               if (sprites[i].colisiona(sprites[j], 10)) {
                    sprites[i].alteraDireccion(sprites[j]);
               }
            }*/
        }

    }

    /**********************************************************************************************/
    /*    interfaz callback                                                                       */

    /**
     * ******************************************************************************************
     */

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try{
            hebraJuego.setFuncionando(true);
            hebraJuego.start();
        }catch(IllegalThreadStateException e){
            j.finish();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        alto = height;
        ancho = width;


        for (int i = 0; i < sprites.length; i++) {
            sprites[i].setDimension(ancho, alto);
            sprites[i].setPosicionAleatorio();
           /* for (int j = 0; j < i - 1; j++) {
                while (sprites[i].colisiona(sprites[j], 10)) {
                    sprites[i].setPosicionAleatorio();
                }
            }*/
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean reintentar = true;
        hebraJuego.setFuncionando(false);
        while (reintentar) {
            try {
                hebraJuego.join();
                reintentar = false;
            } catch (InterruptedException e) {
            }
        }
    }

    private long ultimoClick = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Musica.cDisparo();
        if (System.currentTimeMillis() - ultimoClick > 300) {
            ultimoClick = System.currentTimeMillis();
            float x, y;
            x = event.getX();
            y = event.getY();
            synchronized (getHolder()) {
                for (int i = 0; i < sprites.length; i++) {
                    if (sprites[i].tocado(x, y)) {
                        Log.v("tocado","tocado");
                        Musica.cPajaro();
                        sprites[i].setVelocidadx(velocidadx + 2);
                        sprites[i].setVelocidady(velocidady + 1);
                        velocidadx=sprites[i].getVelocidadx();
                        velocidady=sprites[i].getVelocidady();
                        sprites[i].setPosicionAleatorio();
                        puntuacion++;
                        break;
                    }
                }
            }
        }
        return true;
    }





}


