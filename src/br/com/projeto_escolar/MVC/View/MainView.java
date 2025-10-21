package br.com.projeto_escolar.MVC.View;

import javax.swing.*;
import java.awt.*;
import br.com.projeto_escolar.MVC.Controller.MainController;

public class MainView extends JFrame {

    private JTabbedPane tabbedPane;
    private EstudanteView estudanteView;
    private DisciplinaView disciplinaView;
    private MatriculaView matriculaView;
    private MainController controller;

    public MainView(MainController controller) {
        this.controller = controller;

        setTitle("Sistema Escolar - MVC com Swing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Menu superior com botão de relatório
        JPanel topPanel = criarPainelSuperior();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Abas do sistema
        tabbedPane = new JTabbedPane();

        estudanteView = new EstudanteView(controller.getEstudanteController());
        disciplinaView = new DisciplinaView(controller.getDisciplinaController());
        matriculaView = new MatriculaView(controller.getMatriculaController());

        tabbedPane.addTab("Estudantes", estudanteView);
        tabbedPane.addTab("Disciplinas", disciplinaView);
        tabbedPane.addTab("Matrículas", matriculaView);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel criarPainelSuperior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBackground(new Color(70, 130, 180)); // Azul

        JLabel titulo = new JLabel("Sistema de Gerenciamento Escolar");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 16)); // isso aqui modifica a font das letras

        JButton btnRelatorio = new JButton("Gerar Relatório");
        btnRelatorio.setFont(new Font("Arial", Font.BOLD, 12));
        btnRelatorio.setToolTipText("Gera o arquivo output.txt com relatório completo");

        JButton btnRelatorioResumido = new JButton("Relatório Resumido");
        btnRelatorioResumido.setFont(new Font("Arial", Font.PLAIN, 12));
        btnRelatorioResumido.setToolTipText("Gera relatório resumido com estatísticas principais");

        // Ação do botão de relatório completo
        btnRelatorio.addActionListener(e -> gerarRelatorioCompleto());

        // Ação do botão de relatório resumido
        btnRelatorioResumido.addActionListener(e -> gerarRelatorioResumido());

        panel.add(titulo);
        panel.add(Box.createHorizontalStrut(200)); // Espaçamento
        panel.add(btnRelatorioResumido);
        panel.add(btnRelatorio);

        return panel;
    }

    private void gerarRelatorioCompleto() {
        // Exibe diálogo de loading
        JDialog loadingDialog = criarDialogoLoading();
        loadingDialog.setVisible(true);

        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                return controller.gerarRelatorioCompleto();
            }

            @Override
            protected void done() {
                loadingDialog.dispose();
                try {
                    boolean sucesso = get();
                    if (sucesso) {
                        JOptionPane.showMessageDialog(
                                MainView.this,
                                "Relatório gerado com sucesso!\n\nArquivo: output.txt\nLocalização: Raiz do projeto",
                                "Sucesso",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                                MainView.this,
                                "Erro ao gerar relatório.\nVerifique o console para mais detalhes.",
                                "Erro",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                } catch (Exception ex) {
                    loadingDialog.dispose();
                    JOptionPane.showMessageDialog(
                            MainView.this,
                            "Erro inesperado: " + ex.getMessage(),
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };

        worker.execute();
    }

    private void gerarRelatorioResumido() {
        JDialog loadingDialog = criarDialogoLoading();
        loadingDialog.setVisible(true);

        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                return controller.gerarRelatorioResumido();
            }

            @Override
            protected void done() {
                loadingDialog.dispose();
                try {
                    boolean sucesso = get();
                    if (sucesso) {
                        JOptionPane.showMessageDialog(
                                MainView.this,
                                "Relatório resumido gerado com sucesso!\n\nArquivo: output_resumido.txt\nLocalização: Raiz do projeto",
                                "Sucesso",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                                MainView.this,
                                "Erro ao gerar relatório.\nVerifique o console para mais detalhes.",
                                "Erro",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                } catch (Exception ex) {
                    loadingDialog.dispose();
                    JOptionPane.showMessageDialog(
                            MainView.this,
                            "Erro inesperado: " + ex.getMessage(),
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };

        worker.execute();
    }

    private JDialog criarDialogoLoading() {
        JDialog dialog = new JDialog(this, "Gerando Relatório", true);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setSize(300, 100);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("Gerando relatório, aguarde...", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 14));

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);

        panel.add(label, BorderLayout.NORTH);
        panel.add(progressBar, BorderLayout.CENTER);

        dialog.add(panel);

        return dialog;
    }
}