module com.example.sistemaingressos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens com.example.sistemaingressos to javafx.fxml;
    requires jbcrypt;
    exports com.example.sistemaingressos;
    exports com.example.sistemaingressos.models;
    opens com.example.sistemaingressos.models to javafx.fxml;
    exports com.example.sistemaingressos.telas;
    opens com.example.sistemaingressos.telas to javafx.fxml;
}