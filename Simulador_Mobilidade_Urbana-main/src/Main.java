import cidade.*;
import estruturas.*;
import heuristica.*;
import json.*;
import trafego.*;

public class Main {

    // Simulador global acessível em toda aplicação
    public static Simulador simuladorGlobal;

    // Inicializa o simulador, carregando dados e configurando parâmetros
    public static void inicializarSimulador(String[] args) {
        try {
            int duracaoSimulacao = 20; // duração padrão

            // Tenta ler duração da simulação a partir do argumento, se fornecido
            if (args.length > 0) {
                try {
                    duracaoSimulacao = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    System.out.println("Argumento inválido para duração. Usando valor padrão: " + duracaoSimulacao);
                }
            }

            System.out.println("Carregando grafo do arquivo JSON...");
            Grafo grafo = LeitorOSMJson.carregar("json/Morada_do_Sol.json");
            System.out.printf("Número de vértices carregados: %d%n", grafo.vertices.tamanho());

            Lista<Intersecao> intersecoes = grafo.converterParaIntersecoes();
            HeuristicaControle heuristica = new HeuristicaCicloFixo(5, 2, 5);

            simuladorGlobal = new Simulador(intersecoes, heuristica, duracaoSimulacao, grafo);

            // Configurações do gerador de veículos
            simuladorGlobal.getGeradorVeiculos().setMaxVeiculosParaCriar(7);  // máximo de x veículos
            simuladorGlobal.getGeradorVeiculos().setPassosParaGerarVeiculo(1); // gera x veículo por passo
            simuladorGlobal.getGeradorVeiculos().setLimiteMaximoVerticesCaminho(204); // limite de x vértices

            System.out.println("\nConfigurando simulador...");
            System.out.printf("- Duração: %d unidades de tempo%n", duracaoSimulacao);
            System.out.printf("- Máximo de veículos: %d%n", simuladorGlobal.getGeradorVeiculos().getMaxVeiculosParaCriar());
            System.out.println("- Heurística: Ciclo Fixo");

        } catch (Exception e) {
            System.err.println("Erro ao carregar ou executar a simulação:");
            e.printStackTrace();
        }
    }

    // Ponto de entrada principal do programa
    public static void main(String[] args) {
        inicializarSimulador(args);

        if (simuladorGlobal != null) {
            System.out.println("\nIniciando simulação...\n");

            // Executa a simulação passo a passo, imprimindo o estado a cada passo
            for (int tempoAtual = 0; tempoAtual < simuladorGlobal.getDuracaoSimulacao(); tempoAtual++) {
                System.out.printf("== Passo %d ==%n", tempoAtual);
                simuladorGlobal.executarPasso(tempoAtual);
                System.out.println(); // espaço para melhor leitura
            }

            // Exibe estatísticas finais da simulação
            System.out.println("Simulação finalizada.");
            System.out.printf("Total de veículos gerados: %d%n", simuladorGlobal.getGeradorVeiculos().getTotalVeiculosCriados());
            System.out.printf("Veículos que completaram o trajeto: %d%n", simuladorGlobal.getColetor().getVeiculosFinalizados());
            System.out.printf("Tempo médio de viagem: %.1f unidades%n", simuladorGlobal.getColetor().getMediaTempoViagem());
            System.out.printf("Tempo médio de espera: %.1f unidades%n", simuladorGlobal.getColetor().getMediaConsumoEnergetico());
            
            // Calcular índice de congestionamento simples (veículos não finalizados / total)
            int totalVeiculos = simuladorGlobal.getGeradorVeiculos().getTotalVeiculosCriados();
            int finalizados = simuladorGlobal.getColetor().getVeiculosFinalizados();
            double indiceCongestionamento = totalVeiculos > 0 ? (double)(totalVeiculos - finalizados) / totalVeiculos : 0.0;
            System.out.printf("Índice de congestionamento: %.2f%n", indiceCongestionamento);
        } else {
            System.err.println("Simulador não inicializado. Abortando execução.");
        }
    }
}
