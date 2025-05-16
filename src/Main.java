import cidade.*;
import estruturas.*;
import heuristica.*;
import json.*;

public class Main {

    public static Simulador simuladorGlobal;

    public static void iniciarSimulador(String[] args) {
        try {
            int duracaoSimulacao = 20;
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


            for (int i = 0; i < grafo.vertices.tamanho(); i++) {
                Vertice v = grafo.vertices.obter(i);
                if (v.getIntersecao() == null) {
                    Intersecao intersecao = new Intersecao(String.valueOf(v.getId()));
                    intersecao.setVertice(v);
                    intersecoes.adicionar(intersecao);
                    v.setIntersecao(intersecao);
                }
            }

            // Imprime quantidade de interseções para garantir que não está vazia
            System.out.println("Total de interseções criadas: " + intersecoes.tamanho());
            // Exibe alguns exemplos das interseções para conferir
            int maxPrint = Math.min(5, intersecoes.tamanho());
            for (int i = 0; i < maxPrint; i++) {
                Intersecao inter = intersecoes.obter(i);
                System.out.println("Intersecao #" + i + ": " + inter + " - Vertice: " + inter.getVertice());
            }

            System.out.println("Configurando heurística adaptativa profissional...");
            HeuristicaControle heuristica = new HeuristicaAdaptativa(20, 5, 15, 3);

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
    }
}
