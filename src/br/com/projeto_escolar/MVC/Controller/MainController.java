package br.com.projeto_escolar.MVC.Controller;

import br.com.projeto_escolar.MVC.Model.*;

public class MainController {
    private ListaEstudantes listaEstudantes;
    private CadastroDisciplina cadastroDisciplina;
    private HistoricoNotas historicoNotas;

    private EstudanteController estudanteController;
    private DisciplinaController disciplinaController;
    private MatriculaController matriculaController;

    public MainController() {
        ListaEstudantes listaEstudantes = new ListaEstudantes();
        CadastroDisciplina cadastroDisciplina = new CadastroDisciplina();
        HistoricoNotas historicoNotas = new HistoricoNotas(listaEstudantes);

        LeitorDataBase.lerArqEstudante("./data/estudantes.csv", listaEstudantes);
        LeitorDataBase.lerArqDisciplina("./data/disciplinas.csv", cadastroDisciplina);
        LeitorDataBase.lerArqMatricula("./data/matriculas.csv", historicoNotas);

        estudanteController = new EstudanteController(listaEstudantes);
        disciplinaController = new DisciplinaController(cadastroDisciplina);
        matriculaController = new MatriculaController(historicoNotas, listaEstudantes, cadastroDisciplina);

        this.listaEstudantes = listaEstudantes;
        this.cadastroDisciplina = cadastroDisciplina;
        this.historicoNotas = historicoNotas;
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
