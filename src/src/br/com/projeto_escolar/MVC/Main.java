package br.com.projeto_escolar.MVC;

import br.com.projeto_escolar.MVC.Model.CadastroDisciplina;
import br.com.projeto_escolar.MVC.Model.Disciplina;
import br.com.projeto_escolar.MVC.Model.Estudante;
import br.com.projeto_escolar.MVC.Model.ListaEstudantes;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Disciplina matematica = new Disciplina("MAT002", "matematica");
        CadastroDisciplina cadastroDisciplina = new CadastroDisciplina();

        matematica.setCodigo("MAT101");
        matematica.setNomeDisciplina("matematica");
        cadastroDisciplina.adicionarDisciplina(matematica);
        cadastroDisciplina.removerDisciplina("MAT101");
        System.out.println( cadastroDisciplina.verificarDisciplina("MAT101"));
    }
}
