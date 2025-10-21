package br.com.projeto_escolar.MVC.Model;

import java.util.HashMap;

public class HistoricoNotas {
    HashMap<Integer, Matricula> mapaDasMatriculas = new HashMap<>();

    public void adicionarMatricula(int idEstudante, String codigoDaDisciplina, double nota){
        mapaDasMatriculas.put(idEstudante, new Matricula(codigoDaDisciplina, nota));
    }

}
