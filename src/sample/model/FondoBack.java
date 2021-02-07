package sample.model;

import javafx.scene.canvas.GraphicsContext;
import sample.controller.Controller;

public class FondoBack {

    private int moveX;
    private int moveX2;
    private int moveY;
    private String nombreImagen;
    private String nombreImagen2;
    private int velocidad = 5;
    private int ancho;
    private int alto;

    public FondoBack(int moveX, int moveY, String nombreImagen, String nombreImagen2, int velocidad) {
        this.moveX = moveX;
        this.moveY = moveY;
        this.nombreImagen = nombreImagen;
        this.nombreImagen2 = nombreImagen2;
        this.velocidad = velocidad;
        this.ancho =(int) Controller.imagenes.get(nombreImagen).getWidth();
        this.alto = (int) Controller.imagenes.get(nombreImagen2).getHeight();
        this.moveX2 = ancho + moveX;
    }

    public void pintar(GraphicsContext graficos){
        graficos.drawImage(Controller.imagenes.get(nombreImagen), moveX , moveY);
        graficos.drawImage(Controller.imagenes.get(nombreImagen2), moveX2 , moveY);
    }

    public void mover(){
        if(moveX <= -1*ancho){
            moveX = ancho;
        }
        if(moveX2 <= -1*ancho){
            moveX2 = ancho;
        }

        moveX -= velocidad;
        moveX2-= velocidad;

    }


}
