package br.com.projeto_escolar.MVC.Controller;

import br.com.projeto_escolar.MVC.Model.*;

public class MainController {
    private ListaEstudantes listaEstudantes;
    private CadastroDisciplina cadastroDisciplina;
    private HistoricoNotas historicoNotas;
    private GeradorRelatorio geradorRelatorio;

    private EstudanteController estudanteController;
    private DisciplinaController disciplinaController;
    private MatriculaController matriculaController;

    public MainController() {
        // Inicializa os Models
        listaEstudantes = new ListaEstudantes();
        cadastroDisciplina = new CadastroDisciplina();
        historicoNotas = new HistoricoNotas(listaEstudantes);

        // Carrega dados dos arquivos CSV
        LeitorDataBase.lerArqEstudante("./data/estudantes.csv", listaEstudantes);
        LeitorDataBase.lerArqDisciplina("./data/disciplinas.csv", cadastroDisciplina);
        LeitorDataBase.lerArqMatricula("./data/matriculas.csv", historicoNotas);

        // Inicializa os Controllers
        estudanteController = new EstudanteController(listaEstudantes);
        disciplinaController = new DisciplinaController(cadastroDisciplina);
        matriculaController = new MatriculaController(historicoNotas, listaEstudantes, cadastroDisciplina);

        // Inicializa o Gerador de Relatório
        geradorRelatorio = new GeradorRelatorio(listaEstudantes, cadastroDisciplina, historicoNotas);
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


    public boolean gerarRelatorioCompleto() {
        return geradorRelatorio.gerarRelatorio("./joaos/output.txt");
    }

    public boolean gerarRelatorioResumido() {
        return geradorRelatorio.gerarRelatorioResumido("./joaos/output_resumido.txt");
    }

    public boolean gerarRelatorioPersonalizado(String caminho) {
        return geradorRelatorio.gerarRelatorio(caminho);
    }
}