package com.example.sistemaingressos.models;

import com.example.sistemaingressos.database.IngressoDAO;
import com.example.sistemaingressos.database.VendaDAO;
import com.example.sistemaingressos.database.VendaComboDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class VendaModel {
    private int id;
    private int qntIngressos;
    private LocalDate data;
    private String cpf;
    private double precoTotal;
    private ArrayList<IngressoModel> ingressos;

    public VendaModel(int id, int qntIngressos, LocalDate data, String cpf, double precoTotal) {
        this.id = id;
        this.qntIngressos = qntIngressos;
        this.data = data;
        this.cpf = cpf;
        this.precoTotal = precoTotal;
    }

    public VendaModel(int qntIngressos, LocalDate data, String cpf, double precoTotal) {
        this.qntIngressos = qntIngressos;
        this.data = data;
        this.cpf = cpf;
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

    public void setCombos(ArrayList<ComboModel> combos, int idVenda) throws SQLException {
        System.out.println("------");
        for (ComboModel item : combos) {
            System.out.println(item);
        }
        System.out.println("----");

        for (ComboModel item : combos) {
            VendaComboDAO.adicionarIngressoCombo(item.getId(), idVenda);
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    @Override
    public String toString() {
        return "VendaModel{" +
                "id=" + id +
                ", qntIngressos=" + qntIngressos +
                ", data=" + data +
                ", cpf ='" + cpf + '\'' +
                ", precoTotal=" + precoTotal +
                ", ingressos=" + ingressos +
                '}';
    }
}
