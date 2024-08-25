package com.example.sistemaingressos.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.HashMap;
import com.example.sistemaingressos.database.ClienteDAO;

public class ClienteModel {
    private String nome;
    private int idade;
    private boolean estudante;
    private String cpf;


    //public static HashMap<String, ClienteModel> cliente = new HashMap<>();



    public ClienteModel(String nome, int idade, boolean estudante, String cpf) {
        this.nome = nome;
        this.idade = idade;
        this.estudante = estudante;
        this.cpf = cpf;

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public boolean isEstudante() {
        return estudante;
    }

    public void setEstudante(boolean estudante) {
        this.estudante = estudante;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "ClienteModel{" +
                "nome='" + nome + '\'' +
                ", idade=" + idade +
                ", estudante=" + estudante +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}
