package com.example.reproductormusicamates;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SelectionController {

    @FXML
    private ListView<String> listViewSongs;

    private Map<String, String> cancionesMap = new HashMap<>();
    private MediaPlayer mediaPlayer;
    private static SelectionController instance;

    @FXML
    private void initialize() {
        instance = this;
        URL resource = getClass().getResource("/audio");
        if (resource == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Directorio de música no encontrado.");
            alert.showAndWait();
            return;
        }

        try {
            File songsDir = new File(resource.toURI());
            if (songsDir.exists() && songsDir.isDirectory()) {
                Arrays.stream(songsDir.listFiles())
                        .filter(file -> file.getName().matches("(?i).*\\.mp3"))
                        .forEach(file -> {
                            String fileName = file.getName();
                            String filePath = file.toURI().toString();
                            cancionesMap.put(fileName, filePath);
                            listViewSongs.getItems().add(fileName);
                        });
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static SelectionController getInstance() {
        return instance;
    }

    @FXML
    private void handleSeleccionar(ActionEvent event) {
        String selectedSong = listViewSongs.getSelectionModel().getSelectedItem();
        if (selectedSong == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecciona una canción primero.");
            alert.showAndWait();
            return;
        }

        String fullPath = cancionesMap.get(selectedSong);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/reproductormusicamates/challenge.fxml"));
            Parent root = loader.load();
            ChallengeController challengeController = loader.getController();
            challengeController.setSongPath(fullPath);

            Stage stage = new Stage();
            stage.setTitle("Desafío Matemático");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reproducirMusica(String songPath) {
        if (songPath == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No se ha seleccionado ninguna canción.");
            alert.showAndWait();
            return;
        }
        Media media = new Media(songPath);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    @FXML
    private void handleDetener(ActionEvent event) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }
}
