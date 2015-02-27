package com.example.alejandro.practica8pmdmjuego;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

public class Figura {


    private int ejeY = 0;
    private int direccionY;
    private int ejeX = 0;
    private int direccionX;
    private Bitmap bmp;
    private int alto, ancho;
    private static int anchoMax=0, altoMax=0;


    public Figura( Bitmap bmp) {
        this.bmp = bmp;
        this.ancho=bmp.getWidth();
        this.alto=bmp.getHeight();
        setMovimiento();
        setPosicion();
    }

    public static void setDimension(int ancho, int alto){
        anchoMax=ancho;
        altoMax=alto;
    }

    public void setPosicion(int x, int y){
        ejeX = x;
        ejeY = y;
    }

    public void setPosicion(){
        ejeX = 0;
        ejeY = 0;
    }

    public void setPosicionAleatorio(){
        Random rnd = new Random();
        ejeX = ancho + rnd.nextInt(anchoMax  - ancho*2);
        ejeY = alto + rnd.nextInt(altoMax- alto*2) ;
    }

    public void setMovimiento(){
        Random rnd = new Random();
        direccionX=rnd.nextInt(12)-5;
        if(direccionX==0)
            direccionX=2;
        direccionY=rnd.nextInt(12)-5;
        if(direccionY==0)
            direccionY=2;

    }

    public void dibujar(Canvas canvas) {
        movimiento();
        canvas.drawBitmap(bmp, ejeX, ejeY, null);
    }

    private void movimiento(){
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

    public boolean colision(Figura f){
        if(f.ejeX > ejeX + ancho  && f.ejeX < ejeX + ancho -10 && f.ejeY > ejeY +alto && f.ejeY < ejeY + alto-10){
            f.direccionX = f.direccionX *-1;
            f.direccionY = f.direccionY*-1;
            direccionX = direccionX*-1;
            direccionY = direccionY*-1;
        }
        return true;
    }

    public boolean colisiona(Figura f, int delta){
        if(this.ejeX + this.ancho - delta < f.ejeX){
            return false;
        }
        if(this.ejeY + this.alto - delta < f.ejeY){
            return false;
        }
        if(this.ejeX >f.ejeX + f.ancho -delta){
            return false;
        }
        if(this.ejeY >f.ejeY + f.alto -delta){
            return false;
        }
        return true;
    }

    public boolean tocado(float x, float y){
        return x > ejeX && x < ejeX + ancho && y > ejeY && y < ejeY + alto;
    }

    public void eliminar(){
        direccionX=0;
        direccionY=0;
        ejeX=0;
        ejeY=0;
    }

    public void alteraDireccion(Figura f){
        f.direccionX = -f.direccionX ;
        f.direccionY = -f.direccionY;
        this.direccionX = -this.direccionX;
        this.direccionY = -this.direccionY;
    }
}
