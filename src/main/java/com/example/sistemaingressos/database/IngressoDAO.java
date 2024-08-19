package com.example.sistemaingressos.database;

import java.sql.*;

import com.example.sistemaingressos.models.FilmeVendido;
import com.example.sistemaingressos.models.IngressoModel;
import java.util.ArrayList;

public class IngressoDAO {

    public static ArrayList<Integer> buscarCadeirasOcupadas(int sessaoId) {
        ArrayList<Integer> cadeiras = new ArrayList<>();
        try {
            String sql = "SELECT * FROM ingressos WHERE sessao_id = ? ";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, sessaoId);
            ResultSet result = ps.executeQuery();


            while (result.next()) {
                int cadeira_id = result.getInt("cadeira_id");
                cadeiras.add(cadeira_id);
            }
            ps.close();
        } catch (SQLException ignored) {

        }
        return cadeiras;
    }

    public static ArrayList<FilmeVendido> buscarFilmeMaisVendidos() {
        ArrayList<FilmeVendido> vendas = new ArrayList<>();
        try {
            String sql = "SELECT filme, COUNT(*) AS quantidade \n" +
                    "FROM ingressos\n" +
                    "GROUP BY filme\n" +
                    "ORDER BY quantidade DESC;";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet result = ps.executeQuery();



            while (result.next()) {
                FilmeVendido filme = new FilmeVendido(result.getString("filme"), result.getInt("quantidade"));
                vendas.add(filme);
            }
            ps.close();
            return vendas;
        } catch (SQLException ignored) {
            return vendas;
        }
    }

    public static void addIngresso(IngressoModel novoIngresso) {
        try {
            String sql = "INSERT INTO ingressos (filme, sessao_id, sala_id, cadeira_id, cadeira_especial," +
                    " venda_id, preco_final) values(?,?,?,?,?,?,?)";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, novoIngresso.getFilme());
            ps.setInt(2, novoIngresso.getSessaoId());
            ps.setInt(3, novoIngresso.getSalaId());
            ps.setInt(4, novoIngresso.getCadeiraId());
            ps.setBoolean(5, novoIngresso.getCadeiraEspecial());
            ps.setInt(6, novoIngresso.getVendaId());
            ps.setDouble(7, novoIngresso.getPrecoFinal());


            ps.executeUpdate();
            ps.close();
        } catch (SQLException ignored) {

        }
    }

}
