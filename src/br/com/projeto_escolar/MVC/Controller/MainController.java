package br.com.projeto_escolar.MVC.Controller;

import br.com.projeto_escolar.MVC.Model.*;
import java.io.File;

public class MainController {
    private ListaEstudantes listaEstudantes;
    private CadastroDisciplina cadastroDisciplina;
    private HistoricoNotas historicoNotas;
    private GeradorRelatorio geradorRelatorio;

    private EstudanteController estudanteController;
    private DisciplinaController disciplinaController;
    private MatriculaController matriculaController;

    public MainController() {
        // Inicializa os Models
        listaEstudantes = new ListaEstudantes();
        cadastroDisciplina = new CadastroDisciplina();
        historicoNotas = new HistoricoNotas(listaEstudantes);

        // Carrega dados dos arquivos CSV
        LeitorDataBase.lerArqEstudante("./data/estudantes.csv", listaEstudantes);
        LeitorDataBase.lerArqDisciplina("./data/disciplinas.csv", cadastroDisciplina);
        LeitorDataBase.lerArqMatricula("./data/matriculas.csv", historicoNotas);

        // Inicializa os Controllers
        estudanteController = new EstudanteController(listaEstudantes);
        disciplinaController = new DisciplinaController(cadastroDisciplina);
        matriculaController = new MatriculaController(historicoNotas, listaEstudantes, cadastroDisciplina);

        // Inicializa o Gerador de Relatório
        geradorRelatorio = new GeradorRelatorio(listaEstudantes, cadastroDisciplina, historicoNotas);
    }

    public EstudanteController getEstudanteController() {
        return estudanteController;
    }

    public DisciplinaController getDisciplinaController() {
        return disciplinaController;
    }

    public MatriculaController getMatriculaController() {
        return matriculaController;
    }

    /*
     * Gera relatório na RAIZ do projeto (output.txt)
     * VERSÃO SIMPLIFICADA - SEM PASTA
     */
    public boolean gerarRelatorioCompleto() {
        try {
            System.out.println("═══════════════════════════════════════");
            System.out.println("  GERANDO RELATÓRIO COMPLETO");
            System.out.println("═══════════════════════════════════════");

            boolean resultado = geradorRelatorio.gerarRelatorio("output.txt");

            if (resultado) {
                File arquivo = new File("output.txt");
                System.out.println(" SUCESSO!");
                System.out.println(" Arquivo: " + arquivo.getName());
                System.out.println(" Local: " + arquivo.getAbsolutePath());
                System.out.println("═══════════════════════════════════════");
            } else {
                System.out.println(" FALHOU!");
                System.out.println("═══════════════════════════════════════");
            }

            return resultado;

        } catch (Exception e) {
            System.err.println("ERRO CRÍTICO:");
            e.printStackTrace();
            return false;
        }
    }

    /*
     * Gera relatório resumido na RAIZ (output_resumido.txt)
     */
    public boolean gerarRelatorioResumido() {
        try {
            System.out.println("═══════════════════════════════════════");
            System.out.println("   GERANDO RELATÓRIO RESUMIDO");
            System.out.println("═══════════════════════════════════════");

            boolean resultado = geradorRelatorio.gerarRelatorioResumido("output_resumido.txt");

            if (resultado) {
                File arquivo = new File("output_resumido.txt");
                System.out.println("SUCESSO!");
                System.out.println("Arquivo: " + arquivo.getName());
                System.out.println("Local: " + arquivo.getAbsolutePath());
                System.out.println("═══════════════════════════════════════");
            }

            return resultado;

        } catch (Exception e) {
            System.err.println("ERRO:");
            e.printStackTrace();
            return false;
        }
    }

    /*
     * Gera relatório em caminho customizado
     */
    public boolean gerarRelatorioPersonalizado(String caminho) {
        try {
            System.out.println("═══════════════════════════════════════");
            System.out.println("GERANDO RELATÓRIO PERSONALIZADO");
            System.out.println("Caminho: " + caminho);
            System.out.println("═══════════════════════════════════════");

            // Cria diretórios se necessário
            File arquivo = new File(caminho);
            File diretorio = arquivo.getParentFile();

            if (diretorio != null && !diretorio.exists()) {
                System.out.println("Criando pasta: " + diretorio.getName());
                diretorio.mkdirs();
            }

            boolean resultado = geradorRelatorio.gerarRelatorio(caminho);

            if (resultado) {
                System.out.println("SUCESSO!");
                System.out.println("Local: " + arquivo.getAbsolutePath());
                System.out.println("═══════════════════════════════════════");
            }

            return resultado;

        } catch (Exception e) {
            System.err.println("ERRO:");
            e.printStackTrace();
            return false;
        }
    }
}