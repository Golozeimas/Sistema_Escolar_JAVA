package br.com.projeto_escolar.MVC.Controller;

import br.com.projeto_escolar.MVC.Model.HistoricoNotas;
import br.com.projeto_escolar.MVC.Model.Matricula;
import br.com.projeto_escolar.MVC.Model.Estudante;
import br.com.projeto_escolar.MVC.Model.ListaEstudantes;
import br.com.projeto_escolar.MVC.Model.CadastroDisciplina;

import java.util.List;
import java.util.Optional;

public class MatriculaController {
    private final HistoricoNotas historico;
    private final ListaEstudantes listaEstudantes;
    private final CadastroDisciplina cadastroDisciplina;

    public MatriculaController(HistoricoNotas historico, ListaEstudantes listaEstudantes, CadastroDisciplina cadastroDisciplina) {
        this.historico = historico;
        this.listaEstudantes = listaEstudantes;
        this.cadastroDisciplina = cadastroDisciplina;
    }

    public void adicionarMatricula(int idEstudante, String codigoDisciplina, double nota) {
        historico.adicionarMatricula(idEstudante, codigoDisciplina, nota);
    }

    public List<Matricula> obterMatriculas(int idEstudante) {
        return historico.obteMatriculas(idEstudante);
    }

    public Optional<Double> obterNota(int idEstudante, String codigoDisciplina) {
        return historico.obterNota(idEstudante, codigoDisciplina);
    }

    public void removerMatricula(int idEstudante, String codigoDisciplina) {
        historico.removerMatricula(idEstudante, codigoDisciplina);
    }

    public double mediaDoEstudante(int idEstudante) {
        return historico.mediaDoEstudante(idEstudante);
    }

    public double mediaDaDisciplina(String codigoDisciplina) {
        return historico.mediaDaDisciplina(codigoDisciplina);
    }

    public List<Estudante> topNEstudantesPorMedia(int N) {
        return historico.topNEstudantesPorMedia(N);
    }
}

