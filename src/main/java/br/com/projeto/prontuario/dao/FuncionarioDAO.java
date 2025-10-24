package br.com.projeto.prontuario.dao;

import br.com.projeto.prontuario.config.DatabaseConnection;

import java.sql.*;

public class FuncionarioDAO {

    public Integer buscarIdPorCpf(String cpf) throws SQLException {
        String sql = "SELECT idFuncionario FROM Funcionario WHERE cpf = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idFuncionario");
                }
                return null;
            }
        }
    }

    public boolean existeFuncionario(String cpf) throws SQLException {
        return buscarIdPorCpf(cpf) != null;
    }
}
