package estruturas;

import java.util.HashMap;
import java.util.HashSet;
import cidade.*;


public class Dijkstra {

    public static Fila<Vertice> encontrarMenorCaminho(Grafo grafo, Vertice origem, Vertice destino) {
        HashMap<Vertice, Integer> distancias = new HashMap<>();
        HashMap<Vertice, Vertice> anteriores = new HashMap<>();
        HashSet<Vertice> visitados = new HashSet<>();

        Lista<Vertice> todosVertices = grafo.vertices;
        for (int i = 0; i < todosVertices.tamanho(); i++) {
            Vertice v = todosVertices.obter(i);
            distancias.put(v, Integer.MAX_VALUE);
            anteriores.put(v, null);
        }
        distancias.put(origem, 0);

        while (visitados.size() < todosVertices.tamanho()) {
            Vertice atual = encontrarMenorVertice(distancias, visitados);
            if (atual == null) break;
            visitados.add(atual);

            Lista<Aresta> adjacentes = grafo.obterArestas(atual);
            for (int i = 0; i < adjacentes.tamanho(); i++) {
                Aresta aresta = adjacentes.obter(i);
                Vertice vizinho = aresta.getDestino();
                if (!visitados.contains(vizinho)) {
                    int novaDist = (int) (distancias.get(atual) + aresta.getPeso());
                    if (novaDist < distancias.get(vizinho)) {
                        distancias.put(vizinho, novaDist);
                        anteriores.put(vizinho, atual);
                        //System.out.println("Distância atualizada: " + vizinho + " - > " + novaDist);
                    }
                }
            }
        }

        Fila<Vertice> caminho = construirCaminho(anteriores, origem, destino);

        // Logs para depuração
        System.out.println("Caminho calculado de " + origem + " para " + destino + ": ");
        for (Vertice v : caminho) {
            System.out.print(v + " ");
        }
        System.out.println();

        return caminho;
    }


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

    private static Fila<Vertice> construirCaminho(HashMap<Vertice, Vertice> anteriores, Vertice origem, Vertice destino) {
        Fila<Vertice> caminho = new Fila<>();
        Vertice atual = destino;

        // Percorrer os vértices anteriores até chegar na origem
        while (atual != null) {
            caminho.enfileirar(atual);
            atual = anteriores.get(atual);
        }

        // Reverter o caminho, pois ele foi adicionado de trás para frente
        Fila<Vertice> caminhoFinal = new Fila<>();
        while (!caminho.estaVazia()) {
            caminhoFinal.enfileirar(caminho.desenfileirar());
        }

        return caminhoFinal;
    }
}