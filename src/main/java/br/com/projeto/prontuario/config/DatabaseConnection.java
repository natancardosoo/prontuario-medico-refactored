package br.com.projeto.prontuario.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "bd_UBS";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    private DatabaseConnection() {
        // impede instanciação
    }

    public static Connection getConnection() throws SQLException {
        String url = String.format(
                "jdbc:mysql://%s:%s/%s?useTimezone=true&serverTimezone=UTC",
                HOST, PORT, DATABASE
        );
        return DriverManager.getConnection(url, USER, PASSWORD);
    }
}
