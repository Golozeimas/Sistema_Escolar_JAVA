package br.com.projeto_escolar.MVC;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import br.com.projeto_escolar.MVC.Controller.MainController;
import br.com.projeto_escolar.MVC.View.MainView;

public class App {

    public static void main(String[] args) {
        // Configura o Look and Feel do sistema operacional
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Não foi possível definir o Look and Feel do sistema.");
        }

        // Executa a aplicação na thread de eventos do Swing
        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("=".repeat(80));
                System.out.println("          SISTEMA DE GERENCIAMENTO ESCOLAR - INICIANDO");
                System.out.println("=".repeat(80));
                System.out.println();

                // Inicializa o controller principal
                System.out.println("► Carregando dados dos arquivos CSV...");
                MainController controller = new MainController();
                System.out.println("✓ Dados carregados com sucesso!");
                System.out.println();

                // Cria e exibe a interface gráfica
                System.out.println("► Inicializando interface gráfica...");
                MainView view = new MainView(controller);
                view.setVisible(true);
                System.out.println("✓ Sistema iniciado com sucesso!");
                System.out.println();
                System.out.println("=".repeat(80));
                System.out.println("  Utilize a interface gráfica para gerenciar o sistema.");
                System.out.println("  Para gerar relatório, clique no botão 'Gerar Relatório'.");
                System.out.println("=".repeat(80));
                System.out.println();

            } catch (Exception e) {
                System.err.println("Erro ao iniciar o sistema:");
                e.printStackTrace();
                System.exit(1);
            }
        });
    }
}