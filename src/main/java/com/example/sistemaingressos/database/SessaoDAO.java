package com.example.sistemaingressos.database;

import com.example.sistemaingressos.models.FilmeModel;
import com.example.sistemaingressos.models.SessaoModel;

import java.sql.*;
import java.util.ArrayList;

import static com.example.sistemaingressos.models.FilmeModel.filmes;
import static com.example.sistemaingressos.models.SessaoModel.sessoes;

public class SessaoDAO {
    public static void carregarSessoes() {
        try {
            Connection con = Conexao.getConexao();
            String sql = "SELECT * FROM sessoes";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                FilmeModel f = filmes.get(result.getString("filme"));
                SessaoModel sessao = new SessaoModel(result.getInt("id"), f, result.getInt("hora"),
                        result.getInt("minuto"), result.getInt("sala_id"),
                        result.getDouble("preco"));
                sessoes.add(sessao);
            }
        } catch (SQLException ignored) {

        }
    }

    public static void adicionarSessao(SessaoModel sessao){
        //nome, genero, duracao, faxa_estaria
        try {
            String sql = "INSERT INTO sessoes (filme, hora, minuto, sala_id, preco) values (?, ?, ?, ?, ?)";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, sessao.getStr("filme"));
            ps.setInt(2, sessao.getHora());
            ps.setInt(3, sessao.getMinuto());
            ps.setInt(4, sessao.getSalaId());
            ps.setDouble(5, sessao.getPreco());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException ignored) {

        }
    }

    public static void editarSessao(SessaoModel novaSessao){
        try {
            String sql = "UPDATE sessoes SET filme = ?, hora = ?, minuto = ?, sala_id = ?, preco = ? WHERE id = ? ";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, novaSessao.getStr("filme"));
            ps.setInt(2, novaSessao.getHora());
            ps.setInt(3, novaSessao.getMinuto());
            ps.setInt(4, novaSessao.getSalaId());
            ps.setDouble(5, novaSessao.getPreco());
            ps.setInt(6, novaSessao.getId());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletarSessao(SessaoModel deletarSessao){
        try {
            String sql = "DELETE FROM sessoes WHERE id = ? ";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, deletarSessao.getId());

            ps.execute();
            ps.close();
        } catch (SQLException ignored) {

        }

    }

}
