package br.com.projeto_escolar.MVC.View;

import br.com.projeto_escolar.MVC.Controller.DisciplinaController;
import br.com.projeto_escolar.MVC.Model.Disciplina;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Collection;

public class DisciplinaView extends JPanel {
    private final DisciplinaController controller;

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tfCodigo;
    private JTextField tfNome;
    private JButton btnAdicionar, btnRemover, btnRefresh;

    public DisciplinaView(DisciplinaController controller) {
        this.controller = controller;
        initComponents();
        carregarTabela();
    }

    private void initComponents() {
        setLayout(new BorderLayout(8, 8));

        // Painel superior
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Código:"), gbc);
        gbc.gridx = 1;
        tfCodigo = new JTextField(10);
        formPanel.add(tfCodigo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        tfNome = new JTextField(20);
        formPanel.add(tfNome, gbc);

        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        btnAdicionar = new JButton("Adicionar");
        btnRemover = new JButton("Remover");
        btnRefresh = new JButton("Atualizar");

        buttonPanel.add(btnAdicionar);
        buttonPanel.add(btnRemover);
        buttonPanel.add(btnRefresh);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.NORTH);

        // Tabela
        tableModel = new DefaultTableModel(new Object[]{"Código", "Nome"}, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Ações
        btnAdicionar.addActionListener(e -> adicionarDisciplina());
        btnRemover.addActionListener(e -> removerDisciplina());
        btnRefresh.addActionListener(e -> carregarTabela());

        // Preencher campos ao selecionar linha
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                tfCodigo.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
                tfNome.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
            }
        });
    }

    private void adicionarDisciplina() {
        String codigo = tfCodigo.getText().trim();
        String nome = tfNome.getText().trim();

        if (codigo.isEmpty() || nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha código e nome!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        controller.adicionarDisciplina(codigo, nome);
        carregarTabela();
        limparCampos();
    }

    private void removerDisciplina() {
        String codigo = tfCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o código para remover!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int op = JOptionPane.showConfirmDialog(this, "Remover disciplina " + codigo + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            controller.removerDisciplina(codigo);
            carregarTabela();
            limparCampos();
        }
    }

    private void carregarTabela() {
        tableModel.setRowCount(0);
        Collection<Disciplina> disciplinas = controller.obterTodas();
        for (Disciplina d : disciplinas) {
            tableModel.addRow(new Object[]{d.getCodigo(), d.getNomeDisciplina()});
        }
    }

    private void limparCampos() {
        tfCodigo.setText("");
        tfNome.setText("");
    }
}

