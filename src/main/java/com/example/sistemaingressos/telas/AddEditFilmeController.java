package com.example.sistemaingressos.telas;

import static com.example.sistemaingressos.models.FilmeModel.filmes;
import static com.example.sistemaingressos.telas.AdminController.filmeSelecionado;

import com.example.sistemaingressos.models.FilmeModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class AddEditFilmeController {
    @FXML
    private ChoiceBox<String> classificacaoSelect;
    @FXML
    private TextField nomeFilme, generoFilme, duracaoFilme;

    public void initialize() {
        classificacaoSelect.getItems().addAll("Livre", "10+ anos", "12+ anos", "14+ anos", "16+ anos", "18+ anos");

        if (filmeSelecionado != null) {
            String classificacao = filmeSelecionado.getFaixaEtaria() == 0 ? "Livre": filmeSelecionado.getFaixaEtaria() + "+ anos";
            nomeFilme.setText(filmeSelecionado.getNome());
            nomeFilme.setEditable(false);
            generoFilme.setText(filmeSelecionado.getGenero());
            duracaoFilme.setText(String.valueOf(filmeSelecionado.getDuracao()));
            classificacaoSelect.setValue(classificacao);
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
    void salvarFilme(ActionEvent event) {
        try {
            String nome = nomeFilme.getText();
            String genero = generoFilme.getText();
            if (nome.isEmpty() || genero.isEmpty()) {
                exibirErro("Campo não preenchido", "Informe o nome/genero do filme");
                return;
            }
            int duracao = Integer.parseInt(duracaoFilme.getText());
            if (classificacaoSelect.getValue() == null) {
                exibirErro("Classificação não selecionada", "Selecione a classificação");
                return;
            }
            int classificacao;
            switch (classificacaoSelect.getValue()) {
                case "Livre":
                    classificacao = 0;
                    break;
                default:
                    classificacao = Integer.parseInt(classificacaoSelect.getValue().substring(0, 2));
            }

            boolean confirm = false;

            if (filmeSelecionado == null) {
                if (!filmes.containsKey(nome)) {
                    confirm = exibirConfirmar("Adicionar", "Deseja adicionar esse filme?");
                    if (confirm) {
                        FilmeModel filme = new FilmeModel(nome, genero, duracao, classificacao);
                        FilmeModel.addFilme(filme);
                    }
                } else {
                    exibirErro("Filme já existente", "Já existe um filme com esse nome");
                }
            } else {
                confirm = exibirConfirmar("Salvar alterações", "Deseja salvar as mudanças?");
                if (confirm) {
                    filmeSelecionado.setGenero(genero);
                    filmeSelecionado.setDuracao(duracao);
                    filmeSelecionado.setFaixaEtaria(classificacao);
                    FilmeModel.editarFilme(filmeSelecionado);
                    filmeSelecionado = null;
                }
            }

            try {
                Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
                scene.setRoot(new FXMLLoader(getClass().getResource("AdminTela.fxml")).load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (NumberFormatException ignored) {

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