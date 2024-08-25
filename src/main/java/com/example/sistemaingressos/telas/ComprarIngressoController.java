package com.example.sistemaingressos.telas;

import com.example.sistemaingressos.models.ComboModel;
import com.example.sistemaingressos.models.IngressoModel;
import com.example.sistemaingressos.models.VendaModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.example.sistemaingressos.telas.SelecionarSessaoController.sessaoSelecionada;
import static com.example.sistemaingressos.telas.SelectCadeiraController.cadeirasSelecionadas;
import static com.example.sistemaingressos.telas.DadosClienteController.cliente;
import static com.example.sistemaingressos.models.ComboModel.combos;

public class ComprarIngressoController {

    @FXML
    private VBox dados;

    @FXML
    private VBox confortoList;

    @FXML
    TableView<ComboModel> tabelaCombos;
    @FXML
    TableColumn<ComboModel, String> precoTabelaCombos, itensComboTabelaCombos;

    Font textBig, textSmall;
    ArrayList<String> cadeirasConfortaveis = new ArrayList<>();
    private double totalIngressos = 0;
    private final ObservableList<ComboModel> combosSelecionados = FXCollections.observableArrayList();

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

        tabelaCombos.setItems(combos);
        precoTabelaCombos.setCellValueFactory(data -> data.getValue().get("preco"));
        itensComboTabelaCombos.setCellValueFactory(data -> data.getValue().get("itens"));

        // Configuração para permitir seleção múltipla de combos
        tabelaCombos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tabelaCombos.getSelectionModel().getSelectedItems().addListener((ListChangeListener<ComboModel>) change -> {
            combosSelecionados.clear();
            combosSelecionados.addAll(change.getList());
            atualizarTextoCompra();
        });

        atualizarTextoCompra();
    }

    @FXML
    public void finalizarCompra(ActionEvent event) throws SQLException {
        boolean confirm = exibirConfirmar("Finalizar compra", "Deseja finalizar a compra?");
        System.out.println(totalIngressos);
        if (confirm) {
            VendaModel venda = new VendaModel(cadeirasSelecionadas.size(), LocalDate.now(), cliente.getCpf(), totalIngressos);
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

            venda.setCombos(new ArrayList<>(combosSelecionados), vendaId);
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

        // Adiciona informações dos ingressos
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

        // Adiciona informações dos combos
        if (!combosSelecionados.isEmpty()) {
            Label comboTexto = new Label("Combos Selecionados:");
            comboTexto.setFont(textBig);
            comboTexto.setStyle("-fx-text-fill: #50a3ab;");
            labels.add(comboTexto);

            for (ComboModel combo : combosSelecionados) {
                Label comboLabel = new Label(String.format("• %s - %.2f R$", combo.getItens_combo(), combo.getPreco_combo()));
                comboLabel.setFont(textSmall);
                comboLabel.setStyle("-fx-text-fill: #50a3ab;");
                labels.add(comboLabel);

                totalIngressos += combo.getPreco_combo();
            }
        }

        Label totalIngressosText = new Label(String.format("Ingressos: %.2f R$", totalIngressos));
        totalIngressosText.setFont(textBig);
        totalIngressosText.setStyle("-fx-text-fill: #50a3ab;");
        labels.add(2, totalIngressosText);

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
