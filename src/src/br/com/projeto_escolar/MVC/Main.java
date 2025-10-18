package br.com.projeto_escolar.MVC;

import br.com.projeto_escolar.MVC.Model.CadastroDisciplina;
import br.com.projeto_escolar.MVC.Model.Disciplina;
import br.com.projeto_escolar.MVC.Model.Estudante;
import br.com.projeto_escolar.MVC.Model.ListaEstudantes;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ListaEstudantes listaEstudantes = new ListaEstudantes();
        Estudante estudante = new Estudante();
        Estudante estudante1 = new Estudante();
        Estudante estudante2 = new Estudante();

        Disciplina matematica = new Disciplina();
        CadastroDisciplina cadastroDisciplina = new CadastroDisciplina();

        matematica.setCodigo("MAT101");
        matematica.setNomeDisciplina("Matemética");

        cadastroDisciplina.adicionarDisciplina(matematica);

        cadastroDisciplina.verificarDisciplina("MAT101");


        estudante.setNome("Matheus");
        estudante.setId(1);

        estudante1.setNome("Gabriel");
        estudante1.setId(2);

        estudante2.setNome("Gael");
        estudante2.setId(3);

        listaEstudantes.adicionarEstudante(estudante);
        listaEstudantes.adicionarEstudante(estudante1);
        listaEstudantes.adicionarEstudante(estudante2);
        List<Estudante> resultados = listaEstudantes.buscarEstudantePorNome("ga");
        listaEstudantes.obterEstudantePorIndice(2);
        listaEstudantes.obterEstudantePorIndice(1);
        System.out.println("============================================");
        listaEstudantes.ordenarEstudantesPorNome();
        System.out.println("===========================================");
        for( Estudante e : resultados){
            System.out.println(e);
        }
        System.out.println("============================================");
        listaEstudantes.removerEstudantePorId(2);
        listaEstudantes.removerEstudantePorId(1);


    }
}
