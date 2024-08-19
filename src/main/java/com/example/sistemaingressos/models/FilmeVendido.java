package com.example.sistemaingressos.models;

import javafx.beans.property.ReadOnlyStringWrapper;

public class FilmeVendido {
    private String nomeFilme;
    private int quantidadeVendida;

    public FilmeVendido(String nomeFilme, int quantidadeVendida) {
        this.nomeFilme = nomeFilme;
        this.quantidadeVendida = quantidadeVendida;
    }
    public String getStr(String attr) {
        return switch (attr) {
            case "filme" -> this.nomeFilme;
            case "quantidade" -> String.valueOf(this.quantidadeVendida);
            default -> "";
        };
    }
    public ReadOnlyStringWrapper get(String attr) {
        String str = getStr(attr);
        return new ReadOnlyStringWrapper(str);
    }
}
