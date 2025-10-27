package br.com.projeto.prontuario.view;

import br.com.projeto.prontuario.dao.AtendimentoDAO;
import br.com.projeto.prontuario.dao.FuncionarioDAO;
import br.com.projeto.prontuario.dao.PacienteDAO;
import br.com.projeto.prontuario.dao.ProntuarioDAO;
import br.com.projeto.prontuario.view.components.DialogPersonalizado;
import br.com.projeto.prontuario.view.components.TabelaProntuarios;
import br.com.projeto.prontuario.model.Paciente;
import br.com.projeto.prontuario.model.ProntuarioMedico;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainFrame extends JFrame implements ActionListener {
    PainelAcesso acesso;
    PainelPrincipal menu;
    PainelInformacoes informacoes;
    PainelCriarProntuario prontuario;
    PainelVerProntuarios verProntuarios;
    PainelProntuarioAberto prontuarioAberto;

    AtendimentoDAO atendimentoDAO;
    FuncionarioDAO funcionarioDAO;
    PacienteDAO pacienteDAO;
    ProntuarioDAO prontuarioDAO;

    static String cpfNaoFormatado;
    int confirma = 0;

    public MainFrame() {
        acesso = new PainelAcesso();
        menu = new PainelPrincipal();
        informacoes = new PainelInformacoes();
        prontuario = new PainelCriarProntuario();
        prontuarioAberto = new PainelProntuarioAberto();

        atendimentoDAO = new AtendimentoDAO();
        funcionarioDAO = new FuncionarioDAO();
        pacienteDAO = new PacienteDAO();
        prontuarioDAO = new ProntuarioDAO();

        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/icone.png"))).getImage());
        setSize(600, 600);
        setTitle("Acessar prontuário");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        add(acesso);
        add(menu);
        add(informacoes);
        add(prontuario);
        add(prontuarioAberto);

        acesso.acessar.addActionListener(this);
        menu.buttonSair.addActionListener(this);
        menu.buttonInformacoes.addActionListener(this);
        menu.buttonAbrir.addActionListener(this);
        menu.buttonAnteriores.addActionListener(this);
        informacoes.voltar.addActionListener(this);
        prontuario.abrir.addActionListener(this);
        prontuario.cancelar.addActionListener(this);
        prontuarioAberto.voltar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == acesso.acessar) {

            cpfNaoFormatado = acesso.cpfField.getText().replaceAll("[^0-9]", "");

            verProntuarios = new PainelVerProntuarios();
            add(verProntuarios);
            verProntuarios.voltar.addActionListener(this);
            verProntuarios.tabela.addMouseListener(new MouseInputAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        int rowIndex = verProntuarios.tabela.getSelectedRow();

                        if (rowIndex != -1) {
                            try {
                                String id = (String) verProntuarios.tabela.getValueAt(rowIndex, 0);
                                List<String> informacoes = prontuarioDAO.obterPorId(id);

                                prontuarioAberto.anamnese.setText(informacoes.get(0));
                                prontuarioAberto.planoTerapeutico.setText(informacoes.get(1));
                                prontuarioAberto.encaminhamento.setText(informacoes.get(2));
                                prontuarioAberto.cpfField.setText(informacoes.get(3));

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                            verProntuarios.setVisible(false);
                            prontuarioAberto.setVisible(true);
                            setTitle("Prontuário");
                        }
                    }
                }
            });

            try {
                // busca paciente pelo CPF
                Paciente paciente = pacienteDAO.buscarPorCpf(cpfNaoFormatado);

                if (paciente != null) {
                    DialogPersonalizado.showMessageDialog(this, "Acesso liberado");

                    // Coloca o nome do paciente no menu
                    menu.textoNome.setText(paciente.getNome());

                    // Preenche o painel de informações
                    informacoes.preenchePaciente(paciente);

                    acesso.setVisible(false);
                    menu.setVisible(true);
                    acesso.cpfField.setValue(null);
                    setTitle("Menu principal");

                    // Preenche a tabela de prontuários
                    verProntuarios.tabela.setModel(
                            new TabelaProntuarios(new ArrayList<>(prontuarioDAO.listarPorCpfPaciente(cpfNaoFormatado)))
                    );

                    verProntuarios.atualizaTabela();

                } else {
                    DialogPersonalizado.showMessageDialog(this, "CPF inválido");
                    acesso.cpfField.setValue(null);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                DialogPersonalizado.showMessageDialog(this, "Erro ao acessar o sistema.");
            }
        }

        if (e.getSource() == menu.buttonInformacoes) {
            menu.setVisible(false);
            informacoes.setVisible(true);
            setTitle("Informações do paciente");
        }

        if (e.getSource() == menu.buttonAbrir) {
            menu.setVisible(false);
            prontuario.setVisible(true);
            setTitle("Criar prontuário médico");
        }

        if (e.getSource() == menu.buttonAnteriores) {
            menu.setVisible(false);
            verProntuarios.setVisible(true);
            setTitle("Acessar prontuários anteriores");
        }

        if (e.getSource() == menu.buttonSair) {
            menu.setVisible(false);
            acesso.setVisible(true);
            setTitle("Acessar prontuário");
        }

        if (e.getSource() == informacoes.voltar) {
            informacoes.setVisible(false);
            menu.setVisible(true);
            setTitle("Menu principal");
        }

        if (e.getSource() == verProntuarios.voltar) {
            verProntuarios.setVisible(false);
            menu.setVisible(true);
            setTitle("Menu principal");
        }

        if (e.getSource() == prontuario.abrir) {
            try {
                String cpfFuncionario = prontuario.cpfField.getText().replaceAll("[^0-9]", "");

                Integer idFuncionario = funcionarioDAO.buscarIdPorCpf(cpfFuncionario);

                if (idFuncionario == null) {
                    DialogPersonalizado.showMessageDialog(this, "Erro: CPF do funcionário inválido");
                    prontuario.cpfField.setValue(null);
                    return;
                }

                if (prontuario.anamnese.getText().trim().isEmpty() ||
                        prontuario.planoTerapeutico.getText().trim().isEmpty() ||
                        prontuario.encaminhamento.getText().trim().isEmpty()) {

                    DialogPersonalizado.showMessageDialog(this, "Erro: Campo vazio");
                    return;
                }

                if (confirma == 0) {
                    DialogPersonalizado.showMessageDialog(this, "Confirme os dados");
                    prontuario.cpfField.setValue(null);
                    confirma++;
                    return;
                }

                DialogPersonalizado.showMessageDialog(this, "Prontuário criado");
                confirma = 0;

                int idAtendimento = atendimentoDAO.buscarIdPorCpfPaciente(cpfNaoFormatado);

                ProntuarioMedico prontuarioMedico = new ProntuarioMedico(
                        prontuario.anamnese.getText(),
                        prontuario.planoTerapeutico.getText(),
                        prontuario.encaminhamento.getText(),
                        idFuncionario.toString(),
                        Integer.toString(idAtendimento)
                );

                prontuarioDAO.inserir(prontuarioMedico);

                verProntuarios.tabela.setModel(
                        new TabelaProntuarios(new ArrayList<>(prontuarioDAO.listarPorCpfPaciente(cpfNaoFormatado)))
                );

                verProntuarios.atualizaTabela();

                prontuario.cancelar.doClick();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (e.getSource() == prontuario.cancelar) {
            prontuario.setVisible(false);
            menu.setVisible(true);
            setTitle("Menu principal");
            prontuario.anamnese.setText("");
            prontuario.planoTerapeutico.setText("");
            prontuario.encaminhamento.setText("");
            prontuario.cpfField.setText("");
        }

        if (e.getSource() == prontuarioAberto.voltar) {
            prontuarioAberto.setVisible(false);
            verProntuarios.setVisible(true);
            setTitle("Acessar prontuários anteriores");
        }
    }
}
