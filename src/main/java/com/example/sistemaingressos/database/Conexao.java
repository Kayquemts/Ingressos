package com.example.sistemaingressos.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String url = "jdbc:mysql://localhost:3306/sistemaingressos";
    private static final String user = "root";
    private static final String senha = "admin123";

    private static Connection conn;

    public static Connection getConexao() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (conn == null) {
                conn = DriverManager.getConnection(url, user, senha);
                return conn;
            } else {
                return conn;
            }
        } catch (SQLException e) {
            e.getSQLState();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("teste : " + e);
            return null;
        }
    }

}
