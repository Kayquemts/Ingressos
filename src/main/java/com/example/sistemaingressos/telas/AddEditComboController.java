package com.example.sistemaingressos.telas;

import com.example.sistemaingressos.database.ComboDAO;
import com.example.sistemaingressos.models.FilmeModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import com.example.sistemaingressos.models.ComboModel;
import static com.example.sistemaingressos.models.ComboModel.combos;
import static com.example.sistemaingressos.models.FilmeModel.filmes;
import static com.example.sistemaingressos.telas.AdminController.comboSelecionado;

public class AddEditComboController {

    @FXML
    private Label comboLabel;

    @FXML
    private TextField nomeCombo, valorCombo;

    public void initialize() {
        /*
        if (comboSelecionado != null) {
            nomeCombo.setText(comboSelecionado.getNome());
            descricaoCombo.setText(comboSelecionado.getDescricao());
            valorCombo.setText(String.valueOf(comboSelecionado.getValor()));
        }*/
        System.out.println(comboLabel.getText());
    }

    @FXML
    public void cancelar(ActionEvent event) {
        try {
            Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
            scene.setRoot(new FXMLLoader(getClass().getResource("AdminTela.fxml")).load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void salvarCombo(ActionEvent event) {
        try {
            String nome = nomeCombo.getText();
            String valor = valorCombo.getText();
            double valorD;
            if (nome.isEmpty() || valor.isEmpty()) {
                exibirErro("Campo não preenchido", "Informe o nome ou valor do combo");
                return;
            }

            if (valor.matches("-?\\d+(\\.\\d+)?")) {
                valorD = Double.parseDouble(valor);
            } else {
                exibirErro("Valor invalido", "digite um valor valido");
                return;
            }

            boolean confirm;

            if(comboSelecionado == null){
                confirm = exibirConfirmar("Adicionar", "Deseja adiconar esse combo?");
                if (confirm) {
                    ComboModel combo = new ComboModel(nome, valorD);
                    ComboModel.addCombo(combo);
                }


            }else {
                System.out.println("teste2");
            }
        }catch (Exception e) {
            e.printStackTrace();
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

    private boolean exibirConfirmar(String titulo, String mensagem) {

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.setHeight(70);
        alerta.setWidth(120);

        ButtonType botaoConfirmar = new ButtonType("Sim");
        ButtonType botaoCancelar = new ButtonType("Não");
        alerta.getButtonTypes().setAll(botaoConfirmar, botaoCancelar);
        AtomicBoolean result = new AtomicBoolean(false);
        alerta.showAndWait().ifPresent(button -> {
            result.set(button == botaoConfirmar);
        });

        return result.get();
    }
}
