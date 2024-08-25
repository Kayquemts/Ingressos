package com.example.sistemaingressos.telas;

import com.example.sistemaingressos.database.ClienteDAO;
import com.example.sistemaingressos.models.ClienteModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static com.example.sistemaingressos.telas.DadosClienteController.cliente;

public class LoginController {

    @FXML private TextField cpf;
    @FXML private PasswordField pass;




    public void initialize(){
        cliente= null;
    }

    @FXML
    public void entrar(ActionEvent event) {
        String cpf_cliente = cpf.getText();
        String pass_cliente = pass.getText();

        if (cpf_cliente == null || cpf_cliente.isEmpty() || pass_cliente == null || pass_cliente.isEmpty()) {
            System.out.println("Por favor, preencha o CPF e a senha.");
            return;
        }

        try {

            cliente = ClienteDAO.getClienteByCpfAndSenha(cpf_cliente, BCrypt.hashpw(pass_cliente, "$2a$10$C5oM1KJbL/3iq4U8y1x8me"));

            if (cliente != null) {
                System.out.println(cliente);
                // Atualize a cena com a nova tela
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ListaSessoesTela.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));


            } else {
                System.out.println("CPF ou senha incorretos. Tente novamente.");

            }
        } catch (LoadException ld){
            ld.printStackTrace();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao tentar realizar o login. Tente novamente mais tarde.");
            e.printStackTrace();
        }
    }


    @FXML
    public void cadastro(ActionEvent event) {
        try {
            Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
            scene.setRoot(new FXMLLoader(getClass().getResource("DadosClienteTela.fxml")).load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
