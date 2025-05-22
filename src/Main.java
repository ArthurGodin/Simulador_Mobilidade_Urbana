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

            System.out.println("Criando lista de interseções e vinculando vértices...");
            Lista<Intersecao> intersecoes = grafo.converterParaIntersecoes();
            System.out.printf("Total de interseções criadas: %d%n", intersecoes.tamanho());

            // Exibe algumas interseções para conferência
            int maxPrint = Math.min(5, intersecoes.tamanho());
            for (int i = 0; i < maxPrint; i++) {
                Intersecao inter = intersecoes.obter(i);
                System.out.printf("Interseção #%d: %s - Vértice: %s%n", i, inter, inter.getVertice());
            }

            System.out.println("Configurando heurística do semáforo (ciclo fixo)...");
            HeuristicaControle heuristica = new HeuristicaCicloFixo(5, 2, 5);

            System.out.printf("Criando simulador com duração de %d unidades de tempo.%n", duracaoSimulacao);
            simuladorGlobal = new Simulador(intersecoes, heuristica, duracaoSimulacao, grafo);

            // Configurações do gerador de veículos
            simuladorGlobal.getGeradorVeiculos().setMaxVeiculosParaCriar(7);  // máximo de x veículos
            simuladorGlobal.getGeradorVeiculos().setPassosParaGerarVeiculo(1); // gera x veículo por passo
            simuladorGlobal.getGeradorVeiculos().setLimiteMaximoVerticesCaminho(204); // limite de x vértices

            // Exibe configurações do gerador para acompanhamento
            System.out.println("\nConfigurações do Gerador de Veículos:");
            System.out.println("Max veículos para criar: " + simuladorGlobal.getGeradorVeiculos().getMaxVeiculosParaCriar());
            System.out.println("Passos para gerar veículo: " + simuladorGlobal.getGeradorVeiculos().getPassosParaGerarVeiculo());
            System.out.println("Limite máximo de vértices no caminho: " + simuladorGlobal.getGeradorVeiculos().getLimiteMaximoVerticesCaminho());

            System.out.println("\nSimulador inicializado com sucesso!\n");

        } catch (Exception e) {
            System.err.println("Erro ao carregar ou executar a simulação:");
            e.printStackTrace();
        }
    }

    // Ponto de entrada principal do programa
    public static void main(String[] args) {
        inicializarSimulador(args);

        if (simuladorGlobal != null) {
            System.out.println("Iniciando a simulação...\n");

            // Executa a simulação passo a passo, imprimindo o estado a cada passo
            for (int tempoAtual = 0; tempoAtual < simuladorGlobal.getDuracaoSimulacao(); tempoAtual++) {
                System.out.printf("== Passo %d ==%n", tempoAtual);
                simuladorGlobal.executarPasso(tempoAtual);
                System.out.println(); // espaço para melhor leitura
            }

            // Exibe estatísticas finais da simulação
            System.out.println("=== Estatísticas finais da simulação ===");
            System.out.println("Total de veículos criados: " + simuladorGlobal.getGeradorVeiculos().getTotalVeiculosCriados());
            System.out.println("Veículos que chegaram ao destino: " + simuladorGlobal.getColetor().getVeiculosFinalizados());

            System.out.println("\nSimulação finalizada com sucesso!");
        } else {
            System.err.println("Simulador não inicializado. Abortando execução.");
        }
    }
}
