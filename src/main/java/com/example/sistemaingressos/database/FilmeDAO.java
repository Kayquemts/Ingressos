package com.example.sistemaingressos.database;

import com.example.sistemaingressos.models.FilmeModel;

import java.sql.*;

import static com.example.sistemaingressos.models.FilmeModel.filmes;

public class FilmeDAO {

    public static void carregarFilmes(){
        try {
            String sql = "SELECT * FROM filmes";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet result = ps.executeQuery();
            while (result.next()) {
                FilmeModel f = new FilmeModel(result.getString("nome"), result.getString("genero"),
                        result.getInt("duracao"), result.getInt("faixa_etaria"));
                filmes.put(f.getNome(), f);
            }

            ps.close();
            result.close();
        } catch (SQLException e) {
            System.err.println("Erro ao executar consulta SQL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void adicionarFilme(FilmeModel filme){
        //nome, genero, duracao, faxa_estaria
        try {
            String sql = "INSERT INTO filmes values (?, ?, ?, ?)";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, filme.getNome());
            ps.setString(2, filme.getGenero());
            ps.setInt(3, filme.getDuracao());
            ps.setInt(4, filme.getFaixaEtaria());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException ignored) {

        }
    }

    public static void editarFilme(FilmeModel novoFilme){
        try {
            String sql = "UPDATE filmes SET genero = ?, duracao = ?, faixa_etaria = ? WHERE nome = ? ";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, novoFilme.getGenero());
            ps.setInt(2, novoFilme.getDuracao());
            ps.setInt(3, novoFilme.getFaixaEtaria());
            ps.setString(4, novoFilme.getNome());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException ignored) {
        }
    }

    public static void deletarFilme(FilmeModel deletarFilme){
        try {
            String sql = "DELETE FROM filmes WHERE nome = ? ";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, deletarFilme.getNome());

            ps.execute();
            ps.close();
        } catch (SQLException ignored) {

        }

    }

}
