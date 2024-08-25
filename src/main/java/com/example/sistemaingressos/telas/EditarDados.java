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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import static com.example.sistemaingressos.telas.DadosClienteController.cliente;


public class EditarDados {
    @FXML
    private TextField nomeCliente;
    @FXML private TextField idadeCliente;
    @FXML private CheckBox estudante;
    @FXML private TextField cpf;

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

        cliente.setNome(nomeCliente.getText());
        cliente.setIdade(Integer.parseInt(idadeCliente.getText()));
        cliente.setEstudante(estudante.isSelected());
        cliente.setCpf(cpf.getText());

        ClienteDAO.atualizarCliente(cliente);
        voltar(event);

    }

    public void voltar(ActionEvent event) {
        try {
            // Atualize a cena com a nova tela
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListaSessoesTela.fxml"));
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
}
