import cidade.*;
import estruturas.*;
import heuristica.*;
import json.*;

public class Main {

    public static Simulador simuladorGlobal;

    public static void iniciarSimulador(String[] args) {
        try {
            int duracaoSimulacao = 10;
            if (args.length > 0) {
                try {
                    duracaoSimulacao = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    System.out.println("Argumento inválido para duração. Usando valor padrão: " + duracaoSimulacao);
                }
            }

            System.out.println("Carregando grafo do arquivo JSON...");
            Grafo grafo = LeitorOSMJson.carregar("json/Morada_do_Sol.json");
            System.out.println("Vertices carregados: " + grafo.vertices.tamanho());

            System.out.println("Criando lista de interseções e vinculando vértices...");
            Lista<Intersecao> intersecoes = grafo.converterParaIntersecoes();
            System.out.println("Total de interseções criadas: " + intersecoes.tamanho());

            int maxPrint = Math.min(5, intersecoes.tamanho());
            for (int i = 0; i < maxPrint; i++) {
                Intersecao inter = intersecoes.obter(i);
                System.out.println("Intersecao #" + i + ": " + inter + " - Vertice: " + inter.getVertice());
            }

            System.out.println("Configurando heurística adaptativa profissional...");
            HeuristicaControle heuristica = new HeuristicaCicloFixo(5, 2, 5); // verde 5, amarelo 2, vermelho 5


            System.out.println("Criando simulador com duração de " + duracaoSimulacao + " unidades de tempo.");
            simuladorGlobal = new Simulador(intersecoes, heuristica, duracaoSimulacao, grafo);

            System.out.println("Simulador inicializado com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao carregar ou executar a simulação:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        iniciarSimulador(args);

        if (simuladorGlobal != null) {
            for (int tempoAtual = 0; tempoAtual < simuladorGlobal.getDuracaoSimulacao(); tempoAtual++) {
                simuladorGlobal.executarPasso(tempoAtual);
                // NÃO CHAMAR O ConsoleMonitor.imprimirEstado AQUI, pois já é chamado dentro do executarPasso
            }
        }
    }
}
