package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import sample.model.FondoBack;
import sample.model.Obstaculo;
import sample.model.Player;

import java.util.HashMap;


public class Main extends Application {
    private GraphicsContext graficos;
    private Group root;
    private Scene scene;
    private Canvas lienzo;
    //private  Scene scenita;

    private int move_x = 0;
    private int move_y = 200;

    public static boolean up;
    public static boolean down;
    public static boolean rigth;
    public static boolean left;

    private Player player;
    private FondoBack back;
    private FondoBack back2;
    private Obstaculo obstaculo;
    private Obstaculo obstaculo2;

    public static HashMap<String, Image> imagenes;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        initializeComponents();
        eventoBotones();
        primaryStage.setScene(scene);
        primaryStage.setTitle("CAT SOLO");
        primaryStage.show();
        cicloJuego();
    }

    public void cicloJuego(){
        long init_tiempo = System.nanoTime();
        AnimationTimer animation = new AnimationTimer() {
            @Override
            public void handle(long actually_time) {
                double t = (actually_time - init_tiempo) / 1000000000.0;   //60 veces x 1 seg
                //System.out.println("Tiempo: " + t);
                updateStatus();
                pintar();
            }
        };
        animation.start();
    }


    public void updateStatus(){
        player.mover();
        back.mover();
        obstaculo.mover();
        obstaculo2.mover();
        player.verificarColision(obstaculo);
        player.verificarColision(obstaculo2);
    }


    public void initializeComponents(){

        imagenes = new HashMap<String, Image>();
        uploadImages();
        player = new Player(move_x , move_y , 0 , "cat");
        back = new FondoBack(0 , 0 , "fondo" , 3);
        back2 = new FondoBack(0, 0 , "fondo", 3);
        obstaculo = new Obstaculo(1, 500, 295, 2 , "item1");
        obstaculo2 = new Obstaculo(1, 500, 0, 2 , "item2");


        root = new Group();
        scene = new Scene(root, 700 , 500);
        lienzo = new Canvas(700, 500);
        root.getChildren().add(lienzo);
        graficos = lienzo.getGraphicsContext2D();

    }

    public void uploadImages(){
        imagenes.put("cat", new Image("cat.png"));
        imagenes.put("pikachu", new Image("pikachu.png"));
        imagenes.put("fondo", new Image("fondo.jpg"));
        imagenes.put("tilemap", new Image("tilemap.png"));
        imagenes.put("item1", new Image("item1.png"));
        imagenes.put("item2", new Image("item2.png"));
    }

    public void pintar(){
        back.pintar(graficos);
        player.pintar(graficos);
        obstaculo.pintar(graficos);
        obstaculo2.pintar(graficos);
        graficos.fillText("Vidas: " + player.getVidas(), 20 , 20);
    }


    public void eventoBotones(){
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode().toString()){
                    case "RIGHT":
                        rigth = true;
                        break;
                    case "LEFT":
                        left = true;
                        break;
                    case "UP":
                        up = true;
                        break;
                    case "DOWN":
                        down = true;
                        break;
                    case "SPACE":
                        player.setVelocidad(10);
                        player.setNombreImagen("pikachu");
                        break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode().toString()){
                    case "RIGHT":
                        rigth = false;;
                        player.setDireccion(1);
                        break;
                    case "LEFT":
                        left = false;
                        player.setDireccion(-1);
                        break;
                    case "UP":
                        up = false;
                        break;
                    case "DOWN":
                        down = false;
                        break;
                    case "SPACE":
                        player.setVelocidad(5);
                        player.setNombreImagen("cat");
                        break;
                }
            }
        });
    }
}


