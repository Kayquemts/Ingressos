package com.example.sistemaingressos.telas;

import com.example.sistemaingressos.database.ClienteDAO;
import com.example.sistemaingressos.models.ClienteModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;


public class DadosClienteController {
    @FXML
    private TextField nomeCliente;
    @FXML
    private TextField idadeCliente;
    @FXML
    private CheckBox estudante;
    @FXML
    private TextField cpf;

    @FXML
    private PasswordField senha1;
    @FXML
    private PasswordField senha2;

    public static ClienteModel cliente = null;
    private ClienteDAO bd = new ClienteDAO();

    @FXML
    public void initialize() {

    }

    @FXML
    protected void salvarDadosCliente(ActionEvent event) {

        ClienteModel clienteExistente = bd.getClienteByCpf(cpf.getText());

        if(cpf.getText().length()<11 || cpf.getText().length()>11){
            exibirAviso("CPF invalido", "Digite o CPF novamente, lembre de não colocar . ou -");
            return;
        }else if(ClienteDAO.clienteExistePorCpf(cpf.getText())){
            exibirAviso("CPF invalido", "CPF ja cadastrado");
            return;
        }

        if(nomeCliente.getText().length()<1){
            exibirAviso("Nome invalido", "Digite um Nome");
            return;
        }
        int idade = 0;
        try{
            idade = Integer.parseInt(idadeCliente.getText());
        }catch (Exception e){
            exibirAviso("Idade invalida", "Idade invalida");
            return;
        }

        if( idade < 1 || idade > 100){
            exibirAviso("Idade invalida", "Digite uma idade valida");
            return;
        }

        if(senha1.getText().length()<6 || senha2.getText().length()<6){
            exibirAviso("Senha invalida", "Digite uma senha maior que 6 caractere");
            return;
        }

        if(!senha1.getText().equals(senha2.getText())){
            exibirAviso("Senha invalida", "As senhas estão diferentes");
            return;
        }

        cliente = new ClienteModel(
                nomeCliente.getText(),
                Integer.parseInt(idadeCliente.getText()),
                estudante.isSelected(),
                cpf.getText()
        );




        bd.addCliente(criptografia(senha1.getText()));

        try {
            // Atualize a cena com a nova tela
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListaSessoesTela.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela ListaSessoesTela.fxml");
        }
    }

    public void voltar(ActionEvent event) {
        try {
            // Atualize a cena com a nova tela
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exibirAviso(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(mensagem);
        alert.showAndWait();
    }

    public String criptografia(String senha) {
       return BCrypt.hashpw(senha, "$2a$10$C5oM1KJbL/3iq4U8y1x8me");
    }
}
