package br.com.projeto_escolar.MVC.View;
import javax.swing.*;
import java.awt.*;
import br.com.projeto_escolar.MVC.Controller.MainController;

public class MainView extends JFrame {

        private JTabbedPane tabbedPane;
        private EstudanteView estudanteView;
        private DisciplinaView disciplinaView;
        private MatriculaView matriculaView;

        public MainView(MainController controller) {
            setTitle("Sistema Escolar - MVC com Swing");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(900, 600);
            setLocationRelativeTo(null);

            tabbedPane = new JTabbedPane();

            estudanteView = new EstudanteView(controller.getEstudanteController());
            disciplinaView = new DisciplinaView(controller.getDisciplinaController());
            matriculaView = new MatriculaView(controller.getMatriculaController());

            tabbedPane.addTab("Estudantes", estudanteView);
            tabbedPane.addTab("Disciplinas", disciplinaView);
            tabbedPane.addTab("Matrículas", matriculaView);

            add(tabbedPane, BorderLayout.CENTER);
        }
    }
