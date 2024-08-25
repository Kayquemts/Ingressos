package com.example.sistemaingressos.telas;

import com.example.sistemaingressos.models.FilmeModel;
import com.example.sistemaingressos.models.SessaoModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.sql.SQLException;

public class MainController {
    @FXML
    protected void logarCliente(ActionEvent event) throws SQLException {
        /*
        try {
            Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
            scene.setRoot(new FXMLLoader(getClass().getResource("DadosClienteTela.fxml")).load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/


        try{
            Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
            scene.setRoot(new FXMLLoader(getClass().getResource("Login.fxml")).load());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    protected void logarAdmin(ActionEvent event) {
        try {
            Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
            scene.setRoot(new FXMLLoader(getClass().getResource("AdminTela.fxml")).load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}