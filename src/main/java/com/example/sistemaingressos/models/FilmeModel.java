package com.example.sistemaingressos.models;

import com.example.sistemaingressos.database.FilmeDAO;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.*;

import java.sql.*;

import static com.example.sistemaingressos.telas.AdminController.filmeSelecionado;

public class FilmeModel {
    private String nome;
    private String genero;
    private int duracao; // duracao em minutos
    private int faixaEtaria;
    public static ObservableMap<String, FilmeModel> filmes = FXCollections.observableHashMap();

    public FilmeModel(String nome, String genero, int duracao, int faixaEtaria) {
        this.nome = nome;
        this.genero = genero;
        this.duracao = duracao;
        this.faixaEtaria = faixaEtaria;
    }

    public static void carregarFilmes() throws SQLException {
        FilmeDAO.carregarFilmes();
    }

    public static void addFilme(FilmeModel filme){
        filmes.put(filme.getNome(), filme);
        FilmeDAO.adicionarFilme(filme);
    }

    public static void editarFilme(FilmeModel filme){
        FilmeDAO.editarFilme(filme);
    }

    public static void deletarFilme(FilmeModel filme){
        filmes.remove(filme.getNome());
        FilmeDAO.deletarFilme(filme);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public int getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(int faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public ReadOnlyStringWrapper get(String attr) {

        String str = getStr(attr);
        return new ReadOnlyStringWrapper(str);
    }

    public String getStr(String attr) {
        return switch (attr) {
            case "nome" -> this.nome;
            case "genero" -> this.genero;
            case "classificacao" -> this.faixaEtaria == 0 ? "Livre": this.faixaEtaria + "+ anos";
            case "duracao" -> this.duracao + "min";
            default -> "";
        };
    }

    @Override
    public String toString() {
        return "FilmeModel{" +
                "nome='" + nome + '\'' +
                ", genero='" + genero + '\'' +
                ", duracao=" + duracao +
                ", faixaEtaria=" + faixaEtaria +
                '}';
    }
}
