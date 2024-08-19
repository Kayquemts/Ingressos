package com.example.sistemaingressos.models;

import com.example.sistemaingressos.database.IngressoDAO;
import com.example.sistemaingressos.database.VendaDAO;

import java.time.LocalDate;
import java.util.ArrayList;

public class VendaModel {
    private int id;
    private int qntIngressos;
    private LocalDate data;
    private String nomeComprador;
    private double precoTotal;
    private ArrayList<IngressoModel> ingressos;

    public VendaModel(int id, int qntIngressos, LocalDate data, String nomeComprador, double precoTotal) {
        this.id = id;
        this.qntIngressos = qntIngressos;
        this.data = data;
        this.nomeComprador = nomeComprador;
        this.precoTotal = precoTotal;
    }

    public VendaModel(int qntIngressos, LocalDate data, String nomeComprador, double precoTotal) {
        this.qntIngressos = qntIngressos;
        this.data = data;
        this.nomeComprador = nomeComprador;
        this.precoTotal = precoTotal;
    }
    public static int addVenda(VendaModel venda) {
        return VendaDAO.addVenda(venda);
    }
    public void salvarIngressos() {
        for (IngressoModel ingresso: ingressos) {
            IngressoDAO.addIngresso(ingresso);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQntIngressos() {
        return qntIngressos;
    }

    public void setQntIngressos(int qntIngressos) {
        this.qntIngressos = qntIngressos;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getNomeComprador() {
        return nomeComprador;
    }

    public void setNomeComprador(String nomeComprador) {
        this.nomeComprador = nomeComprador;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public ArrayList<IngressoModel> getIngressos() {
        return ingressos;
    }

    public void setIngressos(ArrayList<IngressoModel> ingressos) {
        this.ingressos = ingressos;
    }
}
