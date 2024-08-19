package com.example.sistemaingressos.database;

import com.example.sistemaingressos.models.FilmeVendido;
import com.example.sistemaingressos.models.IngressoModel;
import com.example.sistemaingressos.models.SalaModel;
import com.example.sistemaingressos.models.VendaModel;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class VendaDAO {
    public static ArrayList<FilmeVendido> buscarVendasNoDia(LocalDate data) {
        ArrayList<FilmeVendido> vendas = new ArrayList<>();
        try {
            String sql = "SELECT filme, COUNT(*) AS quantidade" +
                    " FROM ingressos i JOIN vendas v ON i.venda_id = v.id WHERE v.data = ? GROUP BY filme";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(data)); // Convertendo LocalDate para java.sql.Date para usar no PreparedStatement

            ResultSet result = ps.executeQuery();
            while (result.next()) {
                int qntIngressos = result.getInt("quantidade");
                String nome_filme = result.getString("filme");
                FilmeVendido filme = new FilmeVendido(nome_filme, qntIngressos);
                vendas.add(filme);
            }

            ps.close();
            return vendas;
        } catch (SQLException e) {
            e.printStackTrace();
            return vendas;
        }
    }

    public static int addVenda(VendaModel novaVenda) {
        try {
            String sql = "INSERT INTO vendas (qnt_ingressos, data, nome_comprador, preco_total) values (?, ?, ?, ?)";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, novaVenda.getQntIngressos());
            ps.setDate(2, Date.valueOf(novaVenda.getData()));
            ps.setString(3, novaVenda.getNomeComprador());
            ps.setDouble(4, novaVenda.getPrecoTotal());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
            ps.close();
        } catch (SQLException ignored) {

        }
        return -1;
    }


}

