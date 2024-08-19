package com.example.sistemaingressos.models;

import com.example.sistemaingressos.database.Conexao;
import com.example.sistemaingressos.database.SessaoDAO;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

import static com.example.sistemaingressos.models.FilmeModel.filmes;

public class SessaoModel {
    public static ObservableList<SessaoModel> sessoes = FXCollections.observableArrayList();
    private int id;
    private FilmeModel filme;
    private int hora;
    private int minuto;
    private int salaId;
    private double preco;

    public SessaoModel(int id, FilmeModel filme, int hora, int minuto, int salaId, double preco) {
        this.id = id;
        this.filme = filme;
        this.hora = hora;
        this.minuto = minuto;
        this.salaId = salaId;
        this.preco = preco;
    }

    public static void carregarSessoes() {
        SessaoDAO.carregarSessoes();
    }

    public static void addSessao(SessaoModel sessao) {
        SessaoDAO.adicionarSessao(sessao);
        sessoes.add(sessao);
    }

    public static void editarSessao(SessaoModel sessao) {
        SessaoDAO.editarSessao(sessao);
    }

    public static void deletarSessao(SessaoModel sessao) {
        SessaoDAO.deletarSessao(sessao);
        sessoes.remove(sessao);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FilmeModel getFilme() {
        return filme;
    }

    public void setFilme(FilmeModel filme) {
        this.filme = filme;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public int getSalaId() {
        return salaId;
    }

    public void setSalaId(int salaId) {
        this.salaId = salaId;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public ReadOnlyStringWrapper get(String attr) {
        String str = getStr(attr);
        return new ReadOnlyStringWrapper(str);
    }

    public String getStr(String attr) {
        return switch (attr) {
            case "filme" -> this.filme.getNome();
            case "genero" -> this.filme.getGenero();
            case "classificacao" -> this.filme.getFaixaEtaria() != 0 ? this.filme.getFaixaEtaria() + "+ anos" : "Livre";
            case "horario" -> this.hora + "h" + this.minuto + "min";
            case "preco" -> this.preco + " R$";
            default -> "";
        };
    }
}
