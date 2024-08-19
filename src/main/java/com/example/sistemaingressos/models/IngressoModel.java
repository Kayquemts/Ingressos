package com.example.sistemaingressos.models;

public class IngressoModel {
    private int id;
    private int sessaoId;
    private int salaId;
    private int cadeiraId;
    private boolean cadeiraEspecial;
    private int vendaId;
    private String filme;
    private double precoFinal;

    public IngressoModel(int id, String filme, int cadeiraId, boolean cadeiraEspecial, int salaId, int sessaoId, int vendaId, double precoFinal) {
        this.id = id;
        this.cadeiraId = cadeiraId;
        this.cadeiraEspecial = cadeiraEspecial;
        this.salaId = salaId;
        this.sessaoId = sessaoId;
        this.vendaId = vendaId;
        this.filme = filme;
        this.precoFinal = precoFinal;
    }
    public IngressoModel(String filme, int cadeiraId, boolean cadeiraEspecial, int salaId, int sessaoId, int vendaId, double precoFinal) {
        this.cadeiraId = cadeiraId;
        this.cadeiraEspecial = cadeiraEspecial;
        this.salaId = salaId;
        this.sessaoId = sessaoId;
        this.vendaId = vendaId;
        this.filme = filme;
        this.precoFinal = precoFinal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCadeiraId() {
        return cadeiraId;
    }

    public void setCadeiraId(int cadeiraId) {
        this.cadeiraId = cadeiraId;
    }

    public boolean getCadeiraEspecial() {
        return cadeiraEspecial;
    }

    public void setCadeiraEspecial(boolean cadeiraEspecial) {
        this.cadeiraEspecial = cadeiraEspecial;
    }

    public int getSalaId() {
        return salaId;
    }

    public void setSalaId(int salaId) {
        this.salaId = salaId;
    }

    public int getSessaoId() {
        return sessaoId;
    }

    public void setSessaoId(int sessaoId) {
        this.sessaoId = sessaoId;
    }

    public int getVendaId() {
        return vendaId;
    }

    public void setVendaId(int vendaId) {
        this.vendaId = vendaId;
    }

    public String getFilme() {
        return filme;
    }

    public void setFilme(String filme) {
        this.filme = filme;
    }

    public double getPrecoFinal() {
        return precoFinal;
    }

    public void setPrecoFinal(double precoFinal) {
        this.precoFinal = precoFinal;
    }
}
