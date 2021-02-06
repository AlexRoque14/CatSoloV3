package sample.model;

import javafx.scene.canvas.GraphicsContext;
import sample.Main;

public class FondoBack {

    private int moveX;
    private int moveX2;
    private int moveY;
    private String nombreImagen;
    private String nombreImagen2;
    private int velocidad = 5;
    private int ancho;
    private int alto;

    public FondoBack(int moveX, int moveY, String nombreImagen, int velocidad) {
        this.moveX = moveX;
        this.moveY = moveY;
        this.nombreImagen = nombreImagen;
        this.nombreImagen2 = nombreImagen;
        this.velocidad = velocidad;
        this.ancho =(int) Main.imagenes.get("fondo").getWidth();
        this.alto = (int) Main.imagenes.get("fondo").getHeight();
        this.moveX2 = ancho + moveX;
    }

    public int getMoveX() {
        return moveX;
    }

    public void setMoveX(int moveX) {
        this.moveX = moveX;
    }

    public int getMoveY() {
        return moveY;
    }

    public void setMoveY(int moveY) {
        this.moveY = moveY;
    }

    public String getNombreImagen() {
        return nombreImagen;
    }

    public void setNombreImagen(String nombreImagen) {
        this.nombreImagen = nombreImagen;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public void pintar(GraphicsContext graficos){
        graficos.drawImage(Main.imagenes.get(nombreImagen), moveX , moveY);
        graficos.drawImage(Main.imagenes.get(nombreImagen2), moveX2 , moveY);
    }

    public void mover(){
        if(moveX <= -1*ancho){
            moveX = ancho;
        }

        if(moveX2 <= -1*ancho){
            moveX2 = ancho;
        }

        if(Main.rigth){
            moveX -= velocidad;
            moveX2-= velocidad;
        }
        if(Main.left){
            moveX += velocidad;
            moveX2+= velocidad;
        }
    }


}
