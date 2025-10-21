package br.com.projeto_escolar.MVC.Controller;
import br.com.projeto_escolar.MVC.Model.*;

public class MainController{
        private ListaEstudantes listaEstudantes;
        private CadastroDisciplina cadastroDisciplina;
        private HistoricoNotas historicoNotas;

        private EstudanteController estudanteController;
        private DisciplinaController disciplinaController;
        private MatriculaController matriculaController;

        public MainController() {
            LeitorDataBase leitor = new LeitorDataBase();
            listaEstudantes = leitor.carregarEstudantes();
            cadastroDisciplina = leitor.carregarDisciplinas();
            historicoNotas = leitor.carregarMatriculas(listaEstudantes, cadastroDisciplina);

            estudanteController = new EstudanteController(listaEstudantes);
            disciplinaController = new DisciplinaController(cadastroDisciplina);
            matriculaController = new MatriculaController(historicoNotas, listaEstudantes, cadastroDisciplina);
        }

        public EstudanteController getEstudanteController() {
            return estudanteController;
        }

        public DisciplinaController getDisciplinaController() {
            return disciplinaController;
        }

        public MatriculaController getMatriculaController() {
            return matriculaController;
        }
    }

}
