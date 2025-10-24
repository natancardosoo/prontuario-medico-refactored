package br.com.projeto.prontuario.dao;

import br.com.projeto.prontuario.config.DatabaseConnection;
import br.com.projeto.prontuario.model.ProntuarioMedico;
import br.com.projeto.prontuario.model.TabelaEntidade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProntuarioDAO {

    public void inserir(ProntuarioMedico prontuario) throws SQLException {
        String sql = """
            INSERT INTO Prontuario
            (Funcionario_idFuncionario, Atendimento_idAtendimento, anamnese, plano_terapeutico, encaminhamento)
            VALUES (?, ?, ?, ?, ?)
        """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, prontuario.getId_Funcionario());
            ps.setString(2, prontuario.getId_Atendimento());
            ps.setString(3, prontuario.getAnamnese());
            ps.setString(4, prontuario.getPlano_terapeutico());
            ps.setString(5, prontuario.getEncaminhamento());
            ps.executeUpdate();
        }
    }

    public List<TabelaEntidade> listarPorCpfPaciente(String cpf) throws SQLException {
        String sql = """
            SELECT p.idProntuario, f.nome, a.data_atendimento
            FROM Prontuario p
            JOIN Funcionario f ON p.Funcionario_idFuncionario = f.idFuncionario
            JOIN Atendimento a ON p.Atendimento_idAtendimento = a.idAtendimento
            WHERE a.Paciente_cpf = ?
        """;
        List<TabelaEntidade> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new TabelaEntidade(
                            rs.getString("idProntuario"),
                            rs.getString("nome"),
                            formatarData(rs.getString("data_atendimento"))
                    ));
                }
            }
        }
        return lista;
    }

    public List<String> obterPorId(String idProntuario) throws SQLException {
        String sql = """
            SELECT p.anamnese, p.plano_terapeutico, p.encaminhamento, f.cpf
            FROM Prontuario p
            JOIN Funcionario f ON p.Funcionario_idFuncionario = f.idFuncionario
            WHERE p.idProntuario = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idProntuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    List<String> dados = new ArrayList<>();
                    dados.add(rs.getString("anamnese"));
                    dados.add(rs.getString("plano_terapeutico"));
                    dados.add(rs.getString("encaminhamento"));
                    dados.add(rs.getString("cpf"));
                    return dados;
                }
            }
        }
        return List.of();
    }

    private String formatarData(String data) {
        if (data == null) return "";
        String digits = data.replaceAll("[^0-9]", "");
        if (digits.length() < 8) return data;
        String ano = digits.substring(0, 4);
        String mes = digits.substring(4, 6);
        String dia = digits.substring(6, 8);
        return String.format("%s/%s/%s", dia, mes, ano);
    }
}
