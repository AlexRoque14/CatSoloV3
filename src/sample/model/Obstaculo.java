package sample.model;


import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import sample.Main;
import sample.controller.Controller;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Obstaculo extends Observable implements  Runnable  {

    private int cantidad_vidas;
    private int x;
    private int y;
    private int velocidad;
    private String nombreImagen;
    private int alto;
    private int ancho;
    private boolean captura = false;
    private  static Semaphore mutex = new Semaphore(1);
    private char id;

    public Obstaculo(char id , Observer objeto) {
        this.id = id;
        addObserver(objeto);
    }

    public Obstaculo(int cantidad_vidas, int x, int y, int velocidad, String nombreImagen) {
        this.cantidad_vidas = cantidad_vidas;
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.nombreImagen = nombreImagen;
        this.ancho = (int) Controller.imagenes.get(nombreImagen).getWidth();
        this.alto = (int) Controller.imagenes.get(nombreImagen).getHeight();
    }


    public int getCantidad_vidas() {

        return cantidad_vidas;
    }

    public void setCantidad_vidas(int cantidad_vidas) {
        this.cantidad_vidas = cantidad_vidas;
    }

    public boolean isCaptura() {
        return captura;
    }

    public void setCaptura(boolean captura) {
        this.captura = captura;
    }

    public Rectangle obtenerRectangulo(){
        return new Rectangle(x , y , ancho , alto);
    }


    public void pintar(GraphicsContext graficos){
        if(this.captura){
            graficos.drawImage(Controller.imagenes.get(nombreImagen) , x , y);
            graficos.drawImage(Controller.imagenes.get("over"), 180 , 150);
            return;
        }else{
             graficos.drawImage(Controller.imagenes.get(nombreImagen) , x , y);
             graficos.rect(x , y , ancho , alto);
        }
    }


    public void mover() {
        x -= velocidad;
    }


    @Override
    public void run() {
        System.out.println("Run: " + nombreImagen);
        while (true){
            switch (this.id){
                case '1' :
                    try {
                        mutex.acquire();
                        System.out.println("obstaculo 1: " + Thread.currentThread().getName());
                        setChanged();
                        notifyObservers(String.valueOf(id));
                        mutex.release();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    break;

                case '2':
                    try {
                        mutex.acquire();
                        System.out.println("Obstaculo 2: "+ Thread.currentThread().getName());
                        setChanged();
                        notifyObservers(String.valueOf(id));
                        mutex.release();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                    break;
                default:
            }

            try {
                Thread.sleep(ThreadLocalRandom.current().nextLong(3000) + 200);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }
}