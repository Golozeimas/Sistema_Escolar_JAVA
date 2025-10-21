package br.com.projeto_escolar.MVC.Model;


import java.util.*;


public class ListaEstudantes {

    private List<Estudante> listaEstudante = new ArrayList<>();

    public void adicionarEstudante(Estudante e) {
        listaEstudante.add(e);
    }

    /* rever como estruturar essa lógica, aparentemente esse é o jeito de remover com segurança
     e percorrer em coleções*/
    public void removerEstudantePorId(int indice) {
       Iterator<Estudante> iterator = listaEstudante.iterator();
       while(iterator.hasNext()) {
           Estudante estudante = iterator.next();
           if (estudante.getId() == indice) {
               iterator.remove();
               System.out.println("id do estudante removido: " + estudante.getId());
               System.out.println("nome do esudante removido: " + estudante.getNome());
           }
       }
    }

    public void obterEstudantePorIndice(int indice) {
        // uso do for-each para trabalhamos com a classe
        for (Estudante estudante : listaEstudante) {
            if (estudante.getId() == indice){
                System.out.println("Nome do estudante: " + estudante.getNome());
                System.out.println("Id do estudante: " + estudante.getId());
                break;
            }
            if (listaEstudante.isEmpty()){
                System.out.println("lista de estudantes está vazia");
            }
        }
    }

    public List<Estudante> buscarEstudantePorNome (String subString){
        List<Estudante> encontrados = new ArrayList<>(); //  cria uma lista para colocar os encotrados

        if (subString == null){
            return encontrados;
        }

        String termoDeBusca = subString.toLowerCase(); // transforma em minusculas

        for(Estudante e : listaEstudante){
            if (e.getNome().toLowerCase().contains(termoDeBusca)){
                encontrados.add(e);
            }
        }
        return encontrados;
    }

    /* uso de "sort" com o a classe Comparator e o metodo comparing, passando a classe e a variavel
    *  que vai ser ordenada*/
    public void ordenarEstudantesPorNome(){
        listaEstudante.sort(Comparator.comparing(Estudante::getNome));
        for (Estudante e : listaEstudante){
            System.out.println(e.getNome());
        }
    }


    // ===== PARA TESTES ========
    // APAGAR DEPOIS

    public List<Estudante> obterTodos() {
      return new ArrayList<>(listaEstudante);
    }
}
