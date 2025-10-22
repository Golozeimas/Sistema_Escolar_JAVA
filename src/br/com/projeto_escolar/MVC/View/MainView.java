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

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel topPanel = criarPainelSuperior();
        mainPanel.add(topPanel, BorderLayout.NORTH);

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
        panel.setBackground(new Color(70, 130, 180));

        JLabel titulo = new JLabel("Sistema de Gerenciamento Escolar");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));

        JButton btnRelatorio = new JButton("Gerar Relatório");
        btnRelatorio.setFont(new Font("Arial", Font.BOLD, 12));

        JButton btnRelatorioResumido = new JButton("Resumido");
        btnRelatorioResumido.setFont(new Font("Arial", Font.PLAIN, 12));

        btnRelatorio.addActionListener(e -> {
            btnRelatorio.setEnabled(false);
            btnRelatorio.setText("Gerando...");

            // Executa em thread separada SIMPLES
            new Thread(() -> {
                try {
                    System.out.println("\n INICIANDO GERAÇÃO...\n");
                    boolean sucesso = controller.gerarRelatorioCompleto();

                    // Volta para thread da interface
                    SwingUtilities.invokeLater(() -> {
                        btnRelatorio.setEnabled(true);
                        btnRelatorio.setText("Gerar Relatório");

                        if (sucesso) {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Relatório gerado com sucesso!\n\n" +
                                            "Arquivo: output.txt\n" +
                                            "Localização: Raiz do projeto",
                                    "Sucesso",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        } else {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Erro ao gerar relatório.\n\n" +
                                            "Verifique o console para detalhes.",
                                    "Erro",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    });
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> {
                        btnRelatorio.setEnabled(true);
                        btnRelatorio.setText("Gerar Relatório");
                        JOptionPane.showMessageDialog(
                                this,
                                "Erro: " + ex.getMessage(),
                                "Erro",
                                JOptionPane.ERROR_MESSAGE
                        );
                    });
                    ex.printStackTrace();
                }
            }).start();
        });

        btnRelatorioResumido.addActionListener(e -> {
            btnRelatorioResumido.setEnabled(false);
            btnRelatorioResumido.setText("Gerando...");

            new Thread(() -> {
                try {
                    System.out.println("\nINICIANDO GERAÇÃO RESUMIDA...\n");
                    boolean sucesso = controller.gerarRelatorioResumido();

                    SwingUtilities.invokeLater(() -> {
                        btnRelatorioResumido.setEnabled(true);
                        btnRelatorioResumido.setText("Resumido");

                        if (sucesso) {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Relatório resumido gerado!\n\n" +
                                            "Arquivo: output_resumido.txt\n" +
                                            "Localização: Raiz do projeto",
                                    "Sucesso",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        } else {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Erro ao gerar relatório.",
                                    "Erro",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    });
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> {
                        btnRelatorioResumido.setEnabled(true);
                        btnRelatorioResumido.setText("Resumido");
                    });
                    ex.printStackTrace();
                }
            }).start();
        });

        panel.add(titulo);
        panel.add(Box.createHorizontalStrut(200));
        panel.add(btnRelatorioResumido);
        panel.add(btnRelatorio);

        return panel;
    }
}