package sample.controller;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import sample.Main;
import sample.model.FondoBack;
import sample.model.Obstaculo;
import sample.model.Player;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class Controller implements Observer {

        /*** Scene and JavaFX variables ***/
    private GraphicsContext graficos;
    private Group root;
    private Scene scene;
    private Canvas lienzo;
    private Stage primaryStage;
    private ImageView salir;
    private Button exit;
    public static AudioClip audio;

    /*** Arraylist and Hashmap ***/
    public static AnimationTimer animation;
    public static HashMap<String, Image> imagenes;
    public static ObservableList<Obstaculo> data = FXCollections.observableArrayList();
    public static ArrayList<Obstaculo> listView = new ArrayList<>(data);

    /*** Movement ***/
    private int move_x = 0;
    private int move_y = 200;
    private double timer = 0;
    private int auxiliar = 0;

    /*** Variables buttons keyboard  ***/
    public static boolean up;
    public static boolean down;
    public static boolean rigth;
    public static boolean left;

    /***Instances of classes ***/
    public Player player;
    public FondoBack back;
    public Obstaculo obs;


    @FXML
    void controlsON(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información de Controles");
        alert.setHeaderText(null);
        alert.setContentText("Flecha arriba  --> Ir arriba " +
                "\nFlecha derecha  --> Avanzar" +
                "\nFlecha abajo    --> Ir abajo" +
                "\nFlecha atrás    --> Regresar posición incial" +
                "\nEspacio         --> Acelerar");
        alert.showAndWait();
    }


    @FXML
    void onMouseClikedIniciar(ActionEvent event) {
        Main.audio.stop();

        initializeStage();
    }

    @FXML
    void onMouseClickedExit(MouseEvent event) {
        System.exit(0);
    }




    public void initializeStage(){
        initializeSound();
        initializeComponents();
        initalizeScene();
        eventoBotones();
        salir();
        primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("CAT SOLO");
        primaryStage.show();
        cicloJuego();
    }


    public void initalizeScene(){
        root = new Group();
        scene = new Scene(root, 700 , 500);
        lienzo = new Canvas(700, 500);

        setImage();
        root.getChildren().addAll(lienzo , salir );
        graficos = lienzo.getGraphicsContext2D();
    }

    public void setImage(){
        try{
            FileInputStream input = new FileInputStream("src/exit.png");
            Image image = new Image(input);
            salir = new ImageView(image);
            salir.setX(635);
            salir.setY(440);
            salir.setFitWidth(60);
            salir.setPreserveRatio(true);

            exit = new Button();
            //exit.setMaxSize(400.0 , 400.0);
            exit.setLayoutX(635);
            exit.setLayoutY(440);


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void initializeComponents(){
        imagenes = new HashMap<String, Image>();
        uploadImages();
        player = new Player(move_x , move_y , 3 , "cat");
        back = new FondoBack(0 , 0 , "fondo" , "fondo", 3);
        Obstaculo.status = true;

        /***launch Threads ***/
        new Thread(new Obstaculo('1', this)).start();
        new Thread(new Obstaculo('2', this)).start();
    }


    public  void initializeSound(){
        String musicFile = "src/sound/playing.mp3";
        audio = new AudioClip(new File(musicFile).toURI().toString());
        audio.play();
    }


    public void cicloJuego(){
        long init_tiempo = System.nanoTime();
        animation = new AnimationTimer() {
            @Override
            public void handle(long actually_time) {
                timer = ((actually_time - init_tiempo) / 1000000000.0);   //60 veces x 1 seg
                auxiliar++;
                updateStatus();
                pintar();
            }
        };
        animation.start();
    }

    public void updateStatus(){
        player.mover();
        back.mover();

        Iterator iter = listView.iterator();
        while(iter.hasNext()){
            obs = (Obstaculo) iter.next();
            obs.mover();
            player.verificarColision(obs);
        }
    }

    public void pintar(){
        if(auxiliar == 400){
            back = new FondoBack(0 , 0 , "fondo2" , "fondo2", 3);
        } else if (auxiliar == 1000){
            back = new FondoBack(0 , 0 , "fondo3" , "fondo3", 3);
        }else if(auxiliar == 1600 ){
            back = new FondoBack(0 , 0 , "fondo4" , "fondo4", 3);
        }else if(auxiliar == 2000){
            back = new FondoBack(0 , 0 , "fondo" , "fondo", 3);
            auxiliar = 0;
        }

        back.pintar(graficos);
        player.pintar(graficos);

        Iterator iter = listView.iterator();
        while(iter.hasNext()){
            obs = (Obstaculo) iter.next();
            obs.pintar(graficos);
        }

        graficos.fillText("PUNTUACION: " + player.getVidas(), 20 , 20);
    }

    public void uploadImages(){
        imagenes.put("cat",   new Image("cat.png"));
        imagenes.put("pikachu", new Image("pikachu.png"));
        imagenes.put("fondo", new Image("fondo.jpg"));
        imagenes.put("fondo2", new Image("fondo2.png"));
        imagenes.put("fondo3", new Image("fondo3.jpg"));
        imagenes.put("fondo4", new Image("fondo4.jpg"));
        imagenes.put("item1", new Image("item1.png"));
        imagenes.put("item2", new Image("item2.png"));
        imagenes.put("over", new Image("gameover.png"));
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

    public void salir(){
        salir.setOnMouseClicked(event ->  {
            obs.setCaptura(true);
            obs.setStatus(false);
            listView.clear();
            audio.stop();
            primaryStage.close();
            Main.audio.play();
            obs.setCaptura(false);
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        switch ((String) arg) {
            case "1":
                /*** Item1 = tubo de abajo ***/
                Obstaculo obstacleH1 = new Obstaculo(1, 700, 295, 3 , "item1");
                if(obstacleH1 != null){
                    Platform.runLater(() -> listView.add(obstacleH1));
                }
                break;
            case "2":
                /*** Item2 = tubo de arriba ***/
                Obstaculo obstacleH2 = new Obstaculo(1,700, 0, 3 , "item2");
                if(obstacleH2 != null){
                    Platform.runLater(() -> listView.add(obstacleH2));
                }
                break;
        }
    }
}
