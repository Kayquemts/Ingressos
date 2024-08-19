package com.example.sistemaingressos;

import com.example.sistemaingressos.database.VendaDAO;
import com.example.sistemaingressos.models.FilmeModel;
import com.example.sistemaingressos.models.SalaModel;
import com.example.sistemaingressos.models.SessaoModel;
import com.example.sistemaingressos.models.VendaModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        SalaModel.carregarSalas();
        FilmeModel.carregarFilmes();
        SessaoModel.carregarSessoes();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainTela.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1110, 630);
        stage.setTitle("Sistema de ingressos");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}