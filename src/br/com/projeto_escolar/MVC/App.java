package br.com.projeto_escolar.MVC;

import br.com.projeto_escolar.MVC.Controller.MainController;
import br.com.projeto_escolar.MVC.View.MainView;

public class App {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            MainController controller = new MainController();
            MainView view = new MainView(controller);
            view.setVisible(true);
        });
    }
}
