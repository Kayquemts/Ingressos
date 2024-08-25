package com.example.sistemaingressos.telas;

import com.example.sistemaingressos.MainApplication;
import com.example.sistemaingressos.database.*;
import com.example.sistemaingressos.models.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.sistemaingressos.models.FilmeModel.filmes;
import static com.example.sistemaingressos.models.SessaoModel.sessoes;
import static com.example.sistemaingressos.models.SalaModel.salas;
import static com.example.sistemaingressos.models.ComboModel.combos;

public class AdminController {
    @FXML
    TableView<SessaoModel> tabelaSessoes;
    @FXML
    TableColumn<SessaoModel, String> nomeTabelaSessoes, horarioTabelaSessoes;

    @FXML
    TableView<FilmeModel> tabelaFilmes;
    @FXML
    TableColumn<FilmeModel, String> nomeTabelaFilmes, classificacaoTabelaFilmes, generoTabelaFilmes, duracaoTabelaFilmes;

    @FXML
    TableView<SalaModel> tabelaSalas;
    @FXML
    TableColumn<SalaModel, Integer> idTabelaSalas, qntTabelaSalas;
    @FXML
    TableColumn<SalaModel, String> editarTabelaSalas;

    @FXML
    TableView<FilmeVendido> tabelaTopVendas;
    @FXML
    TableColumn<FilmeVendido, String> nomeTabelaTopVendas, quantidadeTabelaTopVendas;

    @FXML
    TableView<FilmeVendido> tabelaVendas;
    @FXML
    TableColumn<FilmeVendido, String> filmeTabelaVendas, qntTabelaVendas;



    @FXML
    TableView<ComboModel> tabelaCombos;
    @FXML
    TableColumn<ComboModel, String> precoTabelaCombos, itensComboTabelaCombos;


    @FXML
    DatePicker filtroData;
    ObservableList<FilmeModel> filmesLista = FXCollections.observableArrayList();
    ObservableList<SessaoModel> sessoesLista = FXCollections.observableArrayList();
    ObservableList<SalaModel> salasLista = FXCollections.observableArrayList();
    ObservableList<ComboModel> combosLista = FXCollections.observableArrayList();
    public static SessaoModel sessaoSelecionada = null;
    public static FilmeModel filmeSelecionado = null;
    public static ComboModel comboSelecionado = null;

    public void initialize() throws IOException, SQLException {

        filmesLista.setAll(filmes.values());
        sessoesLista.setAll(sessoes);
        salasLista.setAll(salas.values());
        combosLista.setAll(combos);

        // Sessoes
        tabelaSessoes.setItems(sessoes); // sessoesLista
        nomeTabelaSessoes.setCellValueFactory(data -> data.getValue().get("filme"));
        horarioTabelaSessoes.setCellValueFactory(data -> data.getValue().get("horario"));

        //Salas
        tabelaCombos.setItems(combos);
        precoTabelaCombos.setCellValueFactory(data -> data.getValue().get("preco"));
        itensComboTabelaCombos.setCellValueFactory(data -> data.getValue().get("itens"));

        // Filmes
        tabelaFilmes.setItems(filmesLista);
        nomeTabelaFilmes.setCellValueFactory(data -> data.getValue().get("nome"));
        classificacaoTabelaFilmes.setCellValueFactory(data -> data.getValue().get("classificacao"));
        generoTabelaFilmes.setCellValueFactory(data -> data.getValue().get("genero"));
        duracaoTabelaFilmes.setCellValueFactory(data -> data.getValue().get("duracao"));

        // Salas

        // Define os itens da tabela
        tabelaSalas.setItems(salasLista);

        // Define as colunas da tabela
        idTabelaSalas.setCellValueFactory(new PropertyValueFactory<>("id"));
        qntTabelaSalas.setCellValueFactory(new PropertyValueFactory<>("qntMaxPessoas"));
        editarTabelaSalas.setCellValueFactory(data -> new ReadOnlyStringWrapper("Editar"));

        // Configura a fábrica de células para a coluna de edição
        editarTabelaSalas.setCellFactory(tc -> new TableCell<SalaModel, String>() {
            private final Button btnEditar = new Button("Editar");
            private final TextField input = new TextField();

            {

                // Configura a ação do botão de edição
                btnEditar.setOnAction(event -> {
                    SalaModel sala = getTableRow().getItem();
                    if (sala != null) {
                        input.setText(String.valueOf(sala.getQntMaxPessoas()));
                        setGraphic(input);
                    }
                });

                // Configura a ação do campo de texto
                input.setOnAction(event -> {
                    SalaModel sala = getTableRow().getItem();
                    if (sala != null) {
                        try {
                            int novaCapacidade = Integer.parseInt(input.getText());
                            if (novaCapacidade > 70 || novaCapacidade < 1) {
                                exibirErro("Capacidade inválida!", "Digite um número entre 1 e 70");
                                return;
                            }
                            sala.setQntMaxPessoas(novaCapacidade);
                            setGraphic(btnEditar);
                        } catch (NumberFormatException ignored) {
                            exibirErro("Entrada inválida!", "Digite um número válido");
                        }
                    }
                });

                // Configura o listener de foco do campo de texto
                input.focusedProperty().addListener((obs, oldValue, newValue) -> {
                    if (!newValue) {
                        setGraphic(btnEditar);
                        salasLista.setAll(salas.values());
                    }
                });

                setGraphic(btnEditar);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    setGraphic(btnEditar);
                }
            }
        });


        // Filmes mais vendidos
        tabelaTopVendas.setItems(FXCollections.observableArrayList(IngressoDAO.buscarFilmeMaisVendidos()));
        nomeTabelaTopVendas.setCellValueFactory(data -> data.getValue().get("filme"));
        quantidadeTabelaTopVendas.setCellValueFactory(data -> data.getValue().get("quantidade"));

        // Vendas no dia
        filtroData.setValue(LocalDate.now());
        filtrarData();


    }
    public void adicionarFilme(ActionEvent event) {
        try {
            Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
            scene.setRoot(new FXMLLoader(getClass().getResource("AddEditFilme.fxml")).load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void editarFilme(ActionEvent event) {
        filmeSelecionado = tabelaFilmes.getSelectionModel().getSelectedItem();
        if (filmeSelecionado != null) {
            try {
                Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
                scene.setRoot(new FXMLLoader(getClass().getResource("AddEditFilme.fxml")).load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            exibirErro("Erro", "Selecione o filme a ser editado");
        }
    }

    public void removerFilme(ActionEvent event) {
        filmeSelecionado = tabelaFilmes.getSelectionModel().getSelectedItem();
        if (filmeSelecionado != null) {
            filmes.remove(filmeSelecionado.getNome());
            filmesLista.remove(filmeSelecionado);
            FilmeDAO.deletarFilme(filmeSelecionado);
        } else {
            exibirErro("Erro", "Selecione o filme a ser deletado");
        }
    }

    public void adicionarSessao(ActionEvent event) {
        try {
            Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
            scene.setRoot(new FXMLLoader(getClass().getResource("AddEditSessao.fxml")).load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void editarSessao(ActionEvent event) {
        sessaoSelecionada = tabelaSessoes.getSelectionModel().getSelectedItem();
        if (sessaoSelecionada != null) {
            try {
                Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
                scene.setRoot(new FXMLLoader(getClass().getResource("AddEditSessao.fxml")).load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            exibirErro("Erro", "Selecione a sessão a ser editada");
        }
    }
    public void deletarSessao(ActionEvent event) {
        sessaoSelecionada = tabelaSessoes.getSelectionModel().getSelectedItem();
        if (sessaoSelecionada != null) {
            sessoes.remove(sessaoSelecionada);
            sessoesLista.remove(sessaoSelecionada);
            SessaoDAO.deletarSessao(sessaoSelecionada);
        } else {
            exibirErro("Erro", "Selecione a sessão a ser deletada");
        }
    }



    public void salvarSalas(ActionEvent event) {
        for (SalaModel sala: tabelaSalas.getItems()) {
            SalaModel.editarSala(sala);
        }
    }

    public void adicionarSala(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddSala.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            // Abra esta cena em uma nova janela ou diálogo
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void filtrarData() {
        LocalDate dia = filtroData.getValue();
        if (dia != null) {
            ArrayList<FilmeVendido> vendas = VendaDAO.buscarVendasNoDia(dia);
            tabelaVendas.setItems(FXCollections.observableArrayList(vendas));
            filmeTabelaVendas.setCellValueFactory(data -> data.getValue().get("filme"));
            qntTabelaVendas.setCellValueFactory(data -> data.getValue().get("quantidade"));
        }
    }

    public void adicionarCombo(ActionEvent event) {
        try {
            Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
            scene.setRoot(new FXMLLoader(getClass().getResource("AddEditCombo.fxml")).load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletarCombo(ActionEvent event){
        comboSelecionado = tabelaCombos.getSelectionModel().getSelectedItem();
        if(comboSelecionado != null) {
            combos.remove(comboSelecionado);
            combosLista.remove(comboSelecionado);
            ComboDAO.deletarCombo(comboSelecionado);
        } else {
            exibirErro("Erro", "Selecione o combo a ser deletada");
        }
    }



    public void editarCombo(ActionEvent event) {

        comboSelecionado = tabelaCombos.getSelectionModel().getSelectedItem();
        if (comboSelecionado != null) {
            try {
                Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
                scene.setRoot(new FXMLLoader(getClass().getResource("AddEditCombo.fxml")).load());
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        } else {
            exibirErro("Erro", "Selecione o combo a ser editado");
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
