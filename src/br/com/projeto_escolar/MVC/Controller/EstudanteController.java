package br.com.projeto_escolar.MVC.Controller;

import br.com.projeto_escolar.MVC.Model.Estudante;
import br.com.projeto_escolar.MVC.Model.ListaEstudantes;

import java.util.List;
import java.util.Optional;

public class EstudanteController {
    private final ListaEstudantes listaEstudantes;

    public EstudanteController(ListaEstudantes listaEstudantes) {
        this.listaEstudantes = listaEstudantes;
    }

    // Retorna todos os estudantes (na ordem atual de cadastro)
    public List<Estudante> obterTodos() {
        return listaEstudantes.obterTodos(); // tentativa; se sua ListaEstudantes usa outro nome, ajuste aqui
    }

    // Adiciona um estudante (retorna o objeto criado se quiser usar)
    public void adicionarEstudante(int id, String nome) {
        Estudante e = new Estudante(id, nome);
        listaEstudantes.adicionarEstudante(e);
    }

    // Remove por id
    public boolean removerEstudantePorId(int id) {
        listaEstudantes.removerEstudantePorId(id);
        // se a ListaEstudantes não retorna boolean, assumimos sucesso silencioso
        // Caso queira feedback real, modifique ListaEstudantes para retornar boolean
        return true;
    }

    // Buscar por substring no nome (case-insensitive)
    public List<br.com.projeto_escolar.MVC.Model.Estudante> buscarPorNome(String substring) {
        return listaEstudantes.buscarEstudantePorNome(substring);
    }

    // Ordenar estudantes por nome
    public void ordenarPorNome() {
        listaEstudantes.ordenarEstudantesPorNome();
    }


    public Optional<Estudante> obterPorIndice(int indice) {
        return listaEstudantes.obterEstudantePorIndice(indice);
    }

}
