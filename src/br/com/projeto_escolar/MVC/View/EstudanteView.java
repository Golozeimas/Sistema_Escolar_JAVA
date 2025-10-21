package br.com.projeto_escolar.MVC.View;

import br.com.projeto_escolar.MVC.Controller.EstudanteController;
import br.com.projeto_escolar.MVC.Model.Estudante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Optional;

public class EstudanteView extends JPanel {
    private final EstudanteController controller;

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField tfId;
    private JTextField tfNome;
    private JTextField tfBuscar;

    private JButton btnAdicionar;
    private JButton btnRemover;
    private JButton btnBuscar;
    private JButton btnRefresh;
    private JButton btnOrdenar;

    public EstudanteView(EstudanteController controller) {
        this.controller = controller;
        initComponents();
        carregarTabelaComTodos();
    }

    private void initComponents() {
        setLayout(new BorderLayout(8,8));

        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        topPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        tfId = new JTextField(6);
        topPanel.add(tfId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        topPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        tfNome = new JTextField(20);
        topPanel.add(tfNome, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        topPanel.add(new JLabel("Buscar (nome):"), gbc);
        gbc.gridx = 1;
        tfBuscar = new JTextField(20);
        topPanel.add(tfBuscar, gbc);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        btnAdicionar = new JButton("Adicionar");
        btnRemover = new JButton("Remover por ID");
        btnBuscar = new JButton("Buscar");
        btnRefresh = new JButton("Refresh");
        btnOrdenar = new JButton("Ordenar por Nome");

        btnPanel.add(btnAdicionar);
        btnPanel.add(btnRemover);
        btnPanel.add(btnBuscar);
        btnPanel.add(btnRefresh);
        btnPanel.add(btnOrdenar);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        topPanel.add(btnPanel, gbc);

        add(topPanel, BorderLayout.NORTH);


        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabela somente leitura
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Listeners
        btnAdicionar.addActionListener(e -> onAdicionar());
        btnRemover.addActionListener(e -> onRemover());
        btnBuscar.addActionListener(e -> onBuscar());
        btnRefresh.addActionListener(e -> carregarTabelaComTodos());
        btnOrdenar.addActionListener(e -> onOrdenar());

        // Preencher campos ao selecionar linha
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                Object idObj = table.getValueAt(row, 0);
                Object nomeObj = table.getValueAt(row, 1);
                tfId.setText(String.valueOf(idObj));
                tfNome.setText(String.valueOf(nomeObj));
            }
        });

        // Duplo clique abre detalhes (opcional)
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() >= 0) {
                    int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
                    Optional<Estudante> opt = controller.obterPorIndice(id);
                    opt.ifPresent(est -> JOptionPane.showMessageDialog(EstudanteView.this,
                            "Detalhes:\nID: " + est.getId() + "\nNome: " + est.getNome(),
                            "Estudante", JOptionPane.INFORMATION_MESSAGE));
                }
            }
        });
    }

    private void onAdicionar() {
        String idText = tfId.getText().trim();
        String nome = tfNome.getText().trim();

        if (idText.isEmpty() || nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha ID e Nome.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            controller.adicionarEstudante(id, nome);
            carregarTabelaComTodos();
            limparCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID deve ser numérico.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onRemover() {
        String idText = tfId.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o ID para remover.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int id = Integer.parseInt(idText);
            int confirm = JOptionPane.showConfirmDialog(this, "Remover estudante com ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                controller.removerEstudantePorId(id);
                carregarTabelaComTodos();
                limparCampos();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID deve ser numérico.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onBuscar() {
        String q = tfBuscar.getText().trim();
        if (q.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe texto para buscar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        List<Estudante> resultados = controller.buscarPorNome(q);
        carregarTabela(resultados);
    }

    private void onOrdenar() {
        controller.ordenarPorNome();
        carregarTabelaComTodos();
    }

    private void limparCampos() {
        tfId.setText("");
        tfNome.setText("");
        tfBuscar.setText("");
    }

    private void carregarTabelaComTodos() {
        try {
            List<Estudante> todos = controller.obterTodos();
            carregarTabela(todos);
        } catch (Exception ex) {
            // fallback: buscar por "" pode retornar todos dependendo do model
            carregarTabela(List.of());
            System.err.println("Erro ao carregar estudantes: " + ex.getMessage());
        }
    }

    private void carregarTabela(List<Estudante> lista) {
        tableModel.setRowCount(0);
        for (Estudante e : lista) {
            tableModel.addRow(new Object[]{e.getId(), e.getNome()});
        }
    }
}

