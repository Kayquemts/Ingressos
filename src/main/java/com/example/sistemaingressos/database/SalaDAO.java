package com.example.sistemaingressos.database;

import com.example.sistemaingressos.models.SalaModel;
import java.sql.*;

import static com.example.sistemaingressos.models.SalaModel.salas;

public class SalaDAO {
    public static void carregarSalas() {
        try {
            String sql = "SELECT * FROM salas";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet result = ps.executeQuery();
            while (result.next()) {
                SalaModel sala = new SalaModel(result.getInt("id"), result.getInt("qnt_max_pessoas"));
                salas.put(sala.getId(), sala);
            }

            ps.close();
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void adicionarSala(SalaModel sala) {
        try {
            String sql = "INSERT INTO salas values(?, ?)";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, sala.getId());
            ps.setInt(2, sala.getQntMaxPessoas());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException ignored) {

        }
    }

    public static void editarSala(SalaModel novaSala) {
        try {
            String sql = "UPDATE salas SET qnt_max_pessoas = ? WHERE id = ?";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, novaSala.getQntMaxPessoas());
            ps.setInt(2, novaSala.getId());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException ignored) {

        }
    }

    public static void deletarSala(SalaModel deletarSala) {
        try {
            String sql = "DELETE FROM salas WHERE id = ?";

            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, deletarSala.getId());

            ps.execute();
            ps.close();
        } catch (SQLException ignored) {

        }
    }

}