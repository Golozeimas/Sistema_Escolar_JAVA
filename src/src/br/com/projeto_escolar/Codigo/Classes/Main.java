package br.com.projeto_escolar.Codigo.Classes;

import br.com.projeto_escolar.Codigo.Métodos.ListaEstudantes;

public class Main {
    public static void main(String[] args) {
    Estudante estudante = new Estudante(1, "Ana");
    Estudante estudante2 = new Estudante(2,"Beatriz");
    Estudante estudante3 = new Estudante(3,"Matheus");

    ListaEstudantes listaEstudantes = new ListaEstudantes();

    listaEstudantes.adicionarEstudante(estudante);

    listaEstudantes.adicionarEstudante(estudante2);

    listaEstudantes.adicionarEstudante(estudante3);

    listaEstudantes.obterEstudantePorIndice(1);

    }
}
