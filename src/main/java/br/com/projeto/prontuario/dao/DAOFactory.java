package br.com.projeto.prontuario.dao;

public class DAOFactory {

    private DAOFactory() {

    }

    public static PacienteDAO getPacienteDAO() {
        return new PacienteDAO();
    }

    public static FuncionarioDAO getFuncionarioDAO() {
        return new FuncionarioDAO();
    }

    public static AtendimentoDAO getAtendimentoDAO() {
        return new AtendimentoDAO();
    }

    public static ProntuarioDAO getProntuarioDAO() {
        return new ProntuarioDAO();
    }
}
