package br.com.projeto_escolar.MVC.Model;

import java.util.Objects;

public class Matricula{
    private String codigoDisciplina;
    private double nota;

    public Matricula(String codigoDisciplina, double nota){
        this.codigoDisciplina = codigoDisciplina;
        this.nota = nota;
    }

    public String getCodigoDisciplina() {
        return codigoDisciplina;
    }

    public void setCodigoDisciplina(String codigoDisciplina) {
        this.codigoDisciplina = codigoDisciplina;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false; // Verifica se os objetos são da mesma classe
        // Mas poderia ser if (!(o instanceof Matricula matricula))
        Matricula that = (Matricula) o; //DownCast
        return Objects.equals(codigoDisciplina, that.codigoDisciplina);
    }

    @Override
    public int hashCode(){
        return Objects.hash(codigoDisciplina);
    }

    @Override
    public String toString() {
        return "Disciplina - " + codigoDisciplina + " Nota - " + nota ;
    }
}
