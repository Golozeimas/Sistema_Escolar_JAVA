package br.com.projeto_escolar.MVC.Controller;

import br.com.projeto_escolar.MVC.Model.CadastroDisciplina;
import br.com.projeto_escolar.MVC.Model.Disciplina;


import java.util.Optional;
import java.util.Set;

public class DisciplinaController {
    private final CadastroDisciplina cadastro;

    public DisciplinaController(CadastroDisciplina cadastro) {
        this.cadastro = cadastro;
    }

    public void adicionarDisciplina(String codigo, String nome) {
        Disciplina d = new Disciplina(codigo, nome);
        cadastro.adicionarDisciplina(d);
    }

    public boolean removerDisciplina(String codigo) {
        cadastro.removerDisciplina(codigo);
        return true; // ajuste conforme retorno do seu model se necessário
    }

    public boolean verificarDisciplina(String codigo) {
        return cadastro.verificarDisciplina(codigo);
    }

    public Set<Disciplina> obterTodas() {
        return cadastro.obterTodasDisciplinas(); // assume que retorna List ou Collection; ajuste se for Set
    }

    // Caso precise obter por código (se existir no Model)
    public Optional<Boolean> obterPorCodigo(String codigo) {
        try {
            boolean d = cadastro.verificarDisciplina(codigo);
            return Optional.ofNullable(d);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
