package br.com.projeto_escolar.Codigo.Métodos;

import br.com.projeto_escolar.Codigo.Classes.Estudante;

import java.util.ArrayList;


public class ListaEstudantes {
    ArrayList<Estudante> listaEstudante = new ArrayList<>();

    public void adicionarEstudante(Estudante e) {
        listaEstudante.add(e);
    }

    // rever como estruturar essa lógica
    public void removerEstudantePorId(int indice) {
        for (Estudante estudantes : listaEstudante) {
            if (estudantes.getId() == indice) {
                System.out.println("estudante removido com sucesso!");
                listaEstudante.remove(indice);
                break;
            }
        }
    }

    public void obterEstudantePorIndice(int indice) {
        // uso do for-each para trabalhamos com a classe
        for (Estudante estudante : listaEstudante) {
            if (estudante.getId() == indice) {
                System.out.println("Nome do estudante: " + estudante.getNome());
                System.out.println("Id do estudante: " + estudante.getId());
            }
        }
    }

    public void ordenarEstudantesPorNome(){

    }
}
