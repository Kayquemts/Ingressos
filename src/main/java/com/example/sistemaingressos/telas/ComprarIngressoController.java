package com.example.sistemaingressos.telas;

import com.example.sistemaingressos.models.IngressoModel;
import com.example.sistemaingressos.models.VendaModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.example.sistemaingressos.telas.SelecionarSessaoController.sessaoSelecionada;
import static com.example.sistemaingressos.telas.SelectCadeiraController.cadeirasSelecionadas;
import static com.example.sistemaingressos.telas.DadosClienteController.cliente;

public class ComprarIngressoController {

    @FXML
    private VBox dados;

    @FXML
    private VBox confortoList;

    @FXML
    private CheckBox combo1, combo2, combo3;

    Font textBig, textSmall;
    ArrayList<String> cadeirasConfortaveis = new ArrayList<>();
    private double totalIngressos = 0;
    private List<CheckBox> comboList;

    public void initialize() {
        textBig = ((Label) dados.getChildren().get(0)).getFont();
        textSmall = ((Label) dados.getChildren().get(1)).getFont();

        for (String cadeira: cadeirasSelecionadas) {
            CheckBox btn = new CheckBox("Cadeira " + cadeira);
            btn.setFont(textBig);
            btn.setStyle("-fx-text-fill: #50a3ab;");

            btn.setId(cadeira);
            btn.setOnAction(event -> {
                if (btn.isSelected()) {
                    cadeirasConfortaveis.add(cadeira);
                } else {
                    cadeirasConfortaveis.remove(cadeira);
                }
                atualizarTextoCompra();
            });
            confortoList.getChildren().add(btn);
        }

        comboList = List.of(combo1, combo2, combo3);
        comboList.forEach(combo -> combo.setOnAction(event -> atualizarTextoCompra()));

        atualizarTextoCompra();
    }

    @FXML
    public void finalizarCompra(ActionEvent event) {
        boolean confirm = exibirConfirmar("Finalizar compra", "Deseja finalizar a compra?");
        System.out.println(totalIngressos);
        if (confirm) {
            VendaModel venda = new VendaModel(cadeirasSelecionadas.size(), LocalDate.now(), cliente.getCpf(), totalIngressos);
            System.out.println(venda);
            int vendaId = VendaModel.addVenda(venda);
            ArrayList<IngressoModel> ingressos = new ArrayList<>();
            for (String cadeira: cadeirasSelecionadas) {
                ingressos.add(new IngressoModel(sessaoSelecionada.getFilme().getNome(), Integer.parseInt(cadeira),
                        cadeirasConfortaveis.contains(cadeira), sessaoSelecionada.getSalaId(),sessaoSelecionada.getId(),
                        vendaId, sessaoSelecionada.getPreco()));
            }
            venda.setIngressos(ingressos);
            venda.salvarIngressos();
            exibirAviso("Compra realizada com sucesso", "Você comprou os ingressos com sucesso!");

            try {
                // Atualize a cena com a nova tela
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ListaSessoesTela.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erro ao carregar a tela ListaSessoesTela.fxml");
            }
        }
    }

    public void atualizarTextoCompra() {
        dados.getChildren().clear();
        ArrayList<Label> labels = new ArrayList<>();

        Label sessaoTexto = new Label("Dados da Sessão");
        sessaoTexto.setFont(textBig);
        sessaoTexto.setStyle("-fx-text-fill: #50a3ab;");

        labels.add(sessaoTexto);
        Label infoTexto = new Label(String.format(
                "• Filme: %s\n• Sala: %d\n• Horário: %dh%dmin\n", sessaoSelecionada.getStr("filme"),
                sessaoSelecionada.getSalaId(), sessaoSelecionada.getHora(), sessaoSelecionada.getMinuto()
        ));
        infoTexto.setFont(textSmall);
        infoTexto.setStyle("-fx-text-fill: #50a3ab;");
        labels.add(infoTexto);
        totalIngressos = 0;
        for (String cadeirasSelecionada : cadeirasSelecionadas) {
            double preco = sessaoSelecionada.getPreco() / (cliente.isEstudante() ? 2 : 1);
            String precoText = String.format("%.2f R$%s", preco, cliente.isEstudante() ? " (Meia)" : "(Integra)");

            boolean isEspecial = cadeirasConfortaveis.contains(cadeirasSelecionada);
            String especialText = isEspecial ? "Confortável": "Normal";
            double especialPreco = isEspecial ? 10 : 0;
            String especialPrecoText = isEspecial ? "+ 10,00 R$(Especial)": "";

            String texto = String.format("• Cadeira %s (%s) - %s %s", cadeirasSelecionada,
                    especialText, precoText, especialPrecoText);

            Label cadeiraTextoNew = new Label(texto);
            cadeiraTextoNew.setFont(textSmall);
            cadeiraTextoNew.setStyle("-fx-text-fill: #50a3ab;");
            labels.add(cadeiraTextoNew);

            totalIngressos += preco + especialPreco;
        }
        Label totalIngressosText = new Label(String.format("Ingressos: %.2f R$", totalIngressos));
        totalIngressosText.setFont(textBig);
        totalIngressosText.setStyle("-fx-text-fill: #50a3ab;");
        labels.add(2, totalIngressosText);

        List<String> combosSelecionados = new ArrayList<>();
        if (combo1.isSelected()) {
            combosSelecionados.add("Combo 1: Refri + Chocolate (10% OFF)");
            totalIngressos *= 0.9;
        }
        if (combo2.isSelected()) {
            combosSelecionados.add("Combo 2: Refri + Pipoca (15% OFF)");
            totalIngressos *= 0.85;
        }
        if (combo3.isSelected()) {
            combosSelecionados.add("Combo 3: Refri + Chocolate + Pipoca (20% OFF)");
            totalIngressos *= 0.8;
        }

        Label comboLabel = new Label("Combos Selecionados: " + String.join(", ", combosSelecionados));
        comboLabel.setFont(textBig);
        comboLabel.setStyle("-fx-text-fill: #50a3ab;");
        labels.add(comboLabel);

        Label total = new Label("Total: " + String.format("%.2f R$", totalIngressos));
        total.setFont(textBig);
        total.setStyle("-fx-text-fill: #50a3ab;");
        labels.add(total);

        dados.getChildren().addAll(labels);
    }

    public boolean exibirConfirmar(String titulo, String mensagem) {
        AtomicBoolean confirmar = new AtomicBoolean(false);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(mensagem);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                confirmar.set(true);
            }
        });
        return confirmar.get();
    }

    public void exibirAviso(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(mensagem);
        alert.showAndWait();
    }
}
