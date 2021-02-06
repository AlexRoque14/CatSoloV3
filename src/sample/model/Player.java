package sample.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.Main;

public class Player {

        private int moveX;
        private int moveY;
        private int vidas;
        private String nombreImagen;
        private int velocidad = 5;
        private int direccion = 1;
        private int alto;
        private int ancho;


        public Player(int moveX, int moveY, int vidas, String nombreImagen) {
            this.moveX = moveX;
            this.moveY = moveY;
            this.vidas = vidas;
            this.nombreImagen = nombreImagen;
            this.ancho = (int) Main.imagenes.get(nombreImagen).getWidth();
            this.alto = (int) Main.imagenes.get(nombreImagen).getHeight();

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

        public int getVidas() {
            return vidas;
        }

        public void setVidas(int vidas) {
            this.vidas = vidas;
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

    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

    public Rectangle obtenerRectangulo(){
            return new Rectangle(moveX , moveY , direccion*ancho , alto);
    }

    public void pintar(GraphicsContext graficos){
            if(direccion == -1){
                moveX = ancho;
            }
            graficos.drawImage(Main.imagenes.get(nombreImagen), moveX , moveY);
            graficos.rect(moveX , moveY , direccion*ancho , alto);
        }


        public void mover(){
            if(moveX > 700){
                moveX = -80;
            }
            if(Main.rigth){
                moveX += velocidad;
            }
            if(Main.left){
                moveX -=velocidad;
            }
            if(Main.down){
                moveY +=velocidad;
            }
            if(Main.up){
                moveY -=velocidad;
            }
        }


        public void verificarColision(Obstaculo item){
            if (!item.isCaptura() && this.obtenerRectangulo().getBoundsInLocal().intersects(item.obtenerRectangulo().getBoundsInLocal())){
                this.vidas += item.getCantidad_vidas();
                item.setCaptura(true);

            }
        }
}
