package com.example.sistemaingressos.telas;

import com.example.sistemaingressos.database.ClienteDAO;
import com.example.sistemaingressos.models.ClienteModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class DadosClienteController {
    @FXML private TextField nomeCliente;
    @FXML private TextField idadeCliente;
    @FXML private CheckBox estudante;
    @FXML private TextField cpf;

    public static ClienteModel cliente = null;
    private ClienteDAO bd = new ClienteDAO();

    @FXML
    public void initialize() {
        if (cliente != null) {
            nomeCliente.setText(cliente.getNome());
            idadeCliente.setText(String.valueOf(cliente.getIdade()));
            estudante.setSelected(cliente.isEstudante());
            cpf.setText(cliente.getCpf());
        }
    }

    @FXML
    protected void salvarDadosCliente(ActionEvent event) {

        ClienteModel clienteExistente = bd.getClienteByCpf(cpf.getText());

        if (clienteExistente != null) {
            System.out.println("Cliente existente");

            String nome = clienteExistente.getNome();
            int idade = clienteExistente.getIdade();
            boolean estudante = clienteExistente.isEstudante();
            String cpf = clienteExistente.getCpf();
            if (cliente == null){
                cliente = new ClienteModel(nome, idade, estudante, cpf);
            }else{
                cliente.setNome(clienteExistente.getNome());
                cliente.setIdade(clienteExistente.getIdade());
                cliente.setEstudante(clienteExistente.isEstudante());
                cliente.setCpf(clienteExistente.getCpf());
            }
        }else{
            System.out.println("teste");

            cliente = new ClienteModel(
                    nomeCliente.getText(),
                    Integer.parseInt(idadeCliente.getText()),
                    estudante.isSelected(),
                    cpf.getText()
            );

            bd.addCliente();
        }
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
}
