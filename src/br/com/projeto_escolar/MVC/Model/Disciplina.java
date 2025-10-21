package br.com.projeto_escolar.MVC.Model;

import java.util.Objects;

public class Disciplina {
    private String codigo;
    private String nomeDisciplina;

    public Disciplina(String codigo, String nomeDisciplina){
        this.codigo = codigo;
        this.nomeDisciplina = nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina){
        this.nomeDisciplina = nomeDisciplina;
    }

    public String getNomeDisciplina(){
        return nomeDisciplina;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    //Parte do hash para funcionar a função de adicionar na lista sem precisar instanciar cada objeto
    // Serve para prevenir que duas materias iguais do csv sejam adicionadas no set
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disciplina that = (Disciplina) o; //Cast perigoso, VERIFICAR VIABILIDADE
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode(){
        return Objects.hash(codigo);
    }

    @Override
    public String toString(){
        return "Código: " + codigo + " Materia: " + nomeDisciplina; 
    }
}
