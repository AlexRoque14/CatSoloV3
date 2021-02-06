package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class Controller {
    @FXML
    private ImageView fondo;

    @FXML
    private ImageView Cat;

    @FXML
    private ImageView BtnIniciar;

    @FXML
    private Text Tiitulo;

    @FXML
    void onMouseClikedIniciar(MouseEvent event) {
        System.out.println("holaMundo");
    }
}
