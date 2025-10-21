package br.com.projeto_escolar.MVC.Model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import br.com.projeto_escolar.MVC.Model.Estudante;
import br.com.projeto_escolar.MVC.Model.*;

public class LeitorDataBase {
    
    // Recebe o caminho do arquivo e o objeto onde deve adicionar
    public static void lerArqEstudante(String caminhoArq, ListaEstudantes le){
        //OBS: O buffereader carrega todo o arquivo na memória, é útil somente para arqs pequenos
        //Caso precise escalar o sistema para milhares de alunos
        try(BufferedReader br = new BufferedReader(new FileReader(caminhoArq))) {
            br.readLine(); // Lê a primeira linha e não faz nada com ela

            //Logica para ler todo o arquivo
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim(); // Remove qualquer espaço que houver
                if (linha.isEmpty()) continue;// Pula qualquer linha vazia

                //Separa por campos: id,nome
                String[] campos = linha.split(",", -1); //-1 serve para não ignorar campos vazios
                //if (campos.length < 1) continue; TESTAR SE VAI SER UTIL

                int id = Integer.parseInt(campos[0].trim());
                String nome = campos[1].trim();

                le.adicionarEstudante(new Estudante(id,nome));

            }
        } catch (Exception er) {
            // TODO: handle exception
            System.out.println("Erro ao ler arquivo estudante.csv, tente colocar no diretório ./data");
        }
    }

    public static void lerArqDisciplina(String caminhoArq, Disciplina d){
        //OBS: O buffereader carrega todo o arquivo na memória, é útil somente para arqs pequenos
        //Caso precise escalar o sistema para milhares de alunos
        try(BufferedReader br = new BufferedReader(new FileReader(caminhoArq))) {
            br.readLine(); // Lê a primeira linha e não faz nada com ela

            //Logica para ler todo o arquivo
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim(); // Remove qualquer espaço que houver
                if (linha.isEmpty()) continue;
            }
        } catch (Exception er) {
            // TODO: handle exception
             System.out.println("Erro ao ler arquivo disciplinas.csv, tente colocar no diretório ./data");
        }
    }

    public static void lerArqMatricula(String caminhoArq, HistoricoNotas hn){
        //OBS: O buffereader carrega todo o arquivo na memória, é útil somente para arqs pequenos
        //Caso precise escalar o sistema para milhares de alunos
        try(BufferedReader br = new BufferedReader(new FileReader(caminhoArq))) {
            br.readLine(); // Lê a primeira linha e não faz nada com ela
            //Logica para ler todo o arquivo
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim(); // Remove qualquer espaço que houver
                if (linha.isEmpty()) continue;// Pula qualquer linha vazia

                //Separa por campos: codigo,nome
                String[] campos = linha.split(",", -1); //-1 serve para não ignorar campos vazios
                //if (campos.length < 1) continue; TESTAR SE VAI SER UTIL

                int idEstudante = Integer.parseInt(campos[0].trim());
                String codigoDaDisciplina = campos[1].trim();
                Double nota = Double.parseDouble(campos[2].trim());

                hn.adicionarMatricula(idEstudante, codigoDaDisciplina, nota);
            }
        } catch (Exception er) {
            // TODO: handle exception
             System.out.println("Erro ao ler arquivo matricula.csv, tente colocar no diretório ./data");
        }
    }
}
