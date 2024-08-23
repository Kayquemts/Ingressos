package com.example.sistemaingressos.database;

import com.example.sistemaingressos.models.ComboModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.sistemaingressos.models.ComboModel.combos;

public class ComboDAO {
    public static List<ComboModel> carregarCombos() {
        //List<ComboModel> combos = new ArrayList<>();

        try {
            Connection con = Conexao.getConexao();
            PreparedStatement st = con.prepareStatement("SELECT * FROM combos");
            ResultSet result = st.executeQuery();

            while (result.next()) {
                int id_combo = result.getInt("id_combo");
                String itens_combo = result.getString("itens_combo");
                double preco_combo = result.getDouble("preco_combo");

                ComboModel combo = new ComboModel(id_combo, itens_combo, preco_combo);
                combos.add(combo);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Melhore o tratamento de exceções conforme necessário
        }

        return combos;

    }

    public static int adicionarCombo(ComboModel combo) {
        String sql = "INSERT INTO combos (itens_combo, preco_combo) VALUES (?, ?)";

        try {
            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, combo.getItens_combo());
            ps.setDouble(2, combo.getPreco_combo());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
            ps.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void editarCombo(ComboModel novoCombo) {
        try {
            String sql = "UPDATE combos set itens_combo = ?, preco_combo = ? where id_combo = ?";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, novoCombo.getItens_combo());
            ps.setDouble(2, novoCombo.getPreco_combo());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ignored) {

        }
    }

    public static void deletarCombo(ComboModel deletarCombo){
        try{
            String sql = "Delete from combo where id_combo = ? ";
            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, deletarCombo.getId());
            ps.execute();
            ps.close();
        }catch (SQLException ignored){
        }
    }


}
