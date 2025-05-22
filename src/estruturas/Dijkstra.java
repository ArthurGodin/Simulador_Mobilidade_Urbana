package estruturas;

import java.util.HashMap;
import java.util.HashSet;
import cidade.*;

public class Dijkstra {

    // Método principal que encontra o menor caminho entre origem e destino no grafo
    public static Fila<Vertice> encontrarMenorCaminho(Grafo grafo, Vertice origem, Vertice destino) {
        // Mapa que guarda a menor distância conhecida de origem até cada vértice
        HashMap<Vertice, Integer> distancias = new HashMap<>();
        // Mapa que guarda o vértice anterior no caminho para reconstrução do caminho
        HashMap<Vertice, Vertice> anteriores = new HashMap<>();
        // Conjunto para marcar os vértices já visitados
        HashSet<Vertice> visitados = new HashSet<>();

        // Inicializa distâncias e anteriores para todos os vértices
        Lista<Vertice> todosVertices = grafo.vertices;
        for (int i = 0; i < todosVertices.tamanho(); i++) {
            Vertice v = todosVertices.obter(i);
            distancias.put(v, Integer.MAX_VALUE);
            anteriores.put(v, null);
        }
        // Distância da origem para ela mesma é zero
        distancias.put(origem, 0);

        // Enquanto não visitou todos os vértices
        while (visitados.size() < todosVertices.tamanho()) {
            Vertice atual = encontrarMenorVertice(distancias, visitados);
            if (atual == null) break; // Se não encontrou vértice não visitado, termina
            visitados.add(atual);

            // Para cada vértice adjacente ao atual
            Lista<Aresta> adjacentes = grafo.obterArestas(atual);
            for (int i = 0; i < adjacentes.tamanho(); i++) {
                Aresta aresta = adjacentes.obter(i);
                Vertice vizinho = aresta.getDestino();

                // Se o vizinho ainda não foi visitado
                if (!visitados.contains(vizinho)) {
                    int novaDist = (int) (distancias.get(atual) + aresta.getPeso());
                    // Atualiza a distância se o novo caminho for menor
                    if (novaDist < distancias.get(vizinho)) {
                        distancias.put(vizinho, novaDist);
                        anteriores.put(vizinho, atual);
                    }
                }
            }
        }

        // Reconstrói o caminho da origem ao destino
        Fila<Vertice> caminho = construirCaminho(anteriores, origem, destino);

        // Exibe o caminho para depuração
        System.out.println("Caminho calculado de " + origem + " para " + destino + ": ");
        for (Vertice v : caminho) {
            System.out.print(v + " ");
        }
        System.out.println();

        return caminho;
    }

    // Encontra o vértice não visitado com a menor distância conhecida
    private static Vertice encontrarMenorVertice(HashMap<Vertice, Integer> distancias, HashSet<Vertice> visitados) {
        Vertice menor = null;
        int menorDistancia = Integer.MAX_VALUE;
        for (Vertice v : distancias.keySet()) {
            if (!visitados.contains(v) && distancias.get(v) < menorDistancia) {
                menor = v;
                menorDistancia = distancias.get(v);
            }
        }
        return menor;
    }

    // Reconstrói o caminho da origem até o destino a partir do mapa de anteriores
    private static Fila<Vertice> construirCaminho(HashMap<Vertice, Vertice> anteriores, Vertice origem, Vertice destino) {
        Fila<Vertice> caminho = new Fila<>();
        Vertice atual = destino;

        // Percorre do destino até a origem, adicionando os vértices na fila
        while (atual != null) {
            caminho.enfileirar(atual);
            atual = anteriores.get(atual);
        }

        // Inverte a fila para que o caminho fique na ordem correta: origem -> destino
        Fila<Vertice> caminhoFinal = new Fila<>();
        while (!caminho.estaVazia()) {
            caminhoFinal.enfileirar(caminho.desenfileirar());
        }

        return caminhoFinal;
    }
}
