package br.com.projeto_escolar.MVC.Model;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// usa apenas FileWriter e PrintWriter
public class GeradorRelatorio {

    private ListaEstudantes listaEstudantes;
    private CadastroDisciplina cadastroDisciplina;
    private HistoricoNotas historicoNotas;

    public GeradorRelatorio(ListaEstudantes le, CadastroDisciplina cd, HistoricoNotas hn) {
        this.listaEstudantes = le;
        this.cadastroDisciplina = cd;
        this.historicoNotas = hn;
    }

    /**
     * Gera o relatório completo
     */
    public boolean gerarRelatorio(String caminhoArquivo) {
        System.out.println("► Iniciando geração do relatório: " + caminhoArquivo);

        PrintWriter writer = null;
        try {
            // Abre o arquivo para escrita
            writer = new PrintWriter(new FileWriter(caminhoArquivo));

            // Escreve o relatório
            escreverCabecalho(writer);
            escreverEstudantes(writer);
            escreverDisciplinas(writer);
            escreverHistorico(writer);
            escreverEstatisticas(writer);
            escreverRanking(writer);
            escreverRodape(writer);

            System.out.println("✓ Relatório gerado com sucesso!");
            return true;

        } catch (Exception e) {
            System.err.println("✗ ERRO ao gerar relatório:");
            System.err.println("  Mensagem: " + e.getMessage());
            System.err.println("  Classe: " + e.getClass().getName());
            e.printStackTrace();
            return false;

        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    private void escreverCabecalho(PrintWriter w) {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        w.println("================================================================================");
        w.println("                     SISTEMA DE GERENCIAMENTO ESCOLAR");
        w.println("                          RELATÓRIO ACADÊMICO");
        w.println("================================================================================");
        w.println("Data/Hora: " + agora.format(formatter));
        w.println("================================================================================");
        w.println();
    }

    private void escreverEstudantes(PrintWriter w) {
        w.println("┌──────────────────────────────────────────────────────────────────────────────┐");
        w.println("│                           LISTA DE ESTUDANTES                                │");
        w.println("└──────────────────────────────────────────────────────────────────────────────┘");
        w.println();

        var estudantes = listaEstudantes.obterTodos();

        if (estudantes.isEmpty()) {
            w.println("   Nenhum estudante cadastrado.");
        } else {
            w.println(String.format("%-10s | %-50s", "ID", "NOME"));
            w.println("-----------|----------------------------------------------------");

            for (var e : estudantes) {
                w.println(String.format("%-10d | %-50s", e.getId(), e.getNome()));
            }

            w.println();
            w.println("Total de estudantes: " + estudantes.size());
        }
        w.println();
    }

    private void escreverDisciplinas(PrintWriter w) {
        w.println("┌──────────────────────────────────────────────────────────────────────────────┐");
        w.println("│                          LISTA DE DISCIPLINAS                                │");
        w.println("└──────────────────────────────────────────────────────────────────────────────┘");
        w.println();

        var disciplinas = cadastroDisciplina.obterTodasDisciplinas();

        if (disciplinas.isEmpty()) {
            w.println("   Nenhuma disciplina cadastrada.");
        } else {
            w.println(String.format("%-15s | %-50s", "CÓDIGO", "NOME DA DISCIPLINA"));
            w.println("----------------|----------------------------------------------------");

            for (var d : disciplinas) {
                w.println(String.format("%-15s | %-50s", d.getCodigo(), d.getNomeDisciplina()));
            }

            w.println();
            w.println("Total de disciplinas: " + disciplinas.size());
        }
        w.println();
    }

    private void escreverHistorico(PrintWriter w) {
        w.println("┌──────────────────────────────────────────────────────────────────────────────┐");
        w.println("│                    HISTÓRICO DE NOTAS POR ESTUDANTE                          │");
        w.println("└──────────────────────────────────────────────────────────────────────────────┘");
        w.println();

        var estudantes = listaEstudantes.obterTodos();

        if (estudantes.isEmpty()) {
            w.println("   Nenhum estudante cadastrado.");
            w.println();
            return;
        }

        for (var estudante : estudantes) {
            w.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            w.println(String.format("  ESTUDANTE: %s (ID: %d)", estudante.getNome(), estudante.getId()));
            w.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            var matriculas = historicoNotas.obteMatriculas(estudante.getId());

            if (matriculas.isEmpty()) {
                w.println("   → Nenhuma matrícula registrada.");
            } else {
                w.println(String.format("   %-20s | %-10s", "DISCIPLINA", "NOTA"));
                w.println("   ---------------------|------------");

                for (var m : matriculas) {
                    w.println(String.format("   %-20s | %10.2f", m.getCodigoDisciplina(), m.getNota()));
                }

                double media = historicoNotas.mediaDoEstudante(estudante.getId());
                w.println("   ────────────────────────────────");
                w.println(String.format("   MÉDIA GERAL:           %10.2f", media));
            }
            w.println();
        }
    }

    private void escreverEstatisticas(PrintWriter w) {
        w.println("┌──────────────────────────────────────────────────────────────────────────────┐");
        w.println("│                         ESTATÍSTICAS POR DISCIPLINA                          │");
        w.println("└──────────────────────────────────────────────────────────────────────────────┘");
        w.println();

        var disciplinas = cadastroDisciplina.obterTodasDisciplinas();

        if (disciplinas.isEmpty()) {
            w.println("   Nenhuma disciplina cadastrada.");
            w.println();
            return;
        }

        w.println(String.format("%-20s | %-40s | %-10s", "CÓDIGO", "DISCIPLINA", "MÉDIA"));
        w.println("---------------------|------------------------------------------|------------");

        for (var d : disciplinas) {
            double media = historicoNotas.mediaDaDisciplina(d.getCodigo());
            w.println(String.format("%-20s | %-40s | %10.2f",
                    d.getCodigo(),
                    d.getNomeDisciplina(),
                    media));
        }
        w.println();
    }

    private void escreverRanking(PrintWriter w) {
        w.println("┌──────────────────────────────────────────────────────────────────────────────┐");
        w.println("│                        TOP 3 MELHORES ESTUDANTES                             │");
        w.println("└──────────────────────────────────────────────────────────────────────────────┘");
        w.println();

        var top3 = historicoNotas.topNEstudantesPorMedia(3);

        if (top3.isEmpty()) {
            w.println("   Nenhum estudante com notas registradas.");
            w.println();
            return;
        }

        w.println(String.format("%-10s | %-10s | %-40s | %-10s", "POSIÇÃO", "ID", "NOME", "MÉDIA"));
        w.println("-----------|------------|------------------------------------------|------------");

        int posicao = 1;
        for (var e : top3) {
            double media = historicoNotas.mediaDoEstudante(e.getId());

            String medalha = "";
            if (posicao == 1) medalha = "🥇";
            else if (posicao == 2) medalha = "🥈";
            else if (posicao == 3) medalha = "🥉";

            w.println(String.format("%-10s | %-10d | %-40s | %10.2f",
                    posicao + "º " + medalha,
                    e.getId(),
                    e.getNome(),
                    media));

            posicao++;
        }
        w.println();
    }

    private void escreverRodape(PrintWriter w) {
        w.println("================================================================================");
        w.println("                         FIM DO RELATÓRIO");
        w.println("           Sistema de Gerenciamento Escolar - Versão 1.0");
        w.println("================================================================================");
    }

    /**
     * Gera um relatório resumido (apenas estatísticas principais)
     */
    public boolean gerarRelatorioResumido(String caminhoArquivo) {
        System.out.println("► Iniciando geração do relatório resumido: " + caminhoArquivo);

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(caminhoArquivo));

            escreverCabecalho(writer);

            writer.println("┌──────────────────────────────────────────────────────────────────────────────┐");
            writer.println("│                           RESUMO ESTATÍSTICO                                 │");
            writer.println("└──────────────────────────────────────────────────────────────────────────────┘");
            writer.println();

            var estudantes = listaEstudantes.obterTodos();
            var disciplinas = cadastroDisciplina.obterTodasDisciplinas();

            writer.println("Total de Estudantes Cadastrados: " + estudantes.size());
            writer.println("Total de Disciplinas Cadastradas: " + disciplinas.size());
            writer.println();

            escreverRanking(writer);
            escreverRodape(writer);

            System.out.println("✓ Relatório resumido gerado com sucesso!");
            return true;

        } catch (Exception e) {
            System.err.println("✗ ERRO ao gerar relatório resumido:");
            e.printStackTrace();
            return false;

        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}