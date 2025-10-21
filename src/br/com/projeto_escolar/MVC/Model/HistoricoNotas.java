package br.com.projeto_escolar.MVC.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;
import java.util.Iterator;

public class HistoricoNotas {
    //Dict com chave:int do IdEstudante e o valor: as disciplinas que ele está matriculado
    private Map<Integer, List<Matricula>> mapaMatriculaEstudante = new HashMap<>();
    private ListaEstudantes listaNEstudantes;

    public HistoricoNotas(ListaEstudantes le){
        this.listaNEstudantes = le;
    }

    public void adicionarMatricula(int idEstudante, String codigoDaDisciplina, double nota){
        Matricula matricula = new Matricula(codigoDaDisciplina, nota);
        
        List<Matricula> listaMatriculas = mapaMatriculaEstudante.get(idEstudante);
    //As funções getOfDefault e computeIfAbsent podem reduzir esse trecho de código******
        if (listaMatriculas == null) { // Se o estudante não tem matricula no map ele adiciona na lista
            listaMatriculas = new ArrayList<>();
            mapaMatriculaEstudante.put(idEstudante, listaMatriculas);
        }
        //Adiciona a matricula na lista das matriculas do estudante
        listaMatriculas.add(matricula);
    }

    public List<Matricula> obteMatriculas(int idEstudante){
        List<Matricula> listaDeMatriculas = mapaMatriculaEstudante.get(idEstudante);
        
        if(listaDeMatriculas == null){
            return new ArrayList<>(); // Para retornar uma lista vazia
        }

        return listaDeMatriculas;
        //return mapaMatriculaEstudante.getOfDefault(idEstudante, new ArrayList<>());
    }

    public Optional<Double> obterNota(int idEstudante, String codigoDisciplina){ // Não entendi como isso funciona
        List<Matricula> listaDeMatriculas = mapaMatriculaEstudante.get(idEstudante);

        if (listaDeMatriculas == null) {
            System.out.println("Estudante não está cadastrado");
            return Optional.empty();
        }

        //Procura a nota na lista
        for (Matricula m : listaDeMatriculas) {
            if (codigoDisciplina.equalsIgnoreCase(m.getCodigoDisciplina())) {
                return Optional.of(m.getNota());
            }
        }

        System.out.println("Estudante não possui a diciplina cadastrada");
        return Optional.empty();
    }

    public void removerMatricula(int idEstudante, String cdgDisciplina){
        List<Matricula> listaDeMatriculas = mapaMatriculaEstudante.get(idEstudante);

        if (listaDeMatriculas == null) {
            System.out.println("Id Estudante não cadastrado");
            return;
        }

        Iterator<Matricula> iterator = listaDeMatriculas.iterator();
        while(iterator.hasNext()) {
           Matricula m = iterator.next();
           if (m.getCodigoDisciplina().equalsIgnoreCase(cdgDisciplina) ) {
               iterator.remove();
               System.out.println("Codigo da disciplina removida: " + m.getCodigoDisciplina());
               return;
           }

        System.out.println("Matricula não encontrada");
       }
    }

    public double mediaDoEstudante(int idEstudante) {
        List<Matricula> matriculas = mapaMatriculaEstudante.getOrDefault(idEstudante, new ArrayList<>());
        
        if (matriculas.isEmpty()) {
            return 0.0;
        }
        
        double soma = 0;
        for (Matricula m : matriculas) {
            soma += m.getNota();
        }
        
        // retorna média
        return soma / matriculas.size();
    }

    public double mediaDaDisciplina(String codigoDisciplina) {
        double somaNotas = 0;
        int quantidadeMatriculas = 0;
        
        // Percorrer apenas os valores (listas) do Map
        for (List<Matricula> matriculas : mapaMatriculaEstudante.values()) {
            for (Matricula m : matriculas) {
                if (m.getCodigoDisciplina().equals(codigoDisciplina)) {
                    somaNotas += m.getNota();
                    quantidadeMatriculas++;
                }
            }
        }
        
        return (quantidadeMatriculas == 0) ? 0.0 : somaNotas / quantidadeMatriculas;
    }

    public List<Estudante> topNEstudantesPorMedia(int N) {
    // Map: idEstudante -> média
    Map<Integer, Double> mediasPorEstudante = new HashMap<>();
    
    // Calcular média de cada estudante
    for (Integer idEstudante : mapaMatriculaEstudante.keySet()) {
        double media = mediaDoEstudante(idEstudante); // Usa o método que você já fez!
        mediasPorEstudante.put(idEstudante, media);
    }
    List<Map.Entry<Integer, Double>> listaOrdenada = new ArrayList<>(mediasPorEstudante.entrySet());
    listaOrdenada.sort(Map.Entry.<Integer, Double>comparingByValue().reversed());

    List<Estudante> topEstudantes = new ArrayList<>();

    for (int i = 0; i < Math.min(N, listaOrdenada.size()); i++) {
        Integer idEstudante = listaOrdenada.get(i).getKey();
        
        // Buscar objeto Estudante na lista
        listaNEstudantes.obterEstudantePorIndice(idEstudante).ifPresent(topEstudantes::add);
    }

    return topEstudantes;

    }
}
