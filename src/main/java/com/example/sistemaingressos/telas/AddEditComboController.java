package com.example.sistemaingressos.telas;

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

public class AddEditComboController {

    @FXML
    private Label comboLabel;

    @FXML
    private TextField nomeCombo, descricaoCombo, valorCombo;

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
        /*
        try {
            String nome = nomeCombo.getText();
            String descricao = descricaoCombo.getText();
            if (nome.isEmpty() || descricao.isEmpty()) {
                exibirErro("Campo não preenchido", "Informe o nome/descrição do combo");
                return;
            }
            double valor = Double.parseDouble(valorCombo.getText());
            boolean confirm = false;

            if (comboSelecionado == null) {
                if (!combos.containsKey(nome)) {
                    confirm = exibirConfirmar("Adicionar", "Deseja adicionar esse combo?");
                    if (confirm) {
                        ComboModel combo = new ComboModel(nome, descricao, valor);
                        ComboModel.addCombo(combo);
                    }
                } else {
                    exibirErro("Combo já existente", "Já existe um combo com esse nome");
                }
            } else {
                confirm = exibirConfirmar("Salvar alterações", "Deseja salvar as mudanças?");
                if (confirm) {
                    comboSelecionado.setDescricao(descricao);
                    comboSelecionado.setValor(valor);
                    ComboModel.editarCombo(comboSelecionado);
                    comboSelecionado = null;
                }
            }

            try {
                Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
                scene.setRoot(new FXMLLoader(getClass().getResource("AdminTela.fxml")).load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (NumberFormatException ignored) {

        }*/
    }

    private void exibirErro(String titulo, String mensagem) {
        /*
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.setHeight(70);
        alerta.setWidth(120);
        alerta.showAndWait();
        */

    }

    private boolean exibirConfirmar(String titulo, String mensagem) {
        /*
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

         */
        return true;
    }
}
