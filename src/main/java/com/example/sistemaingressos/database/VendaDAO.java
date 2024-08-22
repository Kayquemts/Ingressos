package com.example.sistemaingressos.database;

import com.example.sistemaingressos.models.FilmeVendido;
import com.example.sistemaingressos.models.VendaModel;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class VendaDAO {
        public static ArrayList<FilmeVendido> buscarVendasNoDia(LocalDate data) {
            ArrayList<FilmeVendido> vendas = new ArrayList<>();
            try {
                System.out.println("teste");
                Connection con = Conexao.getConexao();
                String sql = "SELECT filme, COUNT(*) AS quantidade" +
                        " FROM ingressos i JOIN vendas v ON i.venda_id = v.id WHERE v.data = ? GROUP BY filme";


                //System.out.println(Conexao.isConnectionValid(con));
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
            String sql = "INSERT INTO vendas (qnt_ingressos, data, cpf, preco_total) values (?, ?, ?, ?)";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, novaVenda.getQntIngressos());
            ps.setDate(2, Date.valueOf(novaVenda.getData()));
            ps.setString(3, novaVenda.getCpf());
            ps.setDouble(4, novaVenda.getPrecoTotal());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
            ps.close();
        } catch (SQLException e) {
            System.err.println("Erro ao executar consulta SQL: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }


}

