package com.example.sistemaingressos.database;


import com.example.sistemaingressos.models.ClienteModel;
import com.example.sistemaingressos.models.SalaModel;

import java.sql.*;

import static com.example.sistemaingressos.telas.DadosClienteController.cliente;

public class ClienteDAO {
    /*
    public static void carregarClient(){
        try {
            String sql = "SELECT * FROM CLIENTE WHERE id = ? AND nome = ?";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cliente.get);        // Define o valor do parâmetro para o id
            ps.setString(2, clienteNome);   // Define o valor do parâmetro para o nome

            ResultSet result = ps.executeQuery();
            if (result.next()) {
                int id = result.getInt("id");
                boolean estudante = result.getBoolean("estudante");
                int idade = result.getInt("idade");
                String nome = result.getString("nome");

                //cliente = new ClienteModel(id, estudante, idade, nome);
            }

            ps.close();
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }*/

    public boolean addCliente() {
        boolean sucesso = false;

        try {
            String sql = "INSERT INTO CLIENTE (nome, idade, estudante, cpf) VALUES (?, ?, ?, ?)";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNome());
            ps.setInt(2, cliente.getIdade());
            ps.setBoolean(3, cliente.isEstudante());

            ps.setString(4, cliente.getCpf());

            int linhasAfetadas = ps.executeUpdate();
            if (linhasAfetadas > 0) {
                sucesso = true;
            }

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sucesso;
    }

    public ClienteModel getClienteByCpf(String cpf) {
        ClienteModel cliente = null;

        try {
            String sql = "SELECT * FROM CLIENTE WHERE cpf = ?";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cpf); // Define o valor do parâmetro para a consulta

            ResultSet result = ps.executeQuery();
            if (result.next()) {
                String cpfResult = result.getString("cpf");
                boolean estudante = result.getBoolean("estudante");
                int idade = result.getInt("idade");
                String nome = result.getString("nome");

                cliente = new ClienteModel(nome, idade, estudante, cpfResult);
            }

            ps.close();
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cliente;
    }


}
