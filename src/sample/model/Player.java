package sample.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import sample.Main;
import sample.controller.Controller;

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
            this.ancho = (int) Controller.imagenes.get(nombreImagen).getWidth();
            this.alto = (int) Controller.imagenes.get(nombreImagen).getHeight();

        }


        public int getVidas() {
            return vidas;
        }

        public void setNombreImagen(String nombreImagen) {
            this.nombreImagen = nombreImagen;
        }


        public void setVelocidad(int velocidad) {
            this.velocidad = velocidad;
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
            graficos.drawImage(Controller.imagenes.get(nombreImagen), moveX , moveY);
            graficos.rect(moveX , moveY , direccion*ancho , alto);
        }


        public void mover(){

            if(Controller.rigth){
                if(moveX >= 600){
                    moveX = 600;
                }else{
                    moveX += velocidad;
                }
            }
            if(Controller.left){
                if(moveX == 20){
                    moveX = 20;
                }else{
                    moveX -=velocidad;
                }

            }
            if(Controller.down){
                moveY +=velocidad;
            }
            if(Controller.up){
                moveY -=velocidad;
            }
        }


        /** verificar para hilos **/
        public void verificarColision(Obstaculo item){
            if (!item.isCaptura() && this.obtenerRectangulo().getBoundsInLocal().intersects(item.obtenerRectangulo().getBoundsInLocal())){
                this.vidas += item.getCantidad_vidas();
                item.setCaptura(true);
                Controller.animation.stop();
            }
        }
}
