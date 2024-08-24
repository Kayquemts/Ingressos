package com.example.sistemaingressos.models;

import com.example.sistemaingressos.database.ComboDAO;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class ComboModel {

    private int id;
    private String itens_combo;
    private double preco_combo;
    public static ObservableList<ComboModel> combos =  FXCollections.observableArrayList();

    public ComboModel(int id, String itens_combo, double preco_combo) {
        this.id = id;
        this.itens_combo = itens_combo;
        this.preco_combo = preco_combo;
    }

    public ComboModel(String itens_combo, double preco_combo) {
        this.itens_combo = itens_combo;
        this.preco_combo = preco_combo;
    }

    public static void addCombo(ComboModel combo){
        ComboModel.setId(combo, ComboDAO.adicionarCombo(combo));
        combos.add(combo);
    }

    public static void carregarCombo(){
        ComboDAO.carregarCombos();
    }

    public static void editarCombo(ComboModel combo){
        ComboDAO.editarCombo(combo);
    }

    public static void deletarCombo(ComboModel combo){
        combos.remove(combo.getId());
    }

    public int getId() {
        return id;
    }

    public static void setId(ComboModel m,  int id) {
        m.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItens_combo() {
        return itens_combo;
    }

    public void setItens_combo(String itens_combo) {
        this.itens_combo = itens_combo;
    }

    public double getPreco_combo() {
        return preco_combo;
    }

    public void setPreco_combo(double preco_combo) {
        this.preco_combo = preco_combo;
    }

    public ReadOnlyStringWrapper get(String attr) {
        String str = getStr(attr);
        return new ReadOnlyStringWrapper(str);
    }

    public String getStr(String attr) {
        return switch (attr) {
            case "id" -> String.valueOf(this.id);
            case "itens" -> this.itens_combo;
            case "preco" -> String.format("%.2f", this.preco_combo);
            default -> "";
        };
    }

    @Override
    public String toString() {
        return "ComboModel{" +
                "id=" + id +
                ", itens_combo='" + itens_combo + '\'' +
                ", preco_combo=" + preco_combo +
                '}';
    }
}
