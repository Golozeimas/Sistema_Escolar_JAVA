package br.com.projeto_escolar.MVC.Model;

public class Disciplina {
    private String codigo;
    private String nomeDisciplina;

    public void setNomeDisciplina(String nomedisciplina){
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
}
