package com.example.reproductormusicamates;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChallengeController implements Initializable {

    @FXML
    private Label lblOperacion;
    @FXML
    private TextField txtRespuesta;

    private int solucion;
    private String songPath;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generarOperacion();
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }

    private void generarOperacion() {
        Random random = new Random();
        int num1 = random.nextInt(100) + 1;
        int num2 = random.nextInt(100) + 1;
        int num3 = random.nextInt(100) + 1;
        solucion = num1 + num2 - num3;
        lblOperacion.setText("¿Cuánto es " + num1 + " + " + num2 + " - " + num3 + "?");
    }

    @FXML
    private void handleReproducir(ActionEvent event) {
        try {
            int respuesta = Integer.parseInt(txtRespuesta.getText());
            if (respuesta == solucion) {
                SelectionController.getInstance().reproducirMusica(songPath);
                Stage stage = (Stage) lblOperacion.getScene().getWindow();
                stage.close();
                txtRespuesta.clear();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Respuesta incorrecta. Inténtalo de nuevo.");
                alert.showAndWait();
            }
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Ingresa un número válido.");
            alert.showAndWait();
        }
    }
}
