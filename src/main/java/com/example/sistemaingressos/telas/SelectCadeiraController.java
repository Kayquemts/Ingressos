package com.example.sistemaingressos.telas;

import com.example.sistemaingressos.database.IngressoDAO;
import com.example.sistemaingressos.models.IngressoModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import static com.example.sistemaingressos.models.SalaModel.salas;
import static com.example.sistemaingressos.telas.SelecionarSessaoController.sessaoSelecionada;

import java.io.IOException;
import java.util.ArrayList;

public class SelectCadeiraController {
    @FXML
    GridPane cadeiras;

    public static ArrayList<String> cadeirasSelecionadas = new ArrayList<>();

    private int qnt_cadeiras_disponiveis = 1;

    public void initialize() {
        int qnt_cadeiras = salas.get(sessaoSelecionada.getSalaId()).getQntMaxPessoas(), qnt_cadeiras_por_fila = 10;
        ArrayList<Integer> cadeirasOcupadas = IngressoDAO.buscarCadeirasOcupadas(sessaoSelecionada.getId());
        for (int i = 0; i < qnt_cadeiras; i++) {
            Button cadeira = new Button();
            if (cadeirasOcupadas.contains(i+1)) {
                cadeira.setDisable(true);
            }
            cadeira.setMinSize(78, 60);
            cadeira.setText(String.valueOf(i+1));
            cadeira.getStyleClass().add("cadeira");
            cadeira.setOnAction(event -> selecionarCadeira(cadeira));
            int add = i % qnt_cadeiras_por_fila < 5 ? 0 : 1;
            cadeiras.add(cadeira, i%qnt_cadeiras_por_fila+add, i/qnt_cadeiras_por_fila);
        }
    }

    public void selecionarCadeira(Button cadeira) {
        String n_cadeira = cadeira.getText();
        if (cadeirasSelecionadas.contains(n_cadeira)) {
            cadeirasSelecionadas.remove(n_cadeira);
            cadeira.getStyleClass().remove("selecionada");

        } else if (cadeirasSelecionadas.size() < qnt_cadeiras_disponiveis) {
            cadeirasSelecionadas.add(n_cadeira);
            cadeira.getStyleClass().add("selecionada");

        } else {
            exibirErro("Quantidade máxima selecionada", "O limite é de "+  qnt_cadeiras_disponiveis +" cadeiras, caso queira mudar clique na cadeira para desmarcar.");
        }
    }

    @FXML
    public void salvarCadeiras(ActionEvent event) {
        try {
            if (cadeirasSelecionadas.size() < 1) {
                exibirErro("Erro", "Selecione pelo menos uma cadeira!");
                return;
            }
            Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
            scene.setRoot(new FXMLLoader(getClass().getResource("ComprarAssentoECombo.fxml")).load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancelar(ActionEvent event){
        try{
            cadeirasSelecionadas = new ArrayList<>();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListaSessoesTela.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));

        }catch (Exception e){

        }
    }


    private void exibirErro(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.setHeight(70);
        alerta.setWidth(120);
        alerta.showAndWait();
    }
}
