package br.com.projeto_escolar.Codigo.Classes;

import br.com.projeto_escolar.Codigo.Metodos.ListaEstudantes;

public class Main {
    public static void main(String[] args) {
        //MAIN DO TESTE DE LEITURA DO ARQUIVO CSV

        // 1. Definir o caminho do arquivo CSV
        //    Coloque o caminho do arquivo aqui (veja abaixo como ajustar se estiver na pasta "data")
        String caminhoArq = "data/estudantes.csv"; // <-- AQUI você põe o caminho do CSV

        // 2. Criar a lista de estudantes
        ListaEstudantes lista = new ListaEstudantes();

        // 3. Chamar o método para ler o arquivo e carregar os estudantes
        LeitorDataBase.lerArqEstudante(caminhoArq, lista);

        // 4. Imprimir todos os estudantes
        System.out.println("Lista de Estudantes carregados:");
        for (Estudante e : lista.getEstudantes()) {
            System.out.println(e);
        }
    }
}
