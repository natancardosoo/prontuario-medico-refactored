package br.com.projeto.prontuario.dao;

import br.com.projeto.prontuario.config.DatabaseConnection;
import br.com.projeto.prontuario.model.Paciente;

import java.sql.*;

public class PacienteDAO {

    public Paciente buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM Paciente WHERE cpf = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Paciente(
                            rs.getString("cpf"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("data_nasc"),
                            rs.getString("celular"),
                            rs.getString("endereco"),
                            rs.getString("cidade"),
                            rs.getString("cep"),
                            rs.getInt("nr_SUS"),
                            rs.getString("genero")
                    );
                }
                return null;
            }
        }
    }

    public boolean existeCpf(String cpf) throws SQLException {
        return buscarPorCpf(cpf) != null;
    }
}
