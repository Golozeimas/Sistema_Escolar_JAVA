package br.com.projeto_escolar.MVC.Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

public class GeradorRelatorio {

    private ListaEstudantes listaEstudantes;
    private CadastroDisciplina cadastroDisciplina;
    private HistoricoNotas historicoNotas;

    public GeradorRelatorio(ListaEstudantes le, CadastroDisciplina cd, HistoricoNotas hn) {
        this.listaEstudantes = le;
        this.cadastroDisciplina = cd;
        this.historicoNotas = hn;
    }

    /*
     * Gera o relatório completo em arquivo output.txt
     * caminhoArquivo caminho onde o arquivo será salvo (ex: "output.txt")
     * true se gerado com sucesso, false caso contrário
     */
    public boolean gerarRelatorio(String caminhoArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {

            // Cabeçalho do relatório
            escreverCabecalho(writer);

            // Seção 1: Lista de Estudantes
            escreverListaEstudantes(writer);

            // Seção 2: Lista de Disciplinas
            escreverListaDisciplinas(writer);

            // Seção 3: Histórico de Notas por Estudante
            escreverHistoricoNotas(writer);

            // Seção 4: Estatísticas Gerais
            escreverEstatisticas(writer);

            // Seção 5: Ranking Top 3
            escreverRanking(writer);

            // Rodapé
            escreverRodape(writer);

            return true;

        } catch (IOException e) {
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private void escreverCabecalho(BufferedWriter writer) throws IOException {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        writer.write("================================================================================\n");
        writer.write("                     SISTEMA DE GERENCIAMENTO ESCOLAR\n");
        writer.write("                          RELATÓRIO ACADÊMICO\n");
        writer.write("================================================================================\n");
        writer.write("Data/Hora: " + agora.format(formatter) + "\n");
        writer.write("================================================================================\n\n");
    }

    private void escreverListaEstudantes(BufferedWriter writer) throws IOException {
        writer.write("╔══════════════════════════════════════════════════════════════════════════════╗\n");
        writer.write("║                           LISTA DE ESTUDANTES                                ║\n");
        writer.write("╚══════════════════════════════════════════════════════════════════════════════╝\n\n");

        List<Estudante> estudantes = listaEstudantes.obterTodos();

        if (estudantes.isEmpty()) {
            writer.write("   Nenhum estudante cadastrado.\n\n");
        } else {
            writer.write(String.format("%-10s | %-50s\n", "ID", "NOME"));
            writer.write("-----------|----------------------------------------------------\n");

            for (Estudante e : estudantes) {
                writer.write(String.format("%-10d | %-50s\n", e.getId(), e.getNome()));
            }

            writer.write("\nTotal de estudantes: " + estudantes.size() + "\n\n");
        }
    }

    private void escreverListaDisciplinas(BufferedWriter writer) throws IOException {
        writer.write("╔══════════════════════════════════════════════════════════════════════════════╗\n");
        writer.write("║                          LISTA DE DISCIPLINAS                                ║\n");
        writer.write("╚══════════════════════════════════════════════════════════════════════════════╝\n\n");

        Set<Disciplina> disciplinas = cadastroDisciplina.obterTodasDisciplinas();

        if (disciplinas.isEmpty()) {
            writer.write("   Nenhuma disciplina cadastrada.\n\n");
        } else {
            writer.write(String.format("%-15s | %-50s\n", "CÓDIGO", "NOME DA DISCIPLINA"));
            writer.write("----------------|----------------------------------------------------\n");

            for (Disciplina d : disciplinas) {
                writer.write(String.format("%-15s | %-50s\n", d.getCodigo(), d.getNomeDisciplina()));
            }

            writer.write("\nTotal de disciplinas: " + disciplinas.size() + "\n\n");
        }
    }

    private void escreverHistoricoNotas(BufferedWriter writer) throws IOException {
        writer.write("╔══════════════════════════════════════════════════════════════════════════════╗\n");
        writer.write("║                    HISTÓRICO DE NOTAS POR ESTUDANTE                          ║\n");
        writer.write("╚══════════════════════════════════════════════════════════════════════════════╝\n\n");

        List<Estudante> estudantes = listaEstudantes.obterTodos();

        if (estudantes.isEmpty()) {
            writer.write("   Nenhum estudante cadastrado.\n\n");
            return;
        }

        for (Estudante estudante : estudantes) {
            writer.write("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
            writer.write(String.format("  ESTUDANTE: %s (ID: %d)\n", estudante.getNome(), estudante.getId()));
            writer.write("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

            List<Matricula> matriculas = historicoNotas.obteMatriculas(estudante.getId());

            if (matriculas.isEmpty()) {
                writer.write("   → Nenhuma matrícula registrada.\n\n");
            } else {
                writer.write(String.format("   %-20s | %-10s\n", "DISCIPLINA", "NOTA"));
                writer.write("   ---------------------|------------\n");

                for (Matricula m : matriculas) {
                    writer.write(String.format("   %-20s | %10.2f\n", m.getCodigoDisciplina(), m.getNota()));
                }

                double media = historicoNotas.mediaDoEstudante(estudante.getId());
                writer.write("   ────────────────────────────────\n");
                writer.write(String.format("   MÉDIA GERAL:           %10.2f\n\n", media));
            }
        }
    }

    private void escreverEstatisticas(BufferedWriter writer) throws IOException {
        writer.write("╔══════════════════════════════════════════════════════════════════════════════╗\n");
        writer.write("║                         ESTATÍSTICAS POR DISCIPLINA                          ║\n");
        writer.write("╚══════════════════════════════════════════════════════════════════════════════╝\n\n");

        Set<Disciplina> disciplinas = cadastroDisciplina.obterTodasDisciplinas();

        if (disciplinas.isEmpty()) {
            writer.write("   Nenhuma disciplina cadastrada.\n\n");
            return;
        }

        writer.write(String.format("%-20s | %-40s | %-10s\n", "CÓDIGO", "DISCIPLINA", "MÉDIA"));
        writer.write("---------------------|------------------------------------------|------------\n");

        for (Disciplina d : disciplinas) {
            double media = historicoNotas.mediaDaDisciplina(d.getCodigo());
            writer.write(String.format("%-20s | %-40s | %10.2f\n",
                    d.getCodigo(),
                    d.getNomeDisciplina(),
                    media));
        }
        writer.write("\n");
    }

    private void escreverRanking(BufferedWriter writer) throws IOException {
        writer.write("╔══════════════════════════════════════════════════════════════════════════════╗\n");
        writer.write("║                        TOP 3 MELHORES ESTUDANTES                             ║\n");
        writer.write("╚══════════════════════════════════════════════════════════════════════════════╝\n\n");

        List<Estudante> top3 = historicoNotas.topNEstudantesPorMedia(3);

        if (top3.isEmpty()) {
            writer.write("   Nenhum estudante com notas registradas.\n\n");
            return;
        }

        writer.write(String.format("%-10s | %-10s | %-40s | %-10s\n", "POSIÇÃO", "ID", "NOME", "MÉDIA"));
        writer.write("-----------|------------|------------------------------------------|------------\n");

        int posicao = 1;
        for (Estudante e : top3) {
            double media = historicoNotas.mediaDoEstudante(e.getId());

            String medalha = "";
            if (posicao == 1) medalha = "🥇";
            else if (posicao == 2) medalha = "🥈";
            else if (posicao == 3) medalha = "🥉";

            writer.write(String.format("%-10s | %-10d | %-40s | %10.2f\n",
                    posicao + "º " + medalha,
                    e.getId(),
                    e.getNome(),
                    media));

            posicao++;
        }
        writer.write("\n");
    }

    private void escreverRodape(BufferedWriter writer) throws IOException {
        writer.write("================================================================================\n");
        writer.write("                         FIM DO RELATÓRIO\n");
        writer.write("           Sistema de Gerenciamento Escolar - Versão 1.0\n");
        writer.write("================================================================================\n");
    }

    /*
     * Gera um relatório resumido (apenas estatísticas principais)
     */
    public boolean gerarRelatorioResumido(String caminhoArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {

            escreverCabecalho(writer);

            writer.write("╔══════════════════════════════════════════════════════════════════════════════╗\n");
            writer.write("║                           RESUMO ESTATÍSTICO                                 ║\n");
            writer.write("╚══════════════════════════════════════════════════════════════════════════════╝\n\n");

            List<Estudante> estudantes = listaEstudantes.obterTodos();
            Set<Disciplina> disciplinas = cadastroDisciplina.obterTodasDisciplinas();

            writer.write("Total de Estudantes Cadastrados: " + estudantes.size() + "\n");
            writer.write("Total de Disciplinas Cadastradas: " + disciplinas.size() + "\n\n");

            escreverRanking(writer);
            escreverRodape(writer);

            return true;

        } catch (IOException e) {
            System.err.println("Erro ao gerar relatório resumido: " + e.getMessage());
            return false;
        }
    }
}
