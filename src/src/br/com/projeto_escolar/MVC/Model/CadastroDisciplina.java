package br.com.projeto_escolar.MVC.Model;

import java.util.HashSet;
import java.util.Set;

public class CadastroDisciplina {
    private Set<Disciplina> disciplinas = new HashSet<>();

    public void adicionarDisciplina(Disciplina d){
        disciplinas.add(d);
    }

    public boolean verificarDisciplina(String codigo){
        for (Disciplina d : disciplinas){
            if (d.getCodigo().equals(codigo)){
             return true;
            }
        }
        return false;
    }

}
