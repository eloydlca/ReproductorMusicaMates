module com.example.reproductormusicamates {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.example.reproductormusicamates to javafx.fxml;
    exports com.example.reproductormusicamates;
}