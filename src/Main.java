import cidade.*;
import estruturas.*;
import heuristica.*;
import json.*;
import trafego.*;

public class Main {

    // Simulador global para acesso em toda classe
    public static Simulador simuladorGlobal;

    public static void iniciarSimulador(String[] args) {
        try {
            // Duração padrão da simulação
            int duracaoSimulacao = 20;

            // Se foi passado argumento, tenta converter para inteiro
            if (args.length > 0) {
                try {
                    duracaoSimulacao = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    System.out.println("Argumento inválido para duração. Usando valor padrão: " + duracaoSimulacao);
                }
            }

            System.out.println("Carregando grafo do arquivo JSON...");
            Grafo grafo = LeitorOSMJson.carregar("json/Morada_do_Sol.json");
            System.out.printf("Vertices carregados: %d%n", grafo.vertices.tamanho());

            System.out.println("Criando lista de interseções e vinculando vértices...");
            Lista<Intersecao> intersecoes = grafo.converterParaIntersecoes();
            System.out.printf("Total de interseções criadas: %d%n", intersecoes.tamanho());

            // Mostra algumas interseções para debug
            int maxPrint = Math.min(5, intersecoes.tamanho());
            for (int i = 0; i < maxPrint; i++) {
                Intersecao inter = intersecoes.obter(i);
                System.out.printf("Intersecao #%d: %s - Vertice: %s%n", i, inter, inter.getVertice());
            }

            System.out.println("Configurando heurística adaptativa profissional...");
            HeuristicaControle heuristica = new HeuristicaCicloFixo(5, 2, 5);

            System.out.printf("Criando simulador com duração de %d unidades de tempo.%n", duracaoSimulacao);
            simuladorGlobal = new Simulador(intersecoes, heuristica, duracaoSimulacao, grafo);

            // Configurações para controlar veículos criados e frequência de criação
            simuladorGlobal.getGeradorVeiculos().setMaxVeiculosParaCriar(5); // máximo de 5 veículos
            simuladorGlobal.getGeradorVeiculos().setPassosParaGerarVeiculo(1); // gera 1 veículo a cada passo
            simuladorGlobal.getGeradorVeiculos().setLimiteMaximoVerticesCaminho(10); // limita trajetos a no máximo 15 vértices

            System.out.println("Simulador inicializado com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao carregar ou executar a simulação:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        iniciarSimulador(args);

        if (simuladorGlobal != null) {
            // Executa a simulação passo a passo
            for (int tempoAtual = 0; tempoAtual < simuladorGlobal.getDuracaoSimulacao(); tempoAtual++) {
                simuladorGlobal.executarPasso(tempoAtual);
            }

            // Imprime estatísticas finais
            System.out.println("\nTotal de veículos criados: " + simuladorGlobal.getGeradorVeiculos().getTotalVeiculosCriados());
            System.out.println("Veículos que chegaram ao destino: " + simuladorGlobal.getColetor().getVeiculosFinalizados());

            System.out.println("\nSimulação finalizada com sucesso!");
        } else {
            System.err.println("Simulador não inicializado. Abortando execução.");
        }
    }
}
