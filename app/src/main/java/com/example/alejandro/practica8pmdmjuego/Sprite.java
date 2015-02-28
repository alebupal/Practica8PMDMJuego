package com.example.alejandro.practica8pmdmjuego;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;


public class Sprite {

    private int ejeY = 0;
    private int direccionY;
    private int ejeX = 0;
    private int direccionX;
    private int alto, ancho;
    private static int anchoMax=0, altoMax=0;
    private Bitmap bmp;
    private int frameActual=0;
    private static final int COLUMNAS = 8;
    private static final int FILAS = 2;
    private int velocidadx=20,velocidady=10;


    public Sprite(VistaJuego vista, Bitmap bmp) {
        this.bmp = bmp;
        this.alto=bmp.getHeight()/FILAS;
        this.ancho=bmp.getWidth()/COLUMNAS;
        setMovimiento();
        setPosicion();
    }

    private void movimiento(){
        frameActual = ++frameActual % COLUMNAS;
        if (ejeX > anchoMax - ancho - direccionX ||
                ejeX + direccionX < 0) {
            direccionX = -direccionX;
        }
        ejeX = ejeX + direccionX;
        if (ejeY > altoMax - alto - direccionY ||
                ejeY + direccionY < 0) {
            direccionY = -direccionY;
        }
        ejeY = ejeY + direccionY;
    }

    public void dibujar(Canvas canvas) {
        movimiento();
        int origenx = frameActual * ancho;
        int origeny = 0;
        if(direccionX<0)
            origeny=alto;
        else
            origeny=0;
        Rect origen = new Rect(origenx, origeny, origenx + ancho, origeny +
                alto);
        Rect destino = new Rect(ejeX, ejeY, ejeX + ancho, ejeY + alto);
        canvas.drawBitmap(bmp, origen, destino, null);
    }

    public static void setDimension(int ancho, int alto){
        anchoMax=ancho;
        altoMax=alto;
    }

    public void setPosicion(){
        ejeX = 0;
        ejeY = 0;
    }

    public void setPosicionAleatorio(){
        Random rnd = new Random();
        ejeX = ancho + rnd.nextInt(anchoMax  - ancho*2);
        ejeY = alto + rnd.nextInt(altoMax- alto*2) ;
        setMovimiento();
    }

    public void setMovimiento(){
        Random rnd = new Random();
        direccionX=rnd.nextInt(12)-velocidadx;
        if(direccionX==0)
            direccionX=2;
        direccionY=rnd.nextInt(12)-velocidady;
        if(direccionY==0)
            direccionY=2;

    }


    public boolean tocado(float x, float y){
        return x > ejeX && x < ejeX + ancho && y > ejeY && y < ejeY + alto;
    }

    public int getVelocidadx() {
        return velocidadx;
    }

    public void setVelocidadx(int velocidadx) {
        this.velocidadx = velocidadx;
    }

    public int getVelocidady() {
        return velocidady;
    }

    public void setVelocidady(int velocidady) {
        this.velocidady = velocidady;
    }
}
