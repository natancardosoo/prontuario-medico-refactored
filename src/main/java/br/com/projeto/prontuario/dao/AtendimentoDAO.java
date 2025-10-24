package br.com.projeto.prontuario.dao;

import br.com.projeto.prontuario.config.DatabaseConnection;

import java.sql.*;

public class AtendimentoDAO {

    public Integer buscarIdPorCpfPaciente(String cpf) throws SQLException {
        String sql = "SELECT idAtendimento FROM Atendimento WHERE Paciente_cpf = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                Integer id = null;
                while (rs.next()) {
                    id = rs.getInt("idAtendimento");
                }
                return id;
            }
        }
    }
}
