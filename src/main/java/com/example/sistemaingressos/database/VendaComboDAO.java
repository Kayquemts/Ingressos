package com.example.sistemaingressos.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.sistemaingressos.models.IngressoModel;

public class VendaComboDAO {



    public static void adicionarIngressoCombo(int idCombo, int idVenda) throws SQLException {
        System.out.println("ID COMBO: "+idCombo);
        System.out.println("ID VENDA: "+idVenda);


        String sql = "INSERT INTO venda_combo (id_venda, id_combos) VALUES (?, ?)";

        try {
            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idVenda);
            ps.setInt(2, idCombo);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






}
