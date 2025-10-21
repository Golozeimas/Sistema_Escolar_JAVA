package br.com.projeto_escolar.MVC.View;

import br.com.projeto_escolar.MVC.Controller.MatriculaController;
import br.com.projeto_escolar.MVC.Model.Matricula;
import br.com.projeto_escolar.MVC.Model.Estudante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MatriculaView extends JPanel {
    private final MatriculaController controller;

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField tfIdEstudante;
    private JTextField tfCodigoDisciplina;
    private JTextField tfNota;
    private JButton btnAdicionar, btnRemover, btnVerMediaEstudante, btnVerMediaDisciplina, btnTop3;

    public MatriculaView(MatriculaController controller) {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(8,8));

        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        topPanel.add(new JLabel("ID Estudante:"), gbc);
        gbc.gridx = 1;
        tfIdEstudante = new JTextField(6);
        topPanel.add(tfIdEstudante, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        topPanel.add(new JLabel("Código Disciplina:"), gbc);
        gbc.gridx = 1;
        tfCodigoDisciplina = new JTextField(10);
        topPanel.add(tfCodigoDisciplina, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        topPanel.add(new JLabel("Nota:"), gbc);
        gbc.gridx = 1;
        tfNota = new JTextField(6);
        topPanel.add(tfNota, gbc);

        // Botões
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        btnAdicionar = new JButton("Adicionar Matrícula");
        btnRemover = new JButton("Remover");
        btnVerMediaEstudante = new JButton("Média Estudante");
        btnVerMediaDisciplina = new JButton("Média Disciplina");
        btnTop3 = new JButton("Top 3 Estudantes");

        btnPanel.add(btnAdicionar);
        btnPanel.add(btnRemover);
        btnPanel.add(btnVerMediaEstudante);
        btnPanel.add(btnVerMediaDisciplina);
        btnPanel.add(btnTop3);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        topPanel.add(btnPanel, gbc);

        add(topPanel, BorderLayout.NORTH);

        // Tabela
        tableModel = new DefaultTableModel(new Object[]{"Código Disciplina", "Nota"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Eventos
        btnAdicionar.addActionListener(e -> adicionarMatricula());
        btnRemover.addActionListener(e -> removerMatricula());
        btnVerMediaEstudante.addActionListener(e -> mostrarMediaEstudante());
        btnVerMediaDisciplina.addActionListener(e -> mostrarMediaDisciplina());
        btnTop3.addActionListener(e -> mostrarTop3());
    }

    private void adicionarMatricula() {
        try {
            int id = Integer.parseInt(tfIdEstudante.getText().trim());
            String cod = tfCodigoDisciplina.getText().trim();
            double nota = Double.parseDouble(tfNota.getText().trim());

            controller.adicionarMatricula(id, cod, nota);
            carregarTabela(id);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: verifique os campos.\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerMatricula() {
        try {
            int id = Integer.parseInt(tfIdEstudante.getText().trim());
            String cod = tfCodigoDisciplina.getText().trim();
            controller.removerMatricula(id, cod);
            carregarTabela(id);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao remover.\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarMediaEstudante() {
        try {
            int id = Integer.parseInt(tfIdEstudante.getText().trim());
            double media = controller.mediaDoEstudante(id);
            JOptionPane.showMessageDialog(this, "Média do estudante: " + media, "Média", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Informe o ID corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarMediaDisciplina() {
        String cod = tfCodigoDisciplina.getText().trim();
        if (cod.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o código da disciplina.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        double media = controller.mediaDaDisciplina(cod);
        JOptionPane.showMessageDialog(this, "Média da disciplina " + cod + ": " + media, "Média", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarTop3() {
        List<Estudante> top = controller.topNEstudantesPorMedia(3);
        StringBuilder sb = new StringBuilder("Top 3 Estudantes:\n");
        int i = 1;
        for (Estudante e : top) {
            sb.append(i++).append(") ").append(e.getNome()).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Ranking", JOptionPane.INFORMATION_MESSAGE);
    }

    private void carregarTabela(int idEstudante) {
        tableModel.setRowCount(0);
        List<Matricula> matriculas = controller.obterMatriculas(idEstudante);
        for (Matricula m : matriculas) {
            tableModel.addRow(new Object[]{m.getCodigoDisciplina(), m.getNota()});
        }
    }
}
