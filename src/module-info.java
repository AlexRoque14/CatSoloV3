module FlappyV3 {

    requires  javafx.fxml;
    requires  javafx.controls;
    requires  javafx.media;

    opens  sample;
    opens imageScene;
    opens  sample.controller;
}