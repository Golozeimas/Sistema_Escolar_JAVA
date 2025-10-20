package br.com.projeto_escolar.MVC.Model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CadastroDisciplina {
    private Set<Disciplina> disciplinas = new HashSet<>();

    /*
     * @return false se não for adicionado
     * @return true se for adicionado com sucesso(caso nao houver duplicadas)
     */
    public boolean adicionarDisciplina(Disciplina d){
        boolean adicionado = disciplinas.add(d);
        return adicionado;
    }

    public boolean verificarDisciplina(String codigo){
        for (Disciplina d : disciplinas){
            if (d.getCodigo().equals(codigo)){
             return true;
            }
        }
        return false;
    }

    public void removerDisciplina(String codigo){
        Iterator<Disciplina> disciplinaIterator = disciplinas.iterator();
        while (disciplinaIterator.hasNext()){
            Disciplina disciplina = disciplinaIterator.next();
            if (disciplina.getCodigo().equals(codigo)){
                disciplinaIterator.remove();
                System.out.println(disciplina.getNomeDisciplina());
                System.out.println(disciplina.getCodigo());
                System.out.println("removendo...");
            }
        }
    }

    //Esse metodo retorna uma copia para evitar alterar os dados principais
    public Set<Disciplina> obterTodasDisciplinas(){
        return new HashSet<>(disciplinas);
    }

}
