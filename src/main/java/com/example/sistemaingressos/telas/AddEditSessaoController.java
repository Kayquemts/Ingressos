package com.example.sistemaingressos.telas;

import com.example.sistemaingressos.database.SessaoDAO;
import com.example.sistemaingressos.models.FilmeModel;
import com.example.sistemaingressos.models.SalaModel;
import com.example.sistemaingressos.models.SessaoModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.sistemaingressos.models.FilmeModel.filmes;
import static com.example.sistemaingressos.models.SalaModel.salas;
import static com.example.sistemaingressos.models.SessaoModel.sessoes;
import static com.example.sistemaingressos.telas.AdminController.sessaoSelecionada;

public class AddEditSessaoController {
    @FXML
    ChoiceBox<String> selectFilme, selectHora, selectMinuto;
    @FXML
    ChoiceBox<Integer> selectSala;
    @FXML
    TextField capacidadeInput, precoInput;

    public void initialize() {
        selectFilme.getItems().addAll(filmes.keySet());
        addHorarios();

        List<Integer> listaSalas = salas.values().stream()
                .filter(sala -> sala instanceof SalaModel)
                .map(SalaModel::getId)
                .collect(Collectors.toList());




        selectSala.getItems().addAll(listaSalas);

        selectSala.setOnAction(event -> {
            capacidadeInput.setText(String.valueOf(salas.get(selectSala.getValue()).getQntMaxPessoas()));
        });

        if (sessaoSelecionada != null) {
            SalaModel sala = salas.get(sessaoSelecionada.getSalaId());
            selectFilme.setValue(sessaoSelecionada.getStr("filme"));
            selectHora.setValue(sessaoSelecionada.getHora() + " h");
            selectMinuto.setValue(sessaoSelecionada.getMinuto() + " min");
            precoInput.setText(String.valueOf(sessaoSelecionada.getPreco()));
            selectSala.setValue(sala.getId());
            capacidadeInput.setText(String.valueOf(sala.getQntMaxPessoas()));
        }
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
    public void salvarSessao(ActionEvent event) {
        try {
            if (selectFilme.getValue() == null) {
                exibirErro("Filme inválido", "Selecione o filme");
                return;
            }
            int hora = Integer.parseInt(selectHora.getValue().split(" ",0)[0]);
            int minuto = Integer.parseInt(selectMinuto.getValue().split(" ",0)[0]);
            int salaId = selectSala.getValue();
            double preco = Double.parseDouble(precoInput.getText());
            FilmeModel filme = filmes.get(selectFilme.getValue());

            boolean confirm;
            if (sessaoSelecionada == null) {
                confirm = exibirConfirmar("Adicionar sessão", "Deseja adicionar a sessão?");
                if (confirm) {
                    SessaoModel sessao = new SessaoModel(-1, filme, hora, minuto, salaId, preco);
                    SessaoModel.addSessao(sessao);
                }
            } else {
                confirm = exibirConfirmar("Salvar alterações", "Deseja salvar as alteraçoes?");
                if (confirm) {
                    sessaoSelecionada.setFilme(filmes.get(selectFilme.getValue()));
                    sessaoSelecionada.setHora(hora);
                    sessaoSelecionada.setMinuto(minuto);
                    sessaoSelecionada.setSalaId(salaId);
                    sessaoSelecionada.setPreco(preco);
                    SessaoModel.editarSessao(sessaoSelecionada);
                    sessaoSelecionada = null;
                }
            }
            try {
                Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
                scene.setRoot(new FXMLLoader(getClass().getResource("AdminTela.fxml")).load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (NumberFormatException ignored) {
            exibirErro("Valor inválido", "Digite um preço válidoo");
        }
    }

    public void addHorarios() {
        String[] horas = new String[24];
        String[] minutos = new String[60];
        for (int i = 0; i < 60; i++) {
            minutos[i] = i + " min";
            if (i < 24) horas[i] = i + " h";
        }
        selectHora.getItems().addAll(horas);
        selectMinuto.getItems().addAll(minutos);
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
