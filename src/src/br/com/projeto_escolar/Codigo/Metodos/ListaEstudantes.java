package br.com.projeto_escolar.Codigo.Metodos;

import br.com.projeto_escolar.Codigo.Classes.Estudante;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class ListaEstudantes {

    List<Estudante> listaEstudante = new ArrayList<>();

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
            if (listaEstudante.isEmpty()){
                System.out.println("lista de estudante está vazia");
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
            if (listaEstudante.isEmpty()){
                System.out.println("lista de estudantes está vazia");
            }
        }
    }

    /* uso de "sort" com o a classe Comparator e o metodo comparing, passando a classe e a variavel
    *  que vai ser ordenada*/
    public void ordenarEstudantesPorNome(){
        listaEstudante.sort(Comparator.comparing(Estudante::getNome));
        for (Estudante e : listaEstudante){
            System.out.println(e.getNome());
        }
    }


    // Camada(metodos) de testes:
    public List<Estudante> getEstudantes() {
        return listaEstudante;
    }
    
}
