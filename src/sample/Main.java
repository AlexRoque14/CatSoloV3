package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import sample.controller.Controller;

import java.io.File;


public class Main extends Application {
    public static  AudioClip audio;



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/sample.fxml"));
        primaryStage.setTitle("CAT SOLO");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        initializeSound();
    }


    public void initializeSound(){
        String musicFile = "src/sound/menu.mp3";
        audio = new AudioClip(new File(musicFile).toURI().toString());
        audio.play();
    }

}


