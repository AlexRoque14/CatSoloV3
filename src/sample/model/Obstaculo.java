package sample.model;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.controller.Controller;

import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Obstaculo extends Observable implements  Runnable  {

    private int x;
    private int y;
    private char id;
    private int alto;
    private int ancho;
    private int puntos;
    private int velocidad;
    private int anchoPintar;
    private int altoPintar;
    private String nombreImagen;
    private boolean captura = false;
    public static boolean status = true;


    private static Semaphore mutex = new Semaphore(1);


    public Obstaculo(char id , Observer objeto) {
        this.id = id;
        addObserver(objeto);
    }

    public Obstaculo( int puntos, int x, int y, int velocidad, String nombreImagen , int alto , int ancho , int altoPintar) {
        this.puntos = puntos;
        this.x = x;
        this.y = y;
        this.alto = alto;
        this.ancho = ancho;
        this.anchoPintar = 90;
        this.velocidad = velocidad;
        this.altoPintar = altoPintar;
        this.nombreImagen = nombreImagen;
    }


    public void setCaptura(boolean captura) {
        this.captura = captura;
    }


    public void setStatus(boolean status) {
        this.status = status;
    }

    public Rectangle obtenerRectangulo(){
        return new Rectangle(x , y , ancho , alto);
    }

    public int getPuntos() {
        return puntos;
    }

    public boolean isCaptura() {
        return captura;
    }


    public void pintar(GraphicsContext graficos){
        if(this.captura){
            //graficos.drawImage(Controller.imagenes.get(nombreImagen) , x , y , anchoPintar , altoPintar);
            graficos.drawImage(Controller.imagenes.get("over"), 180 , 150);
            return;
        }else{
             graficos.drawImage(Controller.imagenes.get(nombreImagen) , x , y , anchoPintar , altoPintar);
             graficos.setFill(Color.RED);
             graficos.rect(x , y , ancho , alto-10);
        }
    }

    public void mover() {
        x -= velocidad;
    }


    @Override
    public void run() {
        while (status){
            switch (this.id){
                case '1' :
                    try {
                        mutex.acquire();
                        setChanged();
                        notifyObservers(String.valueOf(id));
                        mutex.release();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    break;
                case '2' :
                    try {
                        mutex.acquire();
                        setChanged();
                        notifyObservers(String.valueOf(id));
                        System.out.println();
                        mutex.release();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    break;
                case '3' :
                    try {
                        mutex.acquire();
                        setChanged();
                        notifyObservers(String.valueOf(id));
                        System.out.println("");
                        mutex.release();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    break;
                default:
            }
            try {
                Thread.sleep(ThreadLocalRandom.current().nextLong(7000) + 200);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}