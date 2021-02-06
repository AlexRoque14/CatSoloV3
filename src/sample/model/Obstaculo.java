package sample.model;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.Main;

public class Obstaculo {

    private int cantidad_vidas;
    private int x;
    private int y;
    private int velocidad;
    private String nombreImagen;
    private int alto;
    private int ancho;
    private boolean captura = false;

    public Obstaculo(int cantidad_vidas, int x, int y, int velocidad, String nombreImagen) {
        this.cantidad_vidas = cantidad_vidas;
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.nombreImagen = nombreImagen;
        this.ancho = (int) Main.imagenes.get(nombreImagen).getWidth();
        this.alto = (int) Main.imagenes.get(nombreImagen).getHeight();
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
            return;
        }else{
            graficos.drawImage(Main.imagenes.get(nombreImagen) , x , y);
            graficos.rect(x , y , ancho , alto);
        }
    }

    public void mover(){
        x -= velocidad;
    }

}