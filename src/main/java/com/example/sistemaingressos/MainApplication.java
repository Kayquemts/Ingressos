package com.example.sistemaingressos;

import com.example.sistemaingressos.models.*;
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
        System.out.println("Salas Carregadas");

        FilmeModel.carregarFilmes();
        System.out.println("Filmes Carregadas");

        SessaoModel.carregarSessoes();
        System.out.println("Sessoes Carregadas");

        ComboModel.carregarCombo();
        System.out.println("Combo Carregados");

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