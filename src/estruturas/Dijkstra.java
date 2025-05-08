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

                Lista<Aresta> adjacentes = grafo.obterArestas("");
                for (int i = 0; i < adjacentes.tamanho(); i++) {
                    Aresta aresta = adjacentes.obter(i);
                    Vertice vizinho = aresta.getDestino();
                    if (!visitados.contains(vizinho)) {
                        int novaDist = (int) (distancias.get(atual) + aresta.getPeso());
                        if (novaDist < distancias.get(vizinho)) {
                            distancias.put(vizinho, novaDist);
                            anteriores.put(vizinho, atual);
                        }
                    }
                }
            }

            return construirCaminho(anteriores, origem, destino);
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
            Pilha pilha = new Pilha();
            Vertice atual = destino;

            while (atual != null) {
                pilha.empilhar(atual);
                atual = anteriores.get(atual);
            }

            Fila<Vertice> caminho = new Fila<>();
            while (!pilha.estaVazia()) {
                caminho.enfileirar(pilha.desempilhar());
            }

            return caminho;
        }
}