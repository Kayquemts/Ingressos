package com.example.sistemaingressos.database;


import com.example.sistemaingressos.models.ClienteModel;
import com.example.sistemaingressos.models.SalaModel;

import java.sql.*;

import static com.example.sistemaingressos.telas.DadosClienteController.cliente;

public class ClienteDAO {

    public static void atualizarCliente(ClienteModel cliente) {
        try {
            // Primeiro, recupera as informações atuais do cliente, exceto a senha
            String selectSql = "SELECT * FROM CLIENTE WHERE cpf = ?";
            Connection con = Conexao.getConexao();
            PreparedStatement selectPs = con.prepareStatement(selectSql);
            selectPs.setString(1, cliente.getCpf());  // Define o valor do parâmetro para o CPF

            ResultSet result = selectPs.executeQuery();
            if (result.next()) {
                // Atualiza as informações do cliente, exceto a senha
                String updateSql = "UPDATE CLIENTE SET idade = ?, nome = ?, estudante = ? WHERE cpf = ?";
                PreparedStatement updatePs = con.prepareStatement(updateSql);

                updatePs.setInt(1, cliente.getIdade());
                updatePs.setString(2, cliente.getNome());
                updatePs.setBoolean(3, cliente.isEstudante());
                updatePs.setString(4, cliente.getCpf());  // Define o valor do parâmetro para o CPF

                updatePs.executeUpdate();

                updatePs.close();
            }

            selectPs.close();
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addCliente(String senha) {
        boolean sucesso = false;

        try {
            String sql = "INSERT INTO CLIENTE (nome, idade, estudante, cpf, senha) VALUES (?, ?, ?, ?, ?)";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNome());
            ps.setInt(2, cliente.getIdade());
            ps.setBoolean(3, cliente.isEstudante());


            ps.setString(4, cliente.getCpf());
            ps.setString(5, senha);

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

    public static boolean clienteExistePorCpf(String cpf) {
        boolean existe = false;

        try {
            String sql = "SELECT COUNT(*) AS total FROM CLIENTE WHERE cpf = ?";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cpf); // Define o valor do parâmetro para a consulta

            ResultSet result = ps.executeQuery();
            if (result.next()) {
                int total = result.getInt("total");
                existe = (total > 0);
            }

            ps.close();
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return existe;
    }




    public static ClienteModel getClienteByCpfAndSenha(String cpf, String senha) {
        ClienteModel cliente = null;

        try {
            String sql = "SELECT * FROM cliente WHERE cpf = ? AND senha = ?";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cpf); // Define o valor do parâmetro para o CPF
            ps.setString(2, senha); // Define o valor do parâmetro para a senha

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
